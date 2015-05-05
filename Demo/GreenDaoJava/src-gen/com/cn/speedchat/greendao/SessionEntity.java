package com.cn.speedchat.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table SESSION_ENTITY.
 */
public class SessionEntity {

    private Long id;
    /** Not-null value. */
    private String sessionid;
    /** Not-null value. */
    private String from;
    /** Not-null value. */
    private String to;
    private long gossipid;
    private String gossip;
    private int sessiontype;
    private boolean asdasd;

    public SessionEntity() {
    }

    public SessionEntity(Long id) {
        this.id = id;
    }

    public SessionEntity(Long id, String sessionid, String from, String to, long gossipid, String gossip, int sessiontype, boolean asdasd) {
        this.id = id;
        this.sessionid = sessionid;
        this.from = from;
        this.to = to;
        this.gossipid = gossipid;
        this.gossip = gossip;
        this.sessiontype = sessiontype;
        this.asdasd = asdasd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getSessionid() {
        return sessionid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    /** Not-null value. */
    public String getFrom() {
        return from;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setFrom(String from) {
        this.from = from;
    }

    /** Not-null value. */
    public String getTo() {
        return to;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTo(String to) {
        this.to = to;
    }

    public long getGossipid() {
        return gossipid;
    }

    public void setGossipid(long gossipid) {
        this.gossipid = gossipid;
    }

    public String getGossip() {
        return gossip;
    }

    public void setGossip(String gossip) {
        this.gossip = gossip;
    }

    public int getSessiontype() {
        return sessiontype;
    }

    public void setSessiontype(int sessiontype) {
        this.sessiontype = sessiontype;
    }

    public boolean getAsdasd() {
        return asdasd;
    }

    public void setAsdasd(boolean asdasd) {
        this.asdasd = asdasd;
    }

}