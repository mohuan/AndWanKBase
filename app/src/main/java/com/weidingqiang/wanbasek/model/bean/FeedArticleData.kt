package com.weidingqiang.wanbasek.model.bean

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


data class FeedArticleData(var apkLink:String,var author: String, var chapterId: Int, var chapterName: String,
                           var minScore: String,var courseId: Int,var desc:String,var link:String,
                           var title:String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(this.apkLink)
        parcel.writeString(this.author)
        parcel.writeInt(this.chapterId)
        parcel.writeString(this.chapterName)
        parcel.writeString(this.minScore)
        parcel.writeInt(this.courseId)
        parcel.writeString(this.desc)
        parcel.writeString(this.link)
        parcel.writeString(this.title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FeedArticleData> {
        override fun createFromParcel(parcel: Parcel): FeedArticleData {
            return FeedArticleData(parcel)
        }

        override fun newArray(size: Int): Array<FeedArticleData?> {
            return arrayOfNulls(size)
        }
    }


}

//    private String ;
//    private boolean collect;
//    private int ;
//    private String ;
//    private String envelopePic;
//    private int id;
//    private String ;
//    private String niceDate;
//    private String origin;
//    private String projectLink;
//    private int superChapterId;
//    private String superChapterName;
//    private long publishTime;
//    private String ;
//    private int visible;
//    private int zan;