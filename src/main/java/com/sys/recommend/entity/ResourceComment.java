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
public class ResourceComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "resource_comment_id", type = IdType.AUTO)
    private Integer resourceCommentId;

    private Integer commentBelongId;

    private String publicTime;

    private Integer publicUserId;

    private Integer score;

    private Integer proveNumber;

    private String commentContent;

    private Integer belongType;

    @TableField(exist = false)
    private String resourceName;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getBelongType() {
        return belongType;
    }

    public void setBelongType(Integer belongType) {
        this.belongType = belongType;
    }

    public Integer getResourceCommentId() {
        return resourceCommentId;
    }

    public void setResourceCommentId(Integer resourceCommentId) {
        this.resourceCommentId = resourceCommentId;
    }
    public Integer getCommentBelongId() {
        return commentBelongId;
    }

    public void setCommentBelongId(Integer commentBelongId) {
        this.commentBelongId = commentBelongId;
    }
    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }
    public Integer getPublicUserId() {
        return publicUserId;
    }

    public void setPublicUserId(Integer publicUserId) {
        this.publicUserId = publicUserId;
    }
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
    public Integer getProveNumber() {
        return proveNumber;
    }

    public void setProveNumber(Integer like) {
        this.proveNumber = like;
    }
    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    @Override
    public String toString() {
        return "ResourceComment{" +
            "resourceCommentId=" + resourceCommentId +
            ", commentBelongId=" + commentBelongId +
            ", publicTime=" + publicTime +
            ", publicUserId=" + publicUserId +
            ", score=" + score +
            ", proveNumber=" + proveNumber +
            ", commentContent=" + commentContent +
            ", belongType=" + belongType +
        "}";
    }
}
