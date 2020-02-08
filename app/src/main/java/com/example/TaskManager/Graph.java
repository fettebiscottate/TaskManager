package com.example.TaskManager;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Graph extends AppCompatActivity {
    int taskOpen;
    int taskOnDoing;
    int taskClose;
    DataBaseHelper db;
    Task task;
    Cursor cursorO;
    Cursor cursorC;
    Cursor cursorOD;

    TextView textOpen;
    TextView textOnDoing;
    TextView textClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        db = new DataBaseHelper(this);

        textOpen = findViewById(R.id.textViewOpen);
        textOnDoing=findViewById(R.id.textViewOnDoing);
        textClose = findViewById(R.id.textViewClose);


        cursorO = db.getOpenTask();
        if (cursorO.getCount() == 0) {
            Toast.makeText(Graph.this, "empty TASK open", Toast.LENGTH_LONG).show();
        } else {
            while (cursorO.moveToNext()) {
               taskOpen++;
            }
        }
        textOpen.setText("TO DO: "+taskOpen);

        cursorC = db.getCloseTask();
        if (cursorC.getCount() == 0) {
            Toast.makeText(Graph.this, "empty TASK close", Toast.LENGTH_LONG).show();
        } else {
            while (cursorC.moveToNext()) {
                taskClose++;
            }
        }
        textClose.setText("CLOSE: "+taskClose);

        cursorOD = db.getOnDoingTask();
        if (cursorOD.getCount() == 0) {
            Toast.makeText(Graph.this, "empty TASK OG", Toast.LENGTH_LONG).show();
        } else {
            while (cursorOD.moveToNext()) {
                taskOnDoing++;
            }
        }
        textOnDoing.setText("DOING: "+taskOnDoing);







        PieChartView pieChartView = findViewById(R.id.chart);

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(taskOpen, Color.rgb(39, 194, 31)).setLabel("TO DO "+taskOpen));
        pieData.add(new SliceValue(taskClose, Color.rgb(179, 12, 12)).setLabel("CLOSED "+taskClose));
        pieData.add(new SliceValue(taskOnDoing, Color.rgb(20, 204, 217)).setLabel("ON DOING "+taskOnDoing));
        //*pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Q4: $28"));*/

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("YOUR TASKS").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);
    }



    }
