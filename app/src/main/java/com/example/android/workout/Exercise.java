package com.example.android.workout;

public class Exercise {

    private String mName;
    private String mMuscleGroup;
    private String mType;
    private String mEquipment;

    public Exercise(String name, String MuscleGroup, String type, String equipment) {
        mName = name;
        mMuscleGroup = MuscleGroup;
        mType = type;
        mEquipment = equipment;
    }

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
}
