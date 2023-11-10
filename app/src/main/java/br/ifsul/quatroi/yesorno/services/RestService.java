package br.ifsul.quatroi.yesorno.services;

import br.ifsul.quatroi.yesorno.activities.MainActivity;
import br.ifsul.quatroi.yesorno.domain.YesOrNo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface RestService {

    String URL = "https://yesno.wtf/api/";

    @Headers({"Accept: application/json"})
    @GET(URL)
    Call<YesOrNo> getYesOrNo();

}
