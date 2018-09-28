package com.example.welcome.attendance;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

/**
 * Created by nitesh on 27/3/18.
 */

public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
    @Override
    public  CharSequence getTransformation(CharSequence source, View view){
        return new PasswordCharSequence(source);

    }
    private class PasswordCharSequence implements CharSequence{
        private CharSequence mSource;
        public PasswordCharSequence(CharSequence source) {
            mSource = source;
        }
        public char charAt(int index){
            return '*';
        }
        public int length(){
            return mSource.length();
        }
        public CharSequence subSequence(int start,int end){
            return mSource.subSequence(start,end);
        }
    }
}