package com.mydesing.trivia.adapter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mydesing.trivia.fragment.FragmentPlayOffline;
import com.mydesing.trivia.fragment.FragmentPlayOnline;
import com.mydesing.trivia.listener.StartTriviaTimeListener;


public class TabPagerAdapter extends FragmentPagerAdapter {
    private StartTriviaTimeListener listener;
    public TabPagerAdapter(FragmentManager fm, StartTriviaTimeListener listener) {
        super(fm);
        this.listener = listener;
    }
    @Override
    public Fragment getItem(int position) {
        if(position==0) {
            return new FragmentPlayOnline(listener);
        }
        else{
            return  new FragmentPlayOffline(listener);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}