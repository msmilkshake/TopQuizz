package com.heymilkshake.topquizz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Question implements Parcelable {
    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;

    public Question(String question, List<String> choiceList, int answerIndex) {
        setQuestion(question);
        setChoiceList(choiceList);
        setAnswerIndex(answerIndex);
    }
    
    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public void setChoiceList(List<String> choiceList) {
        if (choiceList == null || choiceList.isEmpty()) {
            throw new IllegalArgumentException("choiceList array cannot be null or empty.");
        }
        mChoiceList = choiceList;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        if (answerIndex < 0 || answerIndex >= mChoiceList.size()) {
            throw new IllegalArgumentException("Answer index is out of bounds.");
        }
        mAnswerIndex = answerIndex;
    }

    @Override
    public String toString() {
        return "Question{" +
                "mQuestion='" + mQuestion + '\'' +
                ", mChoiceList=" + mChoiceList +
                ", mAnswerIndex=" + mAnswerIndex +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Question question = (Question) o;
        
        if (mAnswerIndex != question.mAnswerIndex) {
            return false;
        }
        if (mQuestion != null ? !mQuestion.equals(question.mQuestion) : question.mQuestion != null) {
            return false;
        }
        return mChoiceList != null ? mChoiceList.equals(question.mChoiceList) : question.mChoiceList == null;
    }
    
    @Override
    public int hashCode() {
        int result = mQuestion != null ? mQuestion.hashCode() : 0;
        result = 31 * result + (mChoiceList != null ? mChoiceList.hashCode() : 0);
        result = 31 * result + mAnswerIndex;
        return result;
    }
    
    protected Question(Parcel in) {
        mQuestion = in.readString();
        mChoiceList = in.createStringArrayList();
        mAnswerIndex = in.readInt();
    }
    
    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }
        
        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
    
    @Override
    public int describeContents() {
        return hashCode();
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mQuestion);
        dest.writeStringList(mChoiceList);
        dest.writeInt(mAnswerIndex);
    }
}
