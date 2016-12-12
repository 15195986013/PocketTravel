package com.njbd.pt.model;

import java.util.Date;

public class Post {
    private String id;

    private String userId;

    private String startPostion;

    private String endPostion;

    private Integer state;

    private Integer deleteFlag;

    private String pubDate;

    private Float appraisal;

    private Date createTime;

    private Date updateTime;

    private Double startLon;

    private Double startLat;

    private Double endLon;

    private Double endLat;

    private Integer type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getStartPostion() {
        return startPostion;
    }

    public void setStartPostion(String startPostion) {
        this.startPostion = startPostion == null ? null : startPostion.trim();
    }

    public String getEndPostion() {
        return endPostion;
    }

    public void setEndPostion(String endPostion) {
        this.endPostion = endPostion == null ? null : endPostion.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public Float getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(Float appraisal) {
        this.appraisal = appraisal;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public Double getStartLon() {
        return startLon;
    }

    public void setStartLon(Double startLon) {
        this.startLon = startLon;
    }

    public Double getStartLat() {
        return startLat;
    }

    public void setStartLat(Double startLat) {
        this.startLat = startLat;
    }

    public Double getEndLon() {
        return endLon;
    }

    public void setEndLon(Double endLon) {
        this.endLon = endLon;
    }

    public Double getEndLat() {
        return endLat;
    }

    public void setEndLat(Double endLat) {
        this.endLat = endLat;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}