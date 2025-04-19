package com.example.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LlmRequestDto implements Serializable {
    private long messageid;
    private long chatid;
    private long userid;
    private String text;
    private String filter;
    private boolean summary;
    private String timestamp;

    public LlmRequestDto() {
    }

    public LlmRequestDto(long messageid, long chatid, long userid, String text, String filter, boolean summary, String timestamp) {
        this.messageid = messageid;
        this.chatid = chatid;
        this.userid = userid;
        this.text = text;
        this.filter = filter;
        this.summary = summary;
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

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public boolean isSummary() {
        return summary;
    }

    public void setSummary(boolean summary) {
        this.summary = summary;
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
