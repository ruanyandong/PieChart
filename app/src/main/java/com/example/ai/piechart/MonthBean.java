package com.example.ai.piechart;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MonthBean implements Parcelable {

    public String date;

    public ArrayList<PieBean> obj;

    @Override
    public String toString() {
        return "MonthBean{" +
                "date='" + date + '\'' +
                ", obj=" + obj.toString() +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);

    }

    public static final Parcelable.Creator<MonthBean> CREATOR =
            new Parcelable.Creator<MonthBean>() {
                @Override
                public MonthBean createFromParcel(Parcel source) {

                    return null;
                }

                @Override
                public MonthBean[] newArray(int size) {
                    return new MonthBean[size];
                }
            };

    public float getSum() {
        float sum = 0;

        for (int i = 0; i < obj.size(); i++) {

            sum += obj.get(i).value;
        }
        return sum;
    }

    public float getSum(int index) {

        float sum = 0;
        for (int i = 0; i < index; i++) {
            sum += obj.get(i).value;
        }

        return sum;
    }


    class PieBean {

        public String title;
        public int value;

        @Override
        public String toString() {
            return "PieBean{" +
                    "title='" + title + '\'' +
                    ", value=" + value +
                    '}';
        }
    }

}
