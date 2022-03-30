package com.sys.recommend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-03-30
 */
public class Join implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "join_id", type = IdType.AUTO)
    private Integer joinId;

    private Integer userId;

    private Integer groupId;

    public Integer getJoinId() {
        return joinId;
    }

    public void setJoinId(Integer joinId) {
        this.joinId = joinId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Join{" +
            "joinId=" + joinId +
            ", userId=" + userId +
            ", groupId=" + groupId +
        "}";
    }
}
