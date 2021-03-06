package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class AsyncTaskTest {

    private String result = null;

    @Test
    public void jokeLoadTest() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        new AsyncTaskClass(new ListenerClass() {
            @Override
            public void jokeLoaded(String joke) {
                result = joke;
                countDownLatch.countDown();
            }
        }).execute();

        try {
            countDownLatch.await();
            assertNotNull("joke is null", result);
            assertFalse("joke is empty", TextUtils.isEmpty(result));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}