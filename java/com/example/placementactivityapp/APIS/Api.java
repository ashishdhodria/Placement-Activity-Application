package com.example.placementactivityapp.APIS;

import com.example.placementactivityapp.POSO.Admin;
import com.example.placementactivityapp.POSO.Company;
import com.example.placementactivityapp.POSO.Interest;
import com.example.placementactivityapp.POSO.Notification;
import com.example.placementactivityapp.POSO.PDF;
import com.example.placementactivityapp.POSO.SelectStudent;
import com.example.placementactivityapp.POSO.Student;
import com.example.placementactivityapp.POSO.Tpo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @FormUrlEncoded
    @POST("adminsignup.php")
    Call<ResponseBody> addadmin(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("addtpo.php")
    Call<ResponseBody> addtpo(
            @Field("name") String name,
            @Field("id") String id,
            @Field("email") String email,
            @Field("password") String password,
            @Field("adkey") String adkey
    );
    @FormUrlEncoded
    @POST("addstudent.php")
    Call<ResponseBody> addstudent(
            @Field("name") String name,
            @Field("id") String id,
            @Field("email") String email,
            @Field("branch") String branch,
            @Field("cgpa") String cgpa,
            @Field("password") String password,
            @Field("adkey") String adkey
    );
    @FormUrlEncoded
    @POST("updatestudent.php")
    Call<ResponseBody> updatestudent(
            @Field("name") String name,
            @Field("id") String id,
            @Field("email") String email,
            @Field("branch") String branch,
            @Field("cgpa") String cgpa,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("deletestudent.php")
    Call<ResponseBody> deletestudent(
            @Field("email") String email
    );
    @FormUrlEncoded
    @POST("deleteinterestchoice.php")
    Call<ResponseBody> deleteinterestchoice(
            @Field("semail") String semail,
            @Field("cemail") String cemail
    );
    @FormUrlEncoded
    @POST("addnotification.php")
    Call<ResponseBody> addnotification(
            @Field("title") String title,
            @Field("description") String description,
            @Field("email") String email,
            @Field("adkey") String adkey

    );
    @FormUrlEncoded
    @POST("addcompany.php")
    Call<ResponseBody> addcompany(
      @Field("name") String name,
      @Field("email") String email,
      @Field("location") String location,
      @Field("industry") String industry,
      @Field("position") String position,
      @Field("requirement") String requirement,
      @Field("tkey") String tkey,
      @Field("akey") String akey
    );
    @FormUrlEncoded
    @POST("updatecompany.php")
    Call<ResponseBody> updatecompany(
            @Field("name") String name,
            @Field("email") String email,
            @Field("location") String location,
            @Field("industry") String industry,
            @Field("position") String position,
            @Field("requirement") String requirement
    );
    @FormUrlEncoded
    @POST("interest.php")
    Call<ResponseBody> interestcompany(
            @Field("sname") String sname,
            @Field("semail") String semail,
            @Field("scompany") String scompany,
            @Field("cemail") String cemail,
            @Field("key") String key,
            @Field("position") String position
    );
    @FormUrlEncoded
    @POST("deletenotification.php")
    Call<ResponseBody> deletenotif(
      @Field("title") String title,
      @Field("email") String email
    );
    @FormUrlEncoded
    @POST("deletesestudent.php")
    Call<ResponseBody> deletesestudent(
            @Field("email") String email,
            @Field("cemail") String cemail
    );
    @FormUrlEncoded
    @POST("selectstudent.php")
    Call<ResponseBody> selectstudent(
            @Field("email") String email,
            @Field("tkey") String tkey,
            @Field("adkey") String adkey,
            @Field("cemail") String cemail
    );
    @FormUrlEncoded
    @POST("deletepdf.php")
    Call<ResponseBody> deletepdf(
            @Field("email") String email,
            @Field("pdf") String pdf
    );
    @FormUrlEncoded
    @POST("deletecompany.php")
    Call<ResponseBody> deletecompany(
            @Field("email") String email
    );
    @GET("getnotification.php")
        Call<List<Notification>> getnotif(
        @Query("email") String email
        );
    @GET("showpdf.php")
    Call<List<PDF>> showpdf(
            @Query("email") String email
    );
    @GET("sshowpdf.php")
    Call<List<PDF>> sshowpdf(
            @Query("adkey") String adkey
    );
    @GET("deletecompanyinterest.php")
    Call<ResponseBody> deleteinterest(
            @Query("email") String email
    );
    @GET("getinterest.php")
    Call<List<Interest>> getinterest(
            @Query("email") String email
    );
    @GET("sgetnotification.php")
    Call<List<Notification>> sgetnotif(
            @Query("email") String email
    );
    @GET("showsestudent.php")
    Call<List<SelectStudent>> getsestudent(
            @Query("email") String email
    );
    @GET("sshowsestudent.php")
    Call<List<SelectStudent>> sgetsestudent(
            @Query("email") String email
    );

    @GET("adminlogin.php")
    Call<List<Admin>> getAdmin(
            @Query("username") String username,
            @Query("password") String password
            );
    @GET("tpologin.php")
    Call<List<Tpo>> getTpo(
            @Query("username") String username,
            @Query("password") String password
    );
    @GET("studentlogin.php")
    Call<List<Student>> getStudent(
            @Query("username") String username,
            @Query("password") String password
    );
    @GET("showstudent.php")
    Call<List<Student>> showStudent(
            @Query("email") String email
    );
    @GET("showstudent1.php")
    Call<List<Student>> showStudent1(
            @Query("email") String email
    );
    @GET("showtpo.php")
    Call<List<Tpo>> showTpo(
            @Query("email") String email
    );
    @GET("showcompany.php")
    Call<List<Company>> showComp(
            @Query("email") String email
    );
    @GET("sshowcompany.php")
    Call<List<Company>> sshowComp(
            @Query("email") String adkey,
            @Query("semail") String semail
    );
    @GET("showcompany1.php")
    Call<List<Company>> showComp1(
            @Query("email") String email
    );

}
