package averageme.first.com.average2me.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import averageme.first.com.average2me.R;
import averageme.first.com.average2me.api.API;
import averageme.first.com.average2me.api.SharedP;
import averageme.first.com.average2me.api.helpers.ResultatCallback;
import averageme.first.com.average2me.models.Ask;
import averageme.first.com.average2me.models.RetourUpdate;
import averageme.first.com.average2me.ui.reusable.ActivityBase;

public class AskActivity extends ActivityBase {

    String categoryId;

    private String user_id = "JJUSER";
    private String response = "a";
    private SharedP sharedP;
    private Integer id_ask = 0;
    private TextView ask_label;
    private Button btn_a;
    private Button btn_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ask);

        this.sharedP = new SharedP(this);
        this.user_id = sharedP.getUserId();

        bindView();
    }

    private void bindView() {
        this.ask_label = findViewById(R.id.ask_label);

        this.btn_a = findViewById(R.id.btn_a);
        btn_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upd_grille_via_api("a");
            }
        });

        this.btn_b = findViewById(R.id.btn_b);
        btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upd_grille_via_api("b");
            }
        });

        get_grille_via_api();
    }


    private void get_grille_via_api() {
        API api = new API();

        this.categoryId = this.sharedP.getCategoryId();
        api.getAverageMeAsk(this.user_id, this.categoryId, new ResultatCallback<Ask>() {
            @Override
            public void onWaitingResult(Ask ask) {
                load_ask(ask);
            }
        });
    }


    private void upd_grille_via_api(String response) {
        API api = new API();
        this.response = response;
        api.updateAverageMeAsk(this.id_ask, response, new ResultatCallback<RetourUpdate>() {
            @Override
            public void onWaitingResult(RetourUpdate result) {
                goto_answer();
            }
        });
    }

    private void goto_answer() {
        Bundle bundle = new Bundle();
        bundle.putInt("ask_id", this.id_ask);
        bundle.putString("response", response);
        navigate(AnswerActivity.class, bundle);
    }

    private void load_ask(Ask ask) {
        this.sharedP.setCurrentAsk(ask);
        this.id_ask = ask.getId_ask();
        this.ask_label.setText(ask.getAsk());
        this.btn_a.setText(ask.getReponse_a());
        this.btn_b.setText(ask.getReponse_b());
    }
}
