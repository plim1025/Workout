package com.example.android.workout;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable{

    private String mName;
    private float mWeight;
    private int mReps;
    private int mSet;

    public Exercise(String name, float weight, int reps, int set) {
        mName = name;
        mWeight = weight;
        mReps = reps;
        mSet = set;
    }

    private Exercise(Parcel in) {
        mName = in.readString();
        mWeight = in.readFloat();
        mReps = in.readInt();
        mSet = in.readInt();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public String getName() {
        return mName;
    }

    public float getWeight() { return mWeight; }

    public int getReps() { return mReps; }

    public int getSet() { return mSet; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeFloat(mWeight);
        parcel.writeInt(mReps);
        parcel.writeInt(mSet);
    }
}
