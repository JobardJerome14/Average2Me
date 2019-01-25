package averageme.first.com.average2me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import averageme.first.com.average2me.R;
import averageme.first.com.average2me.api.SharedP;
import averageme.first.com.average2me.models.Ask;
import averageme.first.com.average2me.ui.reusable.ActivityBase;

public class AnswerActivity extends ActivityBase {

    private Ask ask;
    private String response;

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

        this.a = findViewById(R.id.a);
        this.a.setText(ask.getReponse_a());

        this.b = findViewById(R.id.b);
        this.b.setText(ask.getReponse_b());

        Integer valeur_a = ask.getNb_a();
        Integer valeur_b = ask.getNb_b();

        if(response.equals("a")) valeur_a++;
        else valeur_b++;

        this.va = findViewById(R.id.va);
        this.va.setText(String.valueOf(valeur_a));

        this.vb = findViewById(R.id.vb);
        this.vb.setText(String.valueOf(valeur_b));

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
