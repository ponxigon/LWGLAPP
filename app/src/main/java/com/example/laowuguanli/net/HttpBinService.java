package com.example.laowuguanli.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpBinService {

    //登录
    @POST("/users")
    @FormUrlEncoded
    Call<ResponseBody> loginpost(@Field("number") String number, @Field("password") String password);

    //工人资料
    @GET("/workerIm")
    Call<ResponseBody> getWorkerInformation(@Query("id") int id);

    //用户注册
    @POST("/user_login")
    @FormUrlEncoded
    Call<ResponseBody> registerAccount(@Field("number") String number,@Field("password")
            String password,@Field("account") String account);

    //工人修改资料
    @POST("/workerRevamp")
    @FormUrlEncoded
    Call<ResponseBody> workerRevamp(@Field("id") int id, @Field("name") String name,@Field("age") String age
            , @Field("identityCard") String identityCard, @Field("bankId") String bankId);

    //工人工时薪资
    @GET("/manHour")
    Call<ResponseBody> man_hour(@Query("id") int id);

    //Boos修改资料
    @POST("/boosRevamp")
    @FormUrlEncoded
    Call<ResponseBody> boosRevamp(@Field("id") int id,@Field("name") String name,@Field("company")
            String company,@Field("city") String city);

    //boos查询资料
    @GET("/boosInfor")
    Call<ResponseBody> boosinfor(@Query("id") int id);

    //Boos工人信息列表
    @GET("/booslookworker")
    Call<ResponseBody> booslookworker(@Query("companyFront") String company);

    //manage修改资料
    @POST("/manageRevamp")
    @FormUrlEncoded
    Call<ResponseBody> manageRevamp(@Field("id") int id,@Field("name") String name,@Field("city") String city);

    //manage查询信息
    @GET("/manageSelect")
    Call<ResponseBody> manageSelect(@Query("id") int id);

    //修改密码
    @POST("/changePassword")
    @FormUrlEncoded
    Call<ResponseBody> changePassword(@Field("number") String number,@Field("password") String password);

    //账号网名
    @GET("/userOther")
    Call<ResponseBody> userOther(@Query("id") int id,@Query("account") String account);

    //manage查看boos信息
    @GET("/manageLookBoos")
    Call<ResponseBody> manageLookBoos(@Query("city") String city);

    //worker申请成为企业工人
    @POST("/labourService")
    @FormUrlEncoded
    Call<ResponseBody> labourService(@Field("id") int id,@Field("company")String company);

    //boos查看工人申请加入企业表
    @GET("/booslabourService")
    Call<ResponseBody> booslabourService(@Query("boosId") int boosid);

    //boos同意工人加入企业
    @POST("/boosagree")
    @FormUrlEncoded
    Call<ResponseBody> boosagree(@Field("workerId")int workerId,@Field("company")String company,@Field("auditResult")boolean auditResult);

    //Boos派遣员工操作
    @GET("/dispatch")
    Call<ResponseBody> dispatch(@Query("company")String company);

    //员工派遣设置
    @POST("/workerDispatch")
    @FormUrlEncoded
    Call<ResponseBody> workerDispatch(@Field("workerId")int id,@Field("companyrear")String companyrear,@Field("operatingpost")String operatingpost);

    //工人投诉
    @POST("/complaint")
    @FormUrlEncoded
    Call<ResponseBody> complaint(@Field("workerId")int workerId,@Field("companyName")String company,@Field("matter")String matter);

    //manage查看投诉信箱
    @GET("/manageComplaint")
    Call<ResponseBody> manageComplaint(@Query("manageId")int manageId);

    //manage看信内容
    @GET("/manageLookMail")
    Call<ResponseBody> manageLookMail(@Query("complaintid")int complaintid);

    //manage解决投诉
    @POST("/manageSolve")
    @FormUrlEncoded
    Call<ResponseBody> manageSolve(@Field("complaintId")int complaintid);

    //worker请假申请
    @POST("/vacate1")
    @FormUrlEncoded
    Call<ResponseBody> vacate1(@Field("workerId")int workerId,@Field("company")String company,
                               @Field("vacateDate")String vacateDate,@Field("returnDate")String returnDate,@Field("vacateCause")String cause);

    //worker查看请假情况
    @GET("/workerLookVacate")
    Call<ResponseBody> workerLookVacate(@Query("workerId")int workerid);

    //boos审核请假
    @GET("/boosVacate")
    Call<ResponseBody> boosVacate(@Query("boosId")int boosId);

    //boos回应请假结果
    @POST("/boosReplyVacate")
    @FormUrlEncoded
    Call<ResponseBody> boosReplyVacate(@Field("vacateId")int vacateId,@Field("reply")boolean reply);

    //worker预支工资申请
    @POST("/workerAdvance")
    @FormUrlEncoded
    Call<ResponseBody> workerAdvance(@Field("workerId")int workerId,@Field("company")String company,@Field("money")String money);

    //worker获取预支工资记录
    @GET("/workerGetAdvance")
    Call<ResponseBody> workerGetAdvance(@Query("workerId")int workerId);

    //Boos审核预支工资
    @GET("/boosAdvance")
    Call<ResponseBody> boosAdvance(@Query("boosId")int boosId);

    //boos预支反馈审核结果
    @POST("/boosAdvanceResult")
    @FormUrlEncoded
    Call<ResponseBody> boosAdvanceResult(@Field("advanceId")int advanceId, @Field("result")boolean result);
}

