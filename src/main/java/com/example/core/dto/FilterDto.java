package com.example.core.dto;

public class FilterDto {
    private Long id;
    private String value;
    private Boolean summary;
    private String color;
    private String name;
    public FilterDto() {}

    public FilterDto(Long id, String value, Boolean summary, String color, String name) {
        this.id = id;
        this.value = value;
        this.summary = summary;
        this.color = color;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getSummary() {
        return summary;
    }

    public void setSummary(Boolean summary) {
        this.summary = summary;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
