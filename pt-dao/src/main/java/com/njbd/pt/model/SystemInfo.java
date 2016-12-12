package com.njbd.pt.model;

import java.util.Date;

public class SystemInfo {
    private String id;

    private String sysName;

    private String sysVer;

    private String techSupport;

    private String contact;

    private Date createTime;

    private Date updateTime;

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysVer() {
        return sysVer;
    }

    public void setSysVer(String sysVer) {
        this.sysVer = sysVer == null ? null : sysVer.trim();
    }

    public String getTechSupport() {
        return techSupport;
    }

    public void setTechSupport(String techSupport) {
        this.techSupport = techSupport == null ? null : techSupport.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
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


    @Override
    public String toString() {
        return "SystemInfo{" +
            "id='" + id + '\'' +
            ", sysName='" + sysName + '\'' +
            ", sysVer='" + sysVer + '\'' +
            ", techSupport='" + techSupport + '\'' +
            ", contact='" + contact + '\'' +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            '}';
}
}