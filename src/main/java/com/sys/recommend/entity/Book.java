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
 * @since 2022-04-06
 */
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "book_id", type = IdType.AUTO)
    private Integer bookId;

    private String bookName;

    private String bookType;

    private String bookIntroduce;

    private String authorIntroduce;

    private String author;

    private String publish;

    private String originalName;

    private String translateMan;

    private String publishTime;

    private Integer pageNumber;

    private String series;

    /**
     * 出品方
     */
    private String producer;

    @TableField("ISBN")
    private String isbn;

    private String coverImg;

    private Double score;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }
    public String getBookIntroduce() {
        return bookIntroduce;
    }

    public void setBookIntroduce(String bookIntroduce) {
        this.bookIntroduce = bookIntroduce;
    }
    public String getAuthorIntroduce() {
        return authorIntroduce;
    }

    public void setAuthorIntroduce(String authorIntroduce) {
        this.authorIntroduce = authorIntroduce;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }
    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
    public String getTranslateMan() {
        return translateMan;
    }

    public void setTranslateMan(String translateMan) {
        this.translateMan = translateMan;
    }
    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }
    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Book{" +
            "bookId=" + bookId +
            ", bookName=" + bookName +
            ", bookType=" + bookType +
            ", bookIntroduce=" + bookIntroduce +
            ", authorIntroduce=" + authorIntroduce +
            ", author=" + author +
            ", publish=" + publish +
            ", originalName=" + originalName +
            ", translateMan=" + translateMan +
            ", publishTime=" + publishTime +
            ", pageNumber=" + pageNumber +
            ", series=" + series +
            ", producer=" + producer +
            ", isbn=" + isbn +
            ", coverImg=" + coverImg +
            ", score=" + score +
        "}";
    }
}
