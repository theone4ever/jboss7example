package com.sample.bean;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

//import javax.annotation.Resource;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.infinispan.manager.CacheContainer;

import com.sample.model.Property;

@ManagedBean(name="manager")
public class PropertyManager {

    @Resource(lookup="java:jboss/infinispan/container/web")
    private CacheContainer container;

    private org.infinispan.Cache<String, String> cache;
    ArrayList cacheList;

    private String key;
    private String value;

    @PostConstruct  public void start() {
        this.cache = this.container.getCache();
        cacheList = new ArrayList<String>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void save() {
        Property p = new Property();
        p.setKey(key);
        p.setValue(value);

        this.cache.put(generateKey(),value);
    }

    public void clear() {
        this.cache.clear();

    }
    public List getCacheList() {
        List<String> dataList = new ArrayList<String>();
        dataList.addAll(cache.values());
        return dataList;

    }

    public String generateKey() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

}
