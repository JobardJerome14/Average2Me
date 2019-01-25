package averageme.first.com.average2me.api;

import averageme.first.com.average2me.models.Ask;

import averageme.first.com.average2me.models.RetourUpdate;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApi {

    @GET("/getask")
    Call<Ask> getAverageMeAsk(
            @Query("user_id") String user_id,
            @Query("category") String category
    );

    @GET("/updask")
    Call<RetourUpdate> updateAverageMeAsk(
            @Query("ask_id") Integer ask_id,
            @Query("rep") String reponse
    );

}
