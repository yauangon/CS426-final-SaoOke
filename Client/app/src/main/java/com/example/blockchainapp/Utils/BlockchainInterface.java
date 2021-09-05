package com.example.blockchainapp.Utils;

import com.example.blockchainapp.Account.UserKey;
import com.example.blockchainapp.Campaign.Campaign;
import com.example.blockchainapp.Campaign.CampaignList;
import com.example.blockchainapp.Transaction.DonationRequest;
import com.example.blockchainapp.HelpRequest.HelpRequest;
import com.example.blockchainapp.Transaction.GrantRequest;
import com.example.blockchainapp.Transaction.Transaction;
import com.example.blockchainapp.Log.TransactionLogList;
import com.example.blockchainapp.Transaction.TransactionPackage;

import java.security.PublicKey;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BlockchainInterface {

    @POST("/transaction")
    Call<Boolean> ExecutePostTransaction(@Body TransactionPackage transactionPackage);

    @POST("/balance/{username}")
    Call<Long> ExecuteGetBalance(@Path("username") String username);

    @POST("/register")
    Call<Object> ExecutePostRegister(@Body UserKey account);

    @POST("/donate")
    Call<Object> ExecutePostDonate(@Body DonationRequest request);

    //@POST("/login")
    //Call<UserKey> ExecutePostLogin(@Body UserAccount account);

    @GET("/checkHelpRequests/{campaignName}")
    Call<HelpRequest[]> ExecuteGetHelpRequest(@Path("campaignName") String campaignName);

    @GET("/campaign/{campaignName}")
    Call<Campaign> ExecuteGetCampaignInformation(@Path("campaignName") String campaignName);

    @GET("/campaigns")
    Call<Campaign[]> ExecuteGetAllCampaign();

    @GET("/getCamByUser/{username}")
    Call<Campaign[]> ExecuteGetCampaignsByUser(@Path("username") String username);

    @GET("/transactionsLog")
    Call<TransactionLogList> ExecuteGetTransactionLog();

    @POST("/cpncreate")
    Call<Object> ExecutePostCampaign(@Body Campaign campaign);

    @POST("/give")
    Call<Object> ExecutePostGrant(@Body GrantRequest grantRequest);

    @POST("/requesthelp")
    Call<Object> ExecutePostHelpRequest(@Body HelpRequest helpRequest);
}
