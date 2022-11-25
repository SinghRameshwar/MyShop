package com.singh.myshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.singh.myshop.Helpers.HelperClass;
import com.singh.myshop.LoginModelG.LoginModel;

import java.util.concurrent.TimeUnit;


public class LoginFragment extends Fragment {

    String LOG_DBG=this.getClass().getSimpleName();
    ProgressDialog progressDialog;
    // UI references.
    private EditText mobile_num,otpTxt;
    TextView otpTimer;
    private FirebaseAuth mAuth;
    private String verificationId;
    TextView getOTP_BTN;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initLogin(rootView);
        return rootView;
    }

    private void initLogin(final View rootView) {
        // Set up the login form.
        Button mEmailSignInButton = (Button) rootView.findViewById(R.id.btnLogin);
        otpTxt = (EditText) rootView.findViewById(R.id.otpTxt);
        getOTP_BTN=(TextView)rootView.findViewById(R.id.getOTP_BTN);
        otpTimer=(TextView)rootView.findViewById(R.id.otpTimer);
        mobile_num = (EditText) rootView.findViewById(R.id.mobile_num);
        mAuth = FirebaseAuth.getInstance();

        getOTP_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check for a valid emailId.
                if (TextUtils.isEmpty(mobile_num.getText().toString())) {
                    Snackbar.make(getActivity().findViewById(R.id.container), "Please Enter Mobile Number!", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (otpTimer.getTag().equals("0") && getOTP_BTN.getTag().equals("0")) {
                    progressDialogView();
                    getOTP_BTN.setTag("1");
                    mobile_num.setEnabled(false);
                    sendVerificationCode("+91"+mobile_num.getText()+"");
                    otpTxt.requestFocus();
                }else {
                    Toast.makeText(getActivity(), "Already OTP in Progress !", Toast.LENGTH_LONG).show();
                }
            }
        });

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(view);
            }
        });

    }

    void attemptLogin(View rootView){
        try {
            String mob_Num = mobile_num.getText().toString();
            if (TextUtils.isEmpty(mob_Num)) {
                Snackbar.make(getActivity().findViewById(R.id.container), "Please Enter Mobile Number!", Snackbar.LENGTH_LONG).show();
            } else if (Integer.parseInt(otpTxt.getText() + "") == Integer.parseInt(otpTimer.getTag() + "")) {
                /*------------ After Login Next View Call -------------*/
                afterLoginViewCall();
            } else {
                Snackbar.make(getActivity().findViewById(R.id.container), "Please Enter Correct OTP!", Snackbar.LENGTH_LONG).show();
            }
        }catch (Exception e){Log.e("Error: ------",e.toString());}
        if (progressDialog!=null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    void progressDialogView(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            /*------------ After Login Next View Call -------------*/
                            afterLoginViewCall();
                        } else {
                            // if the code is not correct then we are
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        if (progressDialog!=null && progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
    }

    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        new CountDownTimer(180000, 1000) {
            public void onTick(long millisUntilFinished) {
                //otpTimer.setText("" + millisUntilFinished / 1000);
                otpTimer.setText(String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)%60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60));
            }
            public void onFinish() {
                getOTP_BTN.setTag("0");
                otpTimer.setText("");
                otpTimer.setTag("0");
                mobile_num.setEnabled(true);
            }
        }.start();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            progressDialog.dismiss();
        }
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                otpTxt.setText(code);
                otpTimer.setTag(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    void afterLoginViewCall(){
        final SharedPreferences sharedPref = getActivity().getSharedPreferences("loginCredentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("mob_num", mobile_num.getText()+"");
        editor.apply();
        boolean resultInsert = newItemInsert("Rameshwar Singh",mobile_num.getText()+"");
        if (resultInsert) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();

//            Fragment fragment = new FoodListView();
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.replace(R.id.container, fragment);
//            transaction.commit();
        }
    }


    boolean newItemInsert(String name,String mobileNo){
        try {
            final SharedPreferences FirstTimeCredentials = getActivity().getSharedPreferences("FirstTimeCredentials", Context.MODE_PRIVATE);
            String storedFCMToken=FirstTimeCredentials.getString("storedFCMToken","");
            if(storedFCMToken.equals("")){
                throw new Exception("FCM device token not found");
            }
            LoginModel dataHolder=new LoginModel(name,mobileNo, HelperClass.dateTimeForPKey(),storedFCMToken);
            FirebaseDatabase db=FirebaseDatabase.getInstance();
            DatabaseReference user_nood=db.getReference("shopUser/");
            user_nood.child(mobileNo).setValue(dataHolder);
            return true;
        }catch (Exception e){
            Log.e("Item Insert Error:",e.toString());
            Toast.makeText(getContext(), "Sorry: "+e, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
