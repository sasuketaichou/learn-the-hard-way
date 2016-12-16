package com.example.amieruljapri.myapplication27;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Created by amierul.japri on 12/6/2016.
 */

public class GlpiSyncAdapter extends AbstractThreadedSyncAdapter{



    public GlpiSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public GlpiSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {

    }
}
