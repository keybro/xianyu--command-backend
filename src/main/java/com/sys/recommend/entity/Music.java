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
 * @since 2022-04-06
 */
public class Music implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "music_id", type = IdType.AUTO)
    private Integer musicId;

    private String musicName;

    private String musicType;

    private String musicIntroduction;

    /**
     * 曲目
     */
    private String tracks;

    private String performer;

    /**
     * 流派
     */
    private String genre;

    /**
     * 专辑类型
     */
    private String collectionType;

    private String publishTime;

    private String publishName;

    private String coverImg;

    private Double musicScore;

    public Integer getMusicId() {
        return musicId;
    }

    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
    }
    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }
    public String getMusicType() {
        return musicType;
    }

    public void setMusicType(String musicType) {
        this.musicType = musicType;
    }
    public String getMusicIntroduction() {
        return musicIntroduction;
    }

    public void setMusicIntroduction(String musicIntroduction) {
        this.musicIntroduction = musicIntroduction;
    }
    public String getTracks() {
        return tracks;
    }

    public void setTracks(String tracks) {
        this.tracks = tracks;
    }
    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }
    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }
    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }
    public Double getMusicScore() {
        return musicScore;
    }

    public void setMusicScore(Double movieScore) {
        this.musicScore = movieScore;
    }

    @Override
    public String toString() {
        return "Music{" +
            "musicId=" + musicId +
            ", musicName=" + musicName +
            ", musicType=" + musicType +
            ", musicIntroduction=" + musicIntroduction +
            ", tracks=" + tracks +
            ", performer=" + performer +
            ", genre=" + genre +
            ", collectionType=" + collectionType +
            ", publishTime=" + publishTime +
            ", publishName=" + publishName +
            ", coverImg=" + coverImg +
            ", musicScore=" + musicScore +
        "}";
    }
}
