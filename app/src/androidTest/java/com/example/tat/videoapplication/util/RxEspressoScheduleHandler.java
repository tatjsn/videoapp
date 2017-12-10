package com.example.tat.videoapplication.util;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;

import io.reactivex.functions.Function;

public class RxEspressoScheduleHandler implements Function<Runnable, Runnable> {

    private final CountingIdlingResource mCountingIdlingResource =
            new CountingIdlingResource("rxJava");

    @Override
    public Runnable apply(final Runnable runnable) throws Exception {
        return () -> {
            mCountingIdlingResource.increment();

            try {
                runnable.run();
            } finally {
                mCountingIdlingResource.decrement();
            }
        };
    }

    public IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }

}
