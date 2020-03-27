package com.heymilkshake.topquizz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionBank implements Parcelable {
    private List<Question> mQuestionsList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionsList) {
        if (questionsList == null) {
            throw new IllegalArgumentException("questionsList array can not be null");
        }
        if (questionsList.isEmpty()) {
            throw new IllegalArgumentException("questionsList array can not be empty");
        }
        Collections.shuffle(questionsList);
        setQuestionsList(questionsList);
        setNextQuestionIndex(0);
    }
    
    public List<Question> getQuestionsList() {
        return mQuestionsList;
    }

    private void setQuestionsList(List<Question> questionsList) {
        mQuestionsList = questionsList;
    }

    public Question getQuestion() {
        if (mNextQuestionIndex == mQuestionsList.size()) {
            mNextQuestionIndex = 0;
        }
        return mQuestionsList.get(mNextQuestionIndex++);
    }

    private void setNextQuestionIndex(int nextQuestionIndex) {
        mNextQuestionIndex = nextQuestionIndex;
    }
    
    protected QuestionBank(Parcel in) {
        mQuestionsList = in.createTypedArrayList(Question.CREATOR);
        mNextQuestionIndex = in.readInt();
    }
    
    public static final Creator<QuestionBank> CREATOR = new Creator<QuestionBank>() {
        @Override
        public QuestionBank createFromParcel(Parcel in) {
            return new QuestionBank(in);
        }
        
        @Override
        public QuestionBank[] newArray(int size) {
            return new QuestionBank[size];
        }
    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mQuestionsList);
        dest.writeInt(mNextQuestionIndex);
    }
}
