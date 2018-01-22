package com.nickdsantos.stickertest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.google.firebase.appindexing.FirebaseAppIndex;

/**
 * Created by nicknux on 1/21/18.
 */

public class AppIndexingUpdateService extends JobIntentService {
    private static final int INDEXING_JOB_ID = 1001;

    public static void enqueueWork(Context context) {
        enqueueWork(context, AppIndexingUpdateService.class, INDEXING_JOB_ID, new Intent());
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        AppIndexingUtil.setStickers(getApplicationContext(), FirebaseAppIndex.getInstance());
    }
}
