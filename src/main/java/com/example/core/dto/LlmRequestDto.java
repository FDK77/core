package com.example.core.dto;

import com.example.core.models.Filter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class LlmRequestDto implements Serializable {
    private long messageid;
    private long chatid;
    private long userid;
    private String text;
    private Set<FilterToLlmDto> filters;
    private String timestamp;

    public LlmRequestDto() {
    }

    public LlmRequestDto(long messageid, long chatid, long userid, String text, Set<FilterToLlmDto> filters, String timestamp) {
        this.messageid = messageid;
        this.chatid = chatid;
        this.userid = userid;
        this.text = text;
        this.filters = filters;
        this.timestamp = timestamp;
    }

    public long getMessageid() {
        return messageid;
    }

    public void setMessageid(long messageid) {
        this.messageid = messageid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<FilterToLlmDto> getFilters() {
        return filters;
    }

    public void setFilters(Set<FilterToLlmDto> filters) {
        this.filters = filters;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public long getChatid() {
        return chatid;
    }

    public void setChatid(long chatid) {
        this.chatid = chatid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }
}
