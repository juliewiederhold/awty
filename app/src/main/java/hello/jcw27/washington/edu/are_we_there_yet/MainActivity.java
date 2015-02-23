package hello.jcw27.washington.edu.are_we_there_yet;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.AlarmManager.*;


public class MainActivity extends Activity {
    private PendingIntent pendingIntent;
    EditText message;
    EditText duration;
    EditText phoneNumber;
    int interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = (EditText) findViewById(R.id.message);
        phoneNumber = (EditText) findViewById(R.id.number);
        duration = (EditText) findViewById(R.id.duration);

        /* Retrieve a PendingIntent that will perform a broadcast */
        final Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView button = (TextView) findViewById(R.id.start);
                if(button.getText().toString().equals("Start")){
                    if(message.getText().toString().length() > 0 && phoneNumber.getText().toString().length() == 10
                            && duration.getText().toString().length() > 0){
                       String number_and_message = "(" + phoneNumber.getText().toString().substring(0, 3) + ")"
                               + phoneNumber.getText().toString().substring(3, 6) + "-" +phoneNumber.getText().toString().substring(6, 10) + ": " + message.getText().toString();
                        int number = Integer.parseInt(duration.getText().toString());
                        interval = 1000 * 60 * number;

                        alarmIntent.putExtra("message", number_and_message);
                        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
                        start();
                        button.setText("Stop");
                    }
                } else {
                    cancel();
                    button.setText("Start");
                }
            }
        });
    }

    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        manager.setInexactRepeating(RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        pendingIntent.cancel();
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy(){
        cancel();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
