package com.txp.domain;

public class CategoryClickCount {
    private String name;
    private long value;

    public String getName() {
        return name;
    }

    public CategoryClickCount() {

    }

    public CategoryClickCount(String name, long value) {
        this.name = name;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
