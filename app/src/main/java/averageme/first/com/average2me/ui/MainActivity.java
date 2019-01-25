package averageme.first.com.average2me.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.UUID;

import averageme.first.com.average2me.R;
import averageme.first.com.average2me.api.SharedP;
import averageme.first.com.average2me.helpers.FBevent;
import averageme.first.com.average2me.helpers.IFBEvent;
import averageme.first.com.average2me.ui.reusable.ActivityBase;


public class MainActivity extends ActivityBase {

    Button politic_btn;
    Button sport_btn;
    Button food_btn;
    Button medley_btn;

    SharedP sharedP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sharedP = new SharedP(this);
        if (this.sharedP.getUserId().isEmpty()) {
            this.sharedP.setUserId(UUID.randomUUID().toString());
        }
        bindView();
    }

    private void bindView() {
        this.politic_btn = findViewById(R.id.politic_btn);
        this.politic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_average("3");

            }
        });

        this.sport_btn = findViewById(R.id.sport_btn);
        this.sport_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_average("1");
            }
        });

        this.food_btn = findViewById(R.id.food_btn);
        this.food_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_average("2");
            }
        });

        this.medley_btn = findViewById(R.id.medley_btn);
        this.medley_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_average("0");
            }
        });
    }


    public void play_average(String categoryId) {
        //Bundle bundle = new Bundle();
        //bundle.putString("category", category);

        this.sharedP.setCategoryId(categoryId);
        navigate(AskActivity.class, null);

        new FBevent(this, IFBEvent.CLIC_EVENT, IFBEvent.BUTTON_KEY, "ask_activity");
    }

}
