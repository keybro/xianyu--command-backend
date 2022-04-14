package com.sys.recommend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class InvitationReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "invitation_reply_id", type = IdType.AUTO)
    private Integer invitationReplyId;

    private Integer invitationBelongId;

    private String invitationReplyContent;

    private Integer createrId;

    private String replyTime;

    private String createrName;

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public Integer getInvitationReplyId() {
        return invitationReplyId;
    }

    public void setInvitationReplyId(Integer invitationReplyId) {
        this.invitationReplyId = invitationReplyId;
    }
    public Integer getInvitationBelongId() {
        return invitationBelongId;
    }

    public void setInvitationBelongId(Integer invitationBelongId) {
        this.invitationBelongId = invitationBelongId;
    }
    public String getInvitationReplyContent() {
        return invitationReplyContent;
    }

    public void setInvitationReplyContent(String invitationReplyContent) {
        this.invitationReplyContent = invitationReplyContent;
    }
    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }
    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    @Override
    public String toString() {
        return "InvitationReply{" +
            "invitationReplyId=" + invitationReplyId +
            ", invitationBelongId=" + invitationBelongId +
            ", invitationReplyContent=" + invitationReplyContent +
            ", createrId=" + createrId +
            ", replyTime=" + replyTime +
            ", createrName=" + createrName +
        "}";
    }
}
