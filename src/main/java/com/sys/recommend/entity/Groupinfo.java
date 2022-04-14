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
public class Groupinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "group_id", type = IdType.AUTO)
    private Integer groupId;

    private String groupName;

    private String groupIntroduction;

    private String label;

    private Integer createrId;

    private Integer state;

    private String groupHead;

    private String headImgName;

    public String getGroupHead() {
        return groupHead;
    }

    public void setGroupHead(String groupHead) {
        this.groupHead = groupHead;
    }

    public String getHeadImgName() {
        return headImgName;
    }

    public void setHeadImgName(String headImgName) {
        this.headImgName = headImgName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getGroupIntroduction() {
        return groupIntroduction;
    }

    public void setGroupIntroduction(String groupIntroduction) {
        this.groupIntroduction = groupIntroduction;
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String labelId) {
        this.label = labelId;
    }
    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Group{" +
            "groupId=" + groupId +
            ", groupName=" + groupName +
            ", groupIntroduction=" + groupIntroduction +
            ", label=" + label +
            ", createrId=" + createrId +
            ", state=" + state +
            ", groupHead=" + groupHead +
            ", headImgName=" + headImgName +
        "}";
    }
}
