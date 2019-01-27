package averageme.first.com.average2me.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AskList {
    @SerializedName("asks")
    private ArrayList<Ask> askArrayList;

    private Integer answered=0;


    public AskList(ArrayList<Ask> askArrayList) {
        this.askArrayList = askArrayList;
        this.answered = 0;
    }

    public ArrayList<Ask> getAskArrayList() {
        return askArrayList;
    }

    public Ask getAsk(Integer id) {
        return askArrayList.get(id);
    }

    public Integer getAnswered() {
        return answered;
    }

    public void setAnswered(Integer answered) {
        this.answered = answered;
    }

}
