package com.github.gkingf.apidocgenerator.utils;

import com.intellij.psi.PsiFile;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class YamlParser {

    private final PsiFile psiFile;
    private List<Object> data;

    public YamlParser(PsiFile psiFile) {
        this.psiFile = psiFile;
        this.loadData();
    }

    public void loadData() {
        try (InputStream inputStream = psiFile.getVirtualFile().getInputStream()) {
            this.data = new ArrayList<>();
            new Yaml().loadAll(inputStream).forEach(item -> data.add(item));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findProperty(String... keys) {
        List<String> keyPathList = Arrays.asList(keys);
        for (Object target : data) {
            Map<String, Object> properties = (Map<String, Object>) target;
            String prop = this.findPropertyByMap(properties, keyPathList, 0);
            if (!StringUtils.isEmpty(prop)) {
                return prop;
            }
        }
        return "";
    }

    public String findPropertyByMap(Map<String, Object> properties, List<String> keys, int index) {
        if (index >= keys.size()) {
            return null;
        }
        String targetKey = keys.get(index);
        for (String key : properties.keySet()) {
            if (key.equals(targetKey)) {
                if (keys.size() - 1 == index) {
                    return properties.get(key).toString();
                } else {
                    if (properties.get(key) instanceof Map) {
                        Map<String, Object> subProperties = (Map<String, Object>) properties.get(key);
                        return findPropertyByMap(subProperties, keys, index + 1);
                    }
                }
            }
        }
        return null;
    }
}
