package com.example.highcontrols.bean;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

public class BillInfo implements Parcelable {
    public int id;
    public String date;
    public int type;

    public double getAmount() {
        return amount;
    }

    public double amount;
    public String remark;

    // 账单类型，0:收入，1:支出
    public static final int BILL_TYPE_INCOME = 0;
    public static final int BILL_TYPE_COST = 1;

    public BillInfo(Parcel in) {
        id = in.readInt();
        date = in.readString();
        type = in.readInt();
        amount = in.readDouble();
        remark = in.readString();
    }

    public static final Creator<BillInfo> CREATOR = new Creator<BillInfo>() {
        @Override
        public BillInfo createFromParcel(Parcel in) {
            return new BillInfo(in);
        }

        @Override
        public BillInfo[] newArray(int size) {
            return new BillInfo[size];
        }
    };

    public BillInfo() {
    }

    @Override
    public String toString() {
        return "BillInfo{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", remark='" + remark + '\'' +
                '}';
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("type", type);
        values.put("amount", amount);
        values.put("remark", remark);
        return values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(date);
        dest.writeInt(type);
        dest.writeDouble(amount);
        dest.writeString(remark);
    }
}
