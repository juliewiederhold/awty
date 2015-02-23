package hello.jcw27.washington.edu.are_we_there_yet;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // For our recurring task, we'll just display a message
        String message = intent.getStringExtra("message");
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}