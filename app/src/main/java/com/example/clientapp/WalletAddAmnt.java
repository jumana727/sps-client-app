package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletAddAmnt extends AppCompatActivity {

    private static final String BACKEND_URL = "http://ec2-3-145-108-109.us-east-2.compute.amazonaws.com:4242/"; //4242 is port mentioned in server i.e index.js
    EditText amountText;
    CardInputWidget cardInputWidget;
    Button payButton;

    // we need paymentIntentClientSecret to start transaction
    private String paymentIntentClientSecret;
    //declare stripe
    private Stripe stripe;

    int amountDouble;
    String nameFromdb , emailfromdb , passwordfromdb , phoneFromdb ;
    String mWalletMoney;
    private OkHttpClient httpClient;
    DatabaseReference reference1;
    Query checkUserDatabase1;
    String cureent;
    String[] WalletMoney = {""};
    static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_add_amnt);


        amountText = findViewById(R.id.amount_id);
        cardInputWidget = findViewById(R.id.cardInputWidget);
        payButton = findViewById(R.id.payButton);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Transaction in progress");
        progressDialog.setCancelable(false);
        httpClient = new OkHttpClient();

        cureent =global_username.getUserid();
        Toast.makeText(this, cureent, Toast.LENGTH_SHORT).show();
         reference1 = FirebaseDatabase.getInstance().getReference("User");
         checkUserDatabase1 = reference1.orderByChild("userId").equalTo(cureent);
        checkUserDatabase1.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            for(DataSnapshot snapshot1:snapshot.getChildren()) {
                nameFromdb = snapshot1.child("name").getValue(String.class);
                emailfromdb = snapshot1.child("email").getValue(String.class);
                phoneFromdb = snapshot1.child("phone").getValue(String.class);
                passwordfromdb = snapshot1.child("password").getValue(String.class);

                try{
                    mWalletMoney = snapshot1.child("wallet").getValue(String.class);

                }catch(Exception e){
                    int intBalance = snapshot1.child("wallet").getValue(Integer.class);
                    mWalletMoney = Integer.toString(intBalance);
                }

                WalletMoney[0] =mWalletMoney;
               /* Toast.makeText(WalletAddAmnt.this, mWalletMoney+ "\n" + nameFromdb + "\n" +emailfromdb , Toast.LENGTH_SHORT).show();*/

            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }});



        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull("pk_test_51MqqXlSFTtbabsqAw7LaCFD3YcgUv7lHpSaxiUK6dT2OQ4s0PqysX86Jy41vtM2tZbEMNZPT0MWlLt2PMseRdqs000Gqc5r4Pp")
        );

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get Amount
                String amt =  amountText.getText().toString();

                if(amt.isEmpty()){
                    Toast.makeText(WalletAddAmnt.this, "Please Enter a Amount!!", Toast.LENGTH_SHORT).show();
                }
                else{

                    amountDouble = Integer.parseInt(amt);
                    progressDialog.show();
                    //call checkout to get paymentIntentClientSecret key
                    startCheckout();
                }


            }
        });

    }

    private void startCheckout()
        {
            // Create a PaymentIntent by calling the server's endpoint.
            MediaType mediaType = MediaType.get("application/json; charset=utf-8");
//        String json = "{"
//                + "\"currency\":\"usd\","
//                + "\"items\":["
//                + "{\"id\":\"photo_subscription\"}"
//                + "]"
//                + "}";
            int amount=amountDouble*100;
            Map<String,Object> payMap=new HashMap<>();
            Map<String,Object> itemMap=new HashMap<>();
            List<Map<String,Object>> itemList =new ArrayList<>();
            payMap.put("currency","INR");
            itemMap.put("id","photo_subscription");
            itemMap.put("amount",amount);
            itemList.add(itemMap);
            payMap.put("items",itemList);
            String json = new Gson().toJson(payMap);
            RequestBody body = RequestBody.create(json, mediaType);
            Request request = new Request.Builder()
                    .url(BACKEND_URL + "create-payment-intent")
                    .post(body)
                    .build();
            httpClient.newCall(request)
                    .enqueue(new PayCallback(this));

        }

    private static final class PayCallback implements okhttp3.Callback {
        @NonNull
        private final WeakReference<WalletAddAmnt> activityRef;
        PayCallback(@NonNull WalletAddAmnt activity) {
            activityRef = new WeakReference<>(activity);
        }
        @Override
        public void onFailure(@NonNull okhttp3.Call call,@NonNull IOException e) {
            progressDialog.dismiss();
            final WalletAddAmnt activity = activityRef.get();
            if (activity == null) {
                return;
            }
            activity.runOnUiThread(() ->
                    System.out.println("Error: " + e.toString())
//                    Toast.makeText(
//                            activity, "Error: " + e.toString(), Toast.LENGTH_LONG
//                    ).show()

            );
        }
//        @Override
//        public void onFailure(@NonNull Call call, @NonNull IOException e) {
//            progressDialog.dismiss();
//            final WalletAddAmnt activity = activityRef.get();
//            if (activity == null) {
//                return;
//            }
//            activity.runOnUiThread(() ->
//                    Toast.makeText(
//                            activity, "Error: " + e.toString(), Toast.LENGTH_LONG
//                    ).show()
//            );
//        }
        @Override
        public void onResponse(@NonNull okhttp3.Call call, @NonNull final okhttp3.Response response)
             {
            final WalletAddAmnt activity = activityRef.get();
            if (activity == null) {
                return;
            }
            if (!response.isSuccessful()) {
                activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Error: " + response.toString(), Toast.LENGTH_LONG
                        ).show()
                );
            } else {
                try {
                    activity.onPaymentSuccess(response);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }



    }

    private void onPaymentSuccess(@NonNull final okhttp3.Response response) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> responseMap = gson.fromJson(
                 Objects.requireNonNull(response.body().string()),
                type
        );
        paymentIntentClientSecret = responseMap.get("clientSecret");

        //once you get the payment client secret start transaction
        //get card detail
        PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
        if (params != null) {
            //now use paymentIntentClientSecret to start transaction
            ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                    .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
            //start payment
            stripe.confirmPayment(WalletAddAmnt.this, confirmParams);
        }
        Log.i("TAG", "onPaymentSuccess: "+paymentIntentClientSecret);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(WalletAddAmnt.this));

    }

    private final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<WalletAddAmnt> activityRef;
        PaymentResultCallback(@NonNull WalletAddAmnt activity) {
            activityRef = new WeakReference<>(activity);
        }
        //If Payment is successful
        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            progressDialog.dismiss();
            final WalletAddAmnt activity = activityRef.get();
            if (activity == null) {
                return;
            }
            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {

                int i = Integer.parseInt(WalletMoney[0]);
                int ans=i+amountDouble;
                reference1.orderByChild("userId").equalTo(cureent).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                            String clubkey = childSnapshot.getKey();


                            Map<String, Object> updates = new HashMap<>();

                            updates.put("userId", cureent );
                            updates.put("name", nameFromdb );
                            updates.put("email", emailfromdb);
                            updates.put("phone", phoneFromdb);
                            updates.put("password", passwordfromdb);
                            updates.put("wallet", ans);



                            //        reference.child("users").child(current_user).setValue(new_user_name);
                            reference1.child(clubkey).setValue(updates);

                            Toast.makeText(getApplicationContext(), "Balance Added. ", Toast.LENGTH_SHORT).show();
                            finish();
                        }}

                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Failed to read value.", Toast.LENGTH_SHORT).show();
                    }
                });

                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Toast toast =Toast.makeText(activity, "Ordered Successful", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );
            }
        }
        //If Payment is not successful
        @Override
        public void onError(@NonNull Exception e) {
            progressDialog.dismiss();
            final WalletAddAmnt activity = activityRef.get();
            if (activity == null) {
                return;
            }
            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString());
        }
    }
    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", null);
        builder.create().show();
    }

}