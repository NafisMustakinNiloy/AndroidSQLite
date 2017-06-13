package com.example.nafismustakin.androidsqlite;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText dataField;
    TextView outputText;

    MyDBHandler dbHandler;

    NotificationCompat.Builder notification;
    private static final int uniqueID = 53451;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataField = (EditText)findViewById(R.id.dataField);
        outputText = (TextView)findViewById(R.id.outputText);
        dbHandler = new MyDBHandler(this, null, null, 1);

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        printDatabase();
    }

    //Print the database
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        outputText.setText(dbString);
        dataField.setText("");
    }

    //add your elements onclick methods.
    //Add a product to the database
    public void addButtonClicked(View view){
        // dbHandler.add needs an object parameter.
        Products product = new Products(dataField.getText().toString());
        dbHandler.addProduct(product);
        printDatabase();
        //build the notification
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setTicker("Ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("AndroidSQLite");
        notification.setContentText("New data entered");

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //send out the notification
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());
    }

    //Delete items
    public void deleteButtonClicked(View view){
        // dbHandler delete needs string to find in the db
        String inputText = dataField.getText().toString();
        dbHandler.deleteProduct(inputText);
        printDatabase();
    }
}
