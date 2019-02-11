package averageme.first.com.average2me.models;

import com.google.gson.annotations.SerializedName;

public class Ask {

    @SerializedName("id")
    private Integer id_ask;

    @SerializedName("ask")
    private String ask;

    @SerializedName("a")
    private String responseA;

    @SerializedName("b")
    private String responseB;

    @SerializedName("va")
    private Integer nb_a;

    @SerializedName("vb")
    private Integer nb_b;

    public Ask(Integer id_ask, String ask, String responseA, String responseB, Integer nb_a, Integer nb_b) {
        this.id_ask = id_ask;
        this.ask = ask;
        this.responseA = responseA;
        this.responseB = responseB;
        this.nb_a = nb_a;
        this.nb_b = nb_b;
    }

    public Integer getId_ask() {
        return id_ask;
    }

    public String getAsk() {
        return ask;
    }

    public String getResponseA() {
        return responseA;
    }

    public String getResponseB() {
        return responseB;
    }

    public Integer getNb_a() {
        return nb_a;
    }

    public Integer getNb_b() {
        return nb_b;
    }

}
