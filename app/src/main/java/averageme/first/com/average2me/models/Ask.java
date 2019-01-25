package averageme.first.com.average2me.models;

import com.google.gson.annotations.SerializedName;

public class Ask {

    @SerializedName("id")
    private Integer id_ask;

    @SerializedName("ask")
    private String ask;

    @SerializedName("a")
    private String reponse_a;

    @SerializedName("b")
    private String reponse_b;

    @SerializedName("va")
    private Integer nb_a;

    @SerializedName("vb")
    private Integer nb_b;

    public Ask(Integer id_ask, String ask, String reponse_a, String reponse_b, Integer nb_a, Integer nb_b) {
        this.id_ask = id_ask;
        this.ask = ask;
        this.reponse_a = reponse_a;
        this.reponse_b = reponse_b;
        this.nb_a = nb_a;
        this.nb_b = nb_b;
    }

    public Integer getId_ask() {
        return id_ask;
    }

    public String getAsk() {
        return ask;
    }

    public String getReponse_a() {
        return reponse_a;
    }

    public String getReponse_b() {
        return reponse_b;
    }

    public Integer getNb_a() {
        return nb_a;
    }

    public Integer getNb_b() {
        return nb_b;
    }

}
