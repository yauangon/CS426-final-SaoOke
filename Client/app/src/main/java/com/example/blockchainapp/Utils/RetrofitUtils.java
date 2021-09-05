package com.example.blockchainapp.Utils;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.blockchainapp.Campaign.Campaign;
import com.example.blockchainapp.Constants;
import com.example.blockchainapp.HelpRequest.HelpRequestListActivity;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    public static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    public static BlockchainInterface blockchainInterface = retrofit.create(BlockchainInterface.class);

    public static void GetBalance() {
        //Constants.BALANCE = Long.valueOf(1000);

        Call<Long> balanceCall = RetrofitUtils.blockchainInterface.ExecuteGetBalance(Constants.USERNAME);
        balanceCall.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {

                if (response.code() == 200) {
                    Constants.BALANCE = Long.valueOf(response.code());
                } else {
                    Constants.BALANCE = Long.valueOf(1000);
                    System.out.println("Balance: " + Constants.BALANCE);
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                System.out.println("Error obtaining balance.");
                Constants.BALANCE = Long.valueOf(1000);
            }
        });
    }

    public static void LoadCampaignNames(String username) {

        /*
        Call<Campaign[]> logCall = RetrofitUtils.blockchainInterface.ExecuteGetCampaignsByUser(username);
        logCall.enqueue(new Callback<Campaign[]>() {
            @Override
            public void onResponse(Call<Campaign[]> call, Response<Campaign[]> response) {
                if (response.code() == 200) {
                    Log.d("log callback",response.body().toString());
                    Campaign[] campaigns = response.body();
                    ArrayList<String> campaignNames = new ArrayList<>();
                    for (int i = 0; i < campaigns.length; ++i) {
                        campaignNames.add(campaigns[i].getCampaignName());
                    }
                    Constants.CAMPAIGN_LIST = new String[campaignNames.size()];
                    campaignNames.toArray(Constants.CAMPAIGN_LIST);
                }
                else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            @Override
            public void onFailure(Call<Campaign[]> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

//        recyclerView.setAdapter(new CampaignListAdapter());
//        recyclerView.setLayoutManager(new LinearLayoutManager(CampaignListActivity.this));

         */
    }

}
