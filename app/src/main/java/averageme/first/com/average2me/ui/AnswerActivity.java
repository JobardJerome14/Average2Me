package averageme.first.com.average2me.ui;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import averageme.first.com.average2me.R;
import averageme.first.com.average2me.api.SharedP;
import averageme.first.com.average2me.models.Ask;
import averageme.first.com.average2me.ui.reusable.ActivityBase;

public class AnswerActivity extends ActivityBase {

    private Ask ask;
    private String response;
    private PieChart pieChart;

    SharedP sharedP;
    TextView a;
    TextView b;
    TextView va;
    TextView vb;
    TextView ask_label;
    Button btn_next;
    Button btn_back_to_menu;


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

        this.ask_label = findViewById(R.id.ask);
        this.ask_label.setText(ask.getAsk());

/*        this.a = findViewById(R.id.a);
        this.a.setText(ask.getReponse_a());

        this.b = findViewById(R.id.b);
        this.b.setText(ask.getReponse_b());*/

        Integer valeur_a = ask.getNb_a();
        Integer valeur_b = ask.getNb_b();

        if(response.equals("a")) valeur_a++;
        else valeur_b++;

/*        this.va = findViewById(R.id.va);
        this.va.setText(String.valueOf(valeur_a));

        this.vb = findViewById(R.id.vb);
        this.vb.setText(String.valueOf(valeur_b));*/

        this.pieChart = findViewById(R.id.pieChart);
        List<PieEntry> entries = new ArrayList<>();

        float repA = 100*valeur_a.floatValue()/(valeur_a+valeur_b);
        float repB = 100*valeur_b.floatValue()/(valeur_a+valeur_b);

        entries.add(new PieEntry(repA, ask.getReponse_a()));
        entries.add(new PieEntry(repB, ask.getReponse_b()));

        PieDataSet set = new PieDataSet(entries, "Pie Results");

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        set.setColors(colors);
        set.setValueTextSize(16);
        set.setValueTextColor(R.color.my_white);
        set.setSliceSpace(15);

        PieData data = new PieData(set);
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
    }

    private void goto_menu() {
        navigate(MainActivity.class, null);
    }
}
