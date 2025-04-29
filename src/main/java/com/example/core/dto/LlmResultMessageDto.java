package com.example.core.dto;

import java.time.OffsetDateTime;

public class LlmResultMessageDto {
    private long messageid;
    private long chatid;
    private long userid;
    private String text;
    private OffsetDateTime timestamp;
    private String summary;

    private Long match;

    public LlmResultMessageDto() {
    }

    public LlmResultMessageDto(long messageid, long chatid, long userid, String text, OffsetDateTime timestamp, String summary, Long match) {
        this.messageid = messageid;
        this.chatid = chatid;
        this.userid = userid;
        this.text = text;
        this.timestamp = timestamp;
        this.summary = summary;
        this.match = match;
    }

    public long getMessageid() {
        return messageid;
    }

    public void setMessageid(long messageid) {
        this.messageid = messageid;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getMatch() {
        return match;
    }

    public void setMatch(Long match) {
        this.match = match;
    }
}
