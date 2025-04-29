package com.example.core.dto;

public class FilterToLlmDto {
    private Long id;
    private String value;
    private Boolean summary;

    public FilterToLlmDto() {
    }

    public FilterToLlmDto(Long id, String value, Boolean summary) {
        this.id = id;
        this.value = value;
        this.summary = summary;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSummary() {
        return summary;
    }

    public void setSummary(Boolean summary) {
        this.summary = summary;
    }
}
