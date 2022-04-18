package com.sys.recommend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class Joins implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "join_id", type = IdType.AUTO)
    private Integer joinId;

    private Integer userId;

    private Integer groupId;

    @TableField(exist = false)
    private String groupName;

    @TableField(exist = false)
    private String groupHead;

    /**
     *  这个参与的小组中，当前用户是否是创建者，用户个人中心，我加入的小组菜单显示用
     *  1-是，2-不是
     **/
    @TableField(exist = false)
    private int isCreater;

    @TableField(exist = false)
    private int personNumber;

    public int getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
    }

    public int getIsCreater() {
        return isCreater;
    }

    public void setIsCreater(int isCreater) {
        this.isCreater = isCreater;
    }

    public String getGroupHead() {
        return groupHead;
    }

    public void setGroupHead(String groupHead) {
        this.groupHead = groupHead;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

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
