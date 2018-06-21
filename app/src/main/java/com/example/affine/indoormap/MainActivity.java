package com.example.affine.indoormap;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BaseSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<XYValue> xyValueArray;
    ArrayList<XYValue> xyValueArray1;
    PointsGraphSeries<DataPoint> xySeries;
    LineGraphSeries<DataPoint> lineSeries;
    LineGraphSeries<DataPoint> lineSeries1;
    List<Series> seriesList;
    List<SlotInfo> slotInfoList = new ArrayList<>();
    String Slot;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
//        graph.getViewport().setScalable(true);
//        graph.getViewport().setScalableY(true);

        //set manual x bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(10);
        graph.getViewport().setMinY(-10);

        //set manual y bounds
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(10);
        graph.getViewport().setMinX(-10);

        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);// It will remove the background grids
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);// remove horizontal x labels and line
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);

        makeParkingLot(graph,-7.5,-7.5,"Slot1");
        makeParkingLot(graph,-7.5,-2.5,"Slot2");
        makeParkingLot(graph,-7.5,2.5,"Slot3");
        makeParkingLot(graph,-7.5,7.5,"Slot4");
        makeParkingLot(graph,7.5,-7.5,"Slot5");
        makeParkingLot(graph,7.5,-2.5,"Slot6");
        makeParkingLot(graph,7.5,2.5,"Slot7");
        makeParkingLot(graph,7.5,7.5,"Slot8");
        makeParkingLot(graph,12.5,-7.5,"Slot9");
        makeParkingLot(graph,12.5,-2.5,"Slot10");
        makeParkingLot(graph,12.5,2.5,"Slot11");
        makeParkingLot(graph,12.5,7.5,"Slot12");
        makeParkingLot(graph,27.5,-7.5,"Slot13");
        makeParkingLot(graph,27.5,-2.5,"Slot14");
        makeParkingLot(graph,27.5,2.5,"Slot15");
        makeParkingLot(graph,27.5,7.5,"Slot16");
        boundaryPath(graph,"boundary");
        seriesList = graph.getSeries();
        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.containsKey("Slot"))
        {
            Log.e("Message","In if");
            Slot = extras.getString("Slot");
            for(int i=0;i<seriesList.size();i++){
                if(seriesList.get(i).getTitle().equals(Slot)){
                    for(int j=0;j<slotInfoList.size();j++){
                        if (slotInfoList.get(j).getSlotNumber().equals(Slot)){
                            double xp = slotInfoList.get(j).getXp();
                            double yp = slotInfoList.get(j).getYp();
                            showPath(graph,xp,yp,"path");
                        }
                    }
                    xySeries = (PointsGraphSeries<DataPoint>) seriesList.get(i);
                    xySeries.setColor(Color.BLUE);
                }
            }
        }
        else {
            Log.e("Message","In else");
            for(int i=0;i<seriesList.size();i++){
                if(seriesList.get(i).getTitle().equals("Slot7")){
                    showPath(graph,7.5,2.5,"path");
                    xySeries = (PointsGraphSeries<DataPoint>) seriesList.get(i);
                    xySeries.setColor(Color.BLUE);
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void showPath(GraphView graph, double xp, double yp, String title){
        xyValueArray = new ArrayList<>();
        lineSeries = new LineGraphSeries<>();
        if(xp > 0 && xp <= 10){
        xyValueArray.add(new XYValue(0,-10));
        xyValueArray.add(new XYValue(0,yp));
        xyValueArray.add(new XYValue(xp,yp));}
        else if(xp < 0){
            xyValueArray.add(new XYValue(xp,yp));
            xyValueArray.add(new XYValue(0,yp));
            xyValueArray.add(new XYValue(0.01,-10));
        }
        else if(xp > 10 && xp <= 20){
            xyValueArray.add(new XYValue(0,-10));
            xyValueArray.add(new XYValue(0,12.5));
            xyValueArray.add(new XYValue(20,12.5));
            xyValueArray.add(new XYValue(20.01,yp));
            xyValueArray1 = new ArrayList<>();
            xyValueArray1.add(new XYValue(xp,yp));
            xyValueArray1.add(new XYValue(20.01,yp));

        }
        else if(xp >= 20){
            xyValueArray.add(new XYValue(0,-10));
            xyValueArray.add(new XYValue(0,12.5));
            xyValueArray.add(new XYValue(20,12.5));
            xyValueArray.add(new XYValue(20.01,yp));
            xyValueArray.add(new XYValue(xp,yp));
        }
        //xyValueArray = sortArray(xyValueArray);
        for(int i = 0;i <xyValueArray.size(); i++){
            try{
                double x = xyValueArray.get(i).getX();
                double y = xyValueArray.get(i).getY();
                lineSeries.appendData(new DataPoint(x,y),true, 1000);
            }catch (IllegalArgumentException e){
                Log.e(TAG, "createScatterPlot: IllegalArgumentException: " + e.getMessage() );
            }
        }
        if (xp > 10 && xp <= 20){
            lineSeries1 = new LineGraphSeries<>();
            for(int i = 0;i <xyValueArray1.size(); i++){
                try{
                    double x = xyValueArray1.get(i).getX();
                    double y = xyValueArray1.get(i).getY();
                    lineSeries1.appendData(new DataPoint(x,y),true, 1000);
                }catch (IllegalArgumentException e){
                    Log.e(TAG, "createScatterPlot: IllegalArgumentException: " + e.getMessage() );
                }
            }
            lineSeries1.setThickness(20);
            lineSeries1.setTitle(title+"1");
            lineSeries1.setAnimated(false);
            lineSeries1.setColor(Color.RED);
            graph.addSeries(lineSeries1);
        }
        lineSeries.setThickness(20);
        lineSeries.setTitle(title);
        lineSeries.setAnimated(true);
        lineSeries.setColor(Color.RED);
        graph.addSeries(lineSeries);
    }

    public void boundaryPath(GraphView graph,String title){
        xyValueArray = new ArrayList<>();
        lineSeries = new LineGraphSeries<>();
        xyValueArray.add(new XYValue(-10,-15));
        xyValueArray.add(new XYValue(-10,15));
        xyValueArray.add(new XYValue(30,15));
        xyValueArray.add(new XYValue(30,-15));
        xyValueArray = sortArray(xyValueArray);
        for(int i = 0;i <xyValueArray.size(); i++){
            try{
                double x = xyValueArray.get(i).getX();
                double y = xyValueArray.get(i).getY();
                lineSeries.appendData(new DataPoint(x,y),true, 1000);
            }catch (IllegalArgumentException e){
                Log.e(TAG, "createScatterPlot: IllegalArgumentException: " + e.getMessage() );
            }
        }
        lineSeries.setThickness(1);
        lineSeries.setTitle(title);
        lineSeries.setAnimated(false);
        lineSeries.setColor(Color.GRAY);
        graph.addSeries(lineSeries);
    }

    public void makeParkingLot(GraphView graph,double xp, double yp,String title){
        SlotInfo slotInfo = new SlotInfo();
        slotInfo.setSlotNumber(title);
        slotInfo.setXp(xp);
        slotInfo.setYp(yp);
        slotInfoList.add(slotInfo);
        xyValueArray = new ArrayList<>();
        xySeries = new PointsGraphSeries<>();
        xyValueArray.add(new XYValue(xp,yp));
        xyValueArray = sortArray(xyValueArray);
        for(int i = 0;i <xyValueArray.size(); i++){
            try{
                double x = xyValueArray.get(i).getX();
                double y = xyValueArray.get(i).getY();
                xySeries.appendData(new DataPoint(x,y),true, 1000);
            }catch (IllegalArgumentException e){
                Log.e(TAG, "createScatterPlot: IllegalArgumentException: " + e.getMessage() );
            }
        }
        xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);
        xySeries.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
//                paint.setStrokeWidth(10);
//                canvas.drawLine(x-20, y-20, x+20, y+20, paint);
//                canvas.drawLine(x+20, y-20, x-20, y+20, paint);
                canvas.drawRect(x-120f,y+180f,x+120f,y-180f,paint);
            }
        });
        xySeries.setColor(Color.GRAY);
        xySeries.setTitle(title);
        graph.addSeries(xySeries);
    }

    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array){
        /*
        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
         */
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size() - 1;
        int count = 0;
        Log.d(TAG, "sortArray: Sorting the XYArray.");


        while (true) {
            m--;
            if (m <= 0) {
                m = array.size() - 1;
            }
            Log.d(TAG, "sortArray: m = " + m);
            try {
                //print out the y entrys so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m - 1).getY();
                double tempX = array.get(m - 1).getX();
                if (tempX > array.get(m).getX()) {
                    array.get(m - 1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m - 1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                } else if (tempX == array.get(m).getX()) {
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                } else if (array.get(m).getX() > array.get(m - 1).getX()) {
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if (count == factor) {
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_change_slot) {
            Intent myIntent = new Intent(MainActivity.this, SelectSlotActivity.class);
            MainActivity.this.startActivity(myIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
