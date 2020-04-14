package com.example.newtrolleymanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Rice extends AppCompatActivity {

    public String value;
    public DatabaseReference mref;
    TextView des;
    AnyChartView anyChartView;
    public static final String Channel_ID="channel";
    String[] weights= {"remaining","empty"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice);

        //



        //for the graph
        anyChartView = findViewById(R.id.any_chart_view);
        des= findViewById(R.id.textView5);

        Intent intent=getIntent();
        String check=intent.getStringExtra(MainActivity.ch);


        if(check.equalsIgnoreCase("rice"))
        {
            getSupportActionBar().setTitle("Rice");
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference mref = database.getReference("Variable");

            mref.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                    //Map <String, String> map = (Map)dataSnapshot.getValue();
                    //quest = map.get("Variable");
                    Log.i("xxxxx",dataSnapshot.toString());
                    String l = String.valueOf(dataSnapshot.getValue());
                    des.setText(l);
                    int q = Integer.valueOf(l);
                    setupPieChart(q);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        else
        {
            getSupportActionBar().setTitle("Sugar");
            FirebaseDatabase database = FirebaseDatabase.getInstance();
             mref = database.getReference("VariableNew");



            mref.addValueEventListener(new com.google.firebase.database.ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot)
                {

                    Log.i("xxxxx",dataSnapshot.toString());
                    String l=String.valueOf(dataSnapshot.getValue());//the value that is comming is in the string
                    des.setText(l);
                    int q = Integer.valueOf(l);//converting the string value to integer
                    setupPieChart1(q);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {

                }
            });
        }
    }


    //for the graphs
    public void setupPieChart(int q)//for the rice
    {
        int[] weights1 = new int[]{q, 20000-q};
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
       for(int i=0;i<weights.length;i++)
       {
          dataEntries.add(new ValueDataEntry(weights[i],weights1[i]));
        }

        pie.data(dataEntries);
        anyChartView.setChart(pie);

        //String print = Integer.toString(q);
        //Toast.makeText(this,print,Toast.LENGTH_LONG).show();
    }

    public void setupPieChart1(int q)//for sugar
    {
        int[] weights1 = new int[]{q, 10000-q};
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for(int i=0;i<weights.length;i++)
        {
            dataEntries.add(new ValueDataEntry(weights[i],weights1[i]));
        }
        pie.data(dataEntries);
        anyChartView.setChart(pie);

        //String print = Integer.toString(q);
        //Toast.makeText(this,print,Toast.LENGTH_LONG).show();
    }


    //for the clicking and going to next page
    public void MyPage(View view)
    {
        openUrl("https://www.bigbasket.com/ps/?q=rice");
    }
    public void openUrl(String Url) {
        Uri uri = Uri.parse(Url);//making the object for uniforn object resources
        Intent launchweb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchweb);
    }


}
