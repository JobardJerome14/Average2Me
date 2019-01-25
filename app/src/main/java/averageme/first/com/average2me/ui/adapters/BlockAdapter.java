package averageme.first.com.average2me.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import averageme.first.com.average2me.R;
import averageme.first.com.average2me.api.SharedP;
import averageme.first.com.average2me.models.Ask;


public class BlockAdapter extends BaseAdapter {
    private Context contexte;

    private Integer id_ask;
    private String ask_label;
    private String reponse_a;
    private String reponse_b;
    private Integer nb_a;
    private Integer nb_b;

    private SharedP sharedP;

    private String reponse = "a";

    public BlockAdapter(Context contexte, Ask ask) {
        this.contexte = contexte;
        this.id_ask = ask.getId_ask();
        this.ask_label = ask.getAsk();
        this.reponse_a = ask.getReponse_a();
        this.reponse_b = ask.getReponse_b();
        this.nb_a = ask.getNb_a();
        this.nb_b = ask.getNb_b();

        this.sharedP = new SharedP(contexte);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) contexte.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null)
                convertView = inflater.inflate(R.layout.ask, parent, false);
        }

        if (convertView == null)
            return null;



        final TextView ask_label = convertView.findViewById(R.id.ask);
        ask_label.setText(this.ask_label);

        final TextView nb_a = convertView.findViewById(R.id.nb_a);
        nb_a.setText(String.valueOf(this.nb_a));

        final TextView nb_b = convertView.findViewById(R.id.nb_b);
        nb_b.setText(String.valueOf(this.nb_b));

        final TextView reponse_a = convertView.findViewById(R.id.response_a);
        reponse_a.setText(this.reponse_a);

        final TextView reponse_b = convertView.findViewById(R.id.response_b);
        reponse_b.setText(this.reponse_b);

        return convertView;
    }







    /*private void save_current_ask() {
        Ask ask = new Ask(this.id_ask, this.ask, this.reponse_a, this.reponse_b, this.nb_a, this.nb_b);
        this.sharedP.setCurrentAsk(Ask);
    }*/

}
