package com.sys.recommend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-03-30
 */
public class Invitation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "invitation_id", type = IdType.AUTO)
    private Integer invitationId;

    private Integer groupId;

    private String invitationContent;

    private Integer createrId;

    private String createTime;

    private String invitationTitle;

    @TableField(exist = false)
    private String belongGroupName;

    @TableField(exist = false)
    private String createrName;

    @TableField(exist = false)
    private int replyNumber;


    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public int getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(int replyNumber) {
        this.replyNumber = replyNumber;
    }

    public String getBelongGroupName() {
        return belongGroupName;
    }

    public void setBelongGroupName(String belongGroupName) {
        this.belongGroupName = belongGroupName;
    }

    public String getInvitationTitle() {
        return invitationTitle;
    }

    public void setInvitationTitle(String invitationTitle) {
        this.invitationTitle = invitationTitle;
    }

    public Integer getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Integer invitationId) {
        this.invitationId = invitationId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getInvitationContent() {
        return invitationContent;
    }

    public void setInvitationContent(String invitationContent) {
        this.invitationContent = invitationContent;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "invitationId=" + invitationId +
                ", groupId=" + groupId +
                ", invitationContent=" + invitationContent +
                ", createrId=" + createrId +
                ", createTime=" + createTime +
                ", invitationTitle=" + invitationTitle +
                "}";
    }
}
