package com.example.android.workout;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable{

    private String mName;
    private String mMuscleGroup;
    private String mEquipment;
    private String mCategory;
    private float mWeight;
    private int mReps;
    private int mSet;
    private String mNotes;

    public Exercise(String name, String MuscleGroup, String equipment, String category, float weight, int reps, int set, String notes) {
        mName = name;
        mMuscleGroup = MuscleGroup;
        mEquipment = equipment;
        mCategory = category;
        mWeight = weight;
        mReps = reps;
        mSet = set;
        mNotes = notes;
    }

    private Exercise(Parcel in) {
        mName = in.readString();
        mMuscleGroup = in.readString();
        mEquipment = in.readString();
        mCategory = in.readString();
        mWeight = in.readFloat();
        mReps = in.readInt();
        mSet = in.readInt();
        mNotes = in.readString();
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

    public String getEquipment() {
        return mEquipment;
    }

    public String getCategory() {
        return mCategory;
    }

    public float getWeight() { return mWeight; }

    public int getReps() { return mReps; }

    public int getSet() { return mSet; }

    public String getmNotes() { return mNotes; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mMuscleGroup);
        parcel.writeString(mEquipment);
        parcel.writeString(mCategory);
        parcel.writeFloat(mWeight);
        parcel.writeInt(mReps);
        parcel.writeInt(mSet);
        parcel.writeString(mNotes);
    }
}
