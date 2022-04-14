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
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "movie_id", type = IdType.AUTO)
    private Integer movieId;

    private String movieName;

    private String coverImg;

    private Double movieScore;

    private String director;

    private String scriptwriter;

    private String actor;

    private String movieType;

    private String showTime;

    private String movieLong;

    private String description;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }
    public Double getMovieScore() {
        return movieScore;
    }

    public void setMovieScore(Double movieScore) {
        this.movieScore = movieScore;
    }
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
    public String getScriptwriter() {
        return scriptwriter;
    }

    public void setScriptwriter(String scriptwriter) {
        this.scriptwriter = scriptwriter;
    }
    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }
    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }
    public String getMovieLong() {
        return movieLong;
    }

    public void setMovieLong(String movieLong) {
        this.movieLong = movieLong;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String describe) {
        this.description = describe;
    }

    @Override
    public String toString() {
        return "Movie{" +
            "movieId=" + movieId +
            ", movieName=" + movieName +
            ", coverImg=" + coverImg +
            ", movieScore=" + movieScore +
            ", director=" + director +
            ", scriptwriter=" + scriptwriter +
            ", actor=" + actor +
            ", movieType=" + movieType +
            ", showTime=" + showTime +
            ", movieLong=" + movieLong +
            ", description=" + description +
        "}";
    }
}
