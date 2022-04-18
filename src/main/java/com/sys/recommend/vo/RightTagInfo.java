package com.sys.recommend.vo;

/**
 * @Author: LuoRuiJie
 * @Date: 2022/4/15 13:30
 * @Version 1.0
 */
public class RightTagInfo {

    private Integer myBookCommentNumber;

    private Integer myMovieCommentNumber;

    private Integer myMusicCommentNumber;

    private Integer myGroupNumber;

    private Integer myInvitationNumber;


    public Integer getMyBookCommentNumber() {
        return myBookCommentNumber;
    }

    public void setMyBookCommentNumber(Integer myBookCommentNumber) {
        this.myBookCommentNumber = myBookCommentNumber;
    }

    public Integer getMyMovieCommentNumber() {
        return myMovieCommentNumber;
    }

    public void setMyMovieCommentNumber(Integer myMovieCommentNumber) {
        this.myMovieCommentNumber = myMovieCommentNumber;
    }

    public Integer getMyMusicCommentNumber() {
        return myMusicCommentNumber;
    }

    public void setMyMusicCommentNumber(Integer myMusicCommentNumber) {
        this.myMusicCommentNumber = myMusicCommentNumber;
    }

    public Integer getMyGroupNumber() {
        return myGroupNumber;
    }

    public void setMyGroupNumber(Integer myGroupNumber) {
        this.myGroupNumber = myGroupNumber;
    }

    public Integer getMyInvitationNumber() {
        return myInvitationNumber;
    }

    public void setMyInvitationNumber(Integer myInvitationNumber) {
        this.myInvitationNumber = myInvitationNumber;
    }

    @Override
    public String toString() {
        return "RightTagInfo{" +
                "myBookCommentNumber=" + myBookCommentNumber +
                ", myMovieCommentNumber=" + myMovieCommentNumber +
                ", myMusicCommentNumber=" + myMusicCommentNumber +
                ", myGroupNumber=" + myGroupNumber +
                ", myInvitationNumber=" + myInvitationNumber +
                '}';
    }
}
