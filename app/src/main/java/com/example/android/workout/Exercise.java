package com.example.android.workout;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Exercise implements Parcelable{

    private String mName;
    private String mMuscleGroup;
    private String mType;
    private String mEquipment;
    private float mWeight;
    private int mReps;
    private int mSet;

    public Exercise(String name, String MuscleGroup, String type, String equipment, float weight, int reps, int set) {
        mName = name;
        mMuscleGroup = MuscleGroup;
        mType = type;
        mEquipment = equipment;
        mWeight = weight;
        mReps = reps;
        mSet = set;
    }

    private Exercise(Parcel in) {
        mName = in.readString();
        mMuscleGroup = in.readString();
        mType = in.readString();
        mEquipment = in.readString();
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

    public String getMuscleGroup() {
        return mMuscleGroup;
    }

    public String getType() {
        return mType;
    }

    public String getEquipment() {
        return mEquipment;
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
        parcel.writeString(mMuscleGroup);
        parcel.writeString(mType);
        parcel.writeString(mEquipment);
        parcel.writeFloat(mWeight);
        parcel.writeInt(mReps);
        parcel.writeInt(mSet);
    }
}
