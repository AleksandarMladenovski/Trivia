package com.mydesing.trivia.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.mydesing.trivia.DataRepository.RepositoryInstance;
import com.mydesing.trivia.DataRepository.RepositoryQuestion.RepositoryQuestion;
import com.mydesing.trivia.DataRepository.RepositoryQuestion.RepositoryQuestionImpl;
import com.mydesing.trivia.R;
import com.mydesing.trivia.adapter.TabPagerAdapter;
import com.mydesing.trivia.listener.CategoryListener;
import com.mydesing.trivia.listener.CustomListenerRep;
import com.mydesing.trivia.listener.StartTriviaTimeListener;
import com.mydesing.trivia.model.Category;
import com.mydesing.trivia.model.Question;
import com.mydesing.trivia.notification.NotificationAlarmRandom;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpAlarmNotification();
        final ViewPager viewPager = findViewById(R.id.viewPager);
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), new StartTriviaTimeListener() {
            @Override
            public void readyIntent(String amount, String category, String difficulty, String type) {
                Intent intent = new Intent(MainActivity.this, TriviaTime.class);
                intent.putExtra(getResources().getString(R.string.KEY_AMOUNT), amount);
                intent.putExtra(getResources().getString(R.string.KEY_CATEGORY), category);
                intent.putExtra(getResources().getString(R.string.KEY_DIFFICULTY), difficulty);
                intent.putExtra(getResources().getString(R.string.KEY_TYPE), type);
                startActivity(intent);
            }
        });

        viewPager.setAdapter(adapter);
        TabLayout tabLayout = getTabLayout();
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayoutListener(tabLayout, viewPager);

    }

    private TabLayout getTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
//        tabLayout.setBackgroundColor(getColor(R.color.colorTabMenu));
//        tabLayout.setTabTextColors(getColor(R.color.colorNormalTabText),getResources().getColor(R.color.colorSelectedTabText));
        tabLayout.addTab(tabLayout.newTab().setText("ONLINE"));
        tabLayout.addTab(tabLayout.newTab().setText("OFFLINE"));
        return tabLayout;
    }

    private void tabLayoutListener(TabLayout tabLayout, final ViewPager viewPager) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setUpAlarmNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 0);
        Intent intent = new Intent(getApplicationContext(), NotificationAlarmRandom.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//           alarmManager.setRepeating(
//                        AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                        SystemClock.elapsedRealtime()+1000,
//                        60000,
//                        pendingIntent);
        }
    }
}
