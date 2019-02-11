package averageme.first.com.average2me.ui;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;
import java.util.List;
import averageme.first.com.average2me.R;
import averageme.first.com.average2me.api.SharedP;
import averageme.first.com.average2me.helpers.FBevent;
import averageme.first.com.average2me.helpers.IFBEvent;
import averageme.first.com.average2me.models.Ask;
import averageme.first.com.average2me.ui.reusable.ActivityBase;
import averageme.first.com.average2me.ui.reusable.LogUtils;

public class AnswerActivity extends ActivityBase {

    private Ask ask;
    private String response;
    PieChart pieChart;

    private SharedP sharedP;
    private TextView ask_label;
    private Button btn_next;
    private Button btn_back_to_menu;

    AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            response = intent.getStringExtra("response");
        }

        setContentView(R.layout.activity_answer);

        sharedP = new SharedP(this);
        this.ask = sharedP.getCurrentAsk();

        bindView();
    }

    private void bindView() {

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        this.ask_label = findViewById(R.id.ask);
        this.ask_label.setText(ask.getAsk());

        Integer valueA = ask.getNb_a();
        Integer valueB = ask.getNb_b();

        if(response.equals("a")) valueA++;
        else valueB++;

        this.pieChart = findViewById(R.id.pieChart);
        List<PieEntry> entries = new ArrayList<>();

        float repA = 100*valueA.floatValue()/(valueA+valueB);
        float repB = 100*valueB.floatValue()/(valueA+valueB);

        entries.add(new PieEntry(repA, ask.getResponseA()));
        entries.add(new PieEntry(repB, ask.getResponseB()));

        PieDataSet set = new PieDataSet(entries, "");
        
        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<>();
        int[] colorArray= { ColorTemplate.rgb("#FE7484"), ColorTemplate.rgb("#25f177"),};

        ColorTemplate.createColors(colorArray);
        for (int c : colorArray)
            colors.add(c);

        set.setColors(colors);
        set.setValueTextSize(16); //sets the value on slices pie - number
        set.setValueTextColor(getResources().getColor(R.color.my_grey));
        set.setSliceSpace(12); //sets the space between slices

        pieChart.setHoleColor(getResources().getColor(R.color.main_background)); //sets the Hole center of the pie

        PieData data = new PieData(set);
        data.setValueFormatter(new PercentFormatter());
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelColor(getResources().getColor(R.color.my_grey));
        pieChart.setData(data);
        pieChart.invalidate(); // refresh


        this.btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto_ask();
            }
        });

        this.btn_back_to_menu = findViewById(R.id.btn_back_to_menu);
        btn_back_to_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto_menu();
            }
        });
    }

    private void goto_ask() {
        navigate(AskActivity.class, null);

        LogUtils.log("FBEvent", IFBEvent.CLICK_EVENT+ " " + IFBEvent.BUTTON_KEY + " next_question");
        new FBevent(this, IFBEvent.CLICK_EVENT, IFBEvent.BUTTON_KEY, "next_question");
    }

    private void goto_menu() {
        //TODO interstitial before back to Menu
        navigate(MainActivity.class, null);

        LogUtils.log("FBEvent", IFBEvent.CLICK_EVENT+ " " + IFBEvent.BUTTON_KEY + " goto_menu");
        new FBevent(this, IFBEvent.CLICK_EVENT, IFBEvent.BUTTON_KEY, "goto_menu");
    }



}
