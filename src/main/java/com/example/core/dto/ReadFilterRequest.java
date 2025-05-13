package com.example.core.dto;

public class ReadFilterRequest {
    private Long filterId;

    public ReadFilterRequest() {
    }

    public ReadFilterRequest(Long filterId) {
        this.filterId = filterId;
    }

    public Long getFilterId() {
        return filterId;
    }

    public void setFilterId(Long filterId) {
        this.filterId = filterId;
    }
}
