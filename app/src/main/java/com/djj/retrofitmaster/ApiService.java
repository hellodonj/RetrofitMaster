package com.djj.retrofitmaster;


import com.djj.retrofitmaster.baseHttp.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 接口类
 */
public interface ApiService {

//    @GET(Constans.timetable)
//    Call<TimetableVo> getTimetable();

//    @GET(Constans.timetable)
//    Observable<TimetableVo> getTimetable();

    @GET(Constans.timetable)
    Observable<BaseResponse<DemoBeanVo>> getTimetable();
}
