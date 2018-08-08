package com.paxcel.paxcel.bustarckingsystem.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.paxcel.paxcel.bustarckingsystem.R;
import com.paxcel.paxcel.bustarckingsystem.utils.SharedPreferencesUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PUBLIC_PROFILE = "public_profile";
    private static final int RC_SIGN_IN = 01;
    private static final  String TAG="LoginActivity";
    CallbackManager callbackManager;
    LoginButton loginButton;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onStart() {

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*Google sign in setup*/

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("677325696311-5tqjk1kldcvql83r426kvanmhjcu2tqm.apps.googleusercontent.com")
                .requestServerAuthCode("677325696311-5tqjk1kldcvql83r426kvanmhjcu2tqm.apps.googleusercontent.com")
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        /*Facebook  setup*/

        callbackManager = CallbackManager.Factory.create();

        /*Facebook sign in button*/

        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(PUBLIC_PROFILE));
        //loginButton.setLoginBehavior(LoginBehavior.WEB_ONLY);              // for restricting  login with fb to web only .


        /*Google sign in button*/

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.iv_google_login).setOnClickListener(LoginActivity.this);
        findViewById(R.id.iv_fb_login).setOnClickListener(LoginActivity.this);

        //findViewById(R.id.sign_in_button).setOnClickListener(LoginActivity.this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.e("Facebook", "ON success");

                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Log.e("data", object.toString());
                                try {

                                    String name =object.getString("name");
                                    SharedPreferencesUtils.getInstance(LoginActivity.this)
                                            .setValue("username",name);

                                    startActivity(new Intent(LoginActivity.this,TabActivity.class).
                                            putExtra("name",name));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,first_name");
                //parameters.putString("fields", "id,name,link,gender,email");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.e("Facebook", "ON onCancel");

            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("Facebook", " onError"+exception);

            }
        });
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);

            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                String idToken =acct.getIdToken();
                String idAuth =acct.getServerAuthCode();



                Log.e(TAG,personName+"    "+personEmail);
                Log.e(TAG,personId+"    "+idToken +"     "+idAuth);

                SharedPreferencesUtils.getInstance(LoginActivity.this)
                        .setValue("username",personName);

                startActivity(new Intent(LoginActivity.this,TabActivity.class).
                        putExtra("name",personName));
                finish();


            }



            // Signed in successfully, show authenticated UI.
          //  updateUI(account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(Object o) {
        Log.e(TAG,"Here");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_google_login:
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;

            case R.id.iv_fb_login :
                Log.e("Facebook", "ON here");
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,Arrays.asList(PUBLIC_PROFILE));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>()
                        {
                            @Override
                            public void onSuccess(LoginResult loginResult)
                            {
                                Log.e("Facebook", "ON success");

                                GraphRequest request = GraphRequest.newMeRequest(
                                        AccessToken.getCurrentAccessToken(),
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject object, GraphResponse response) {

                                                Log.e("data", object.toString());

                                                try {
                                                    String name =object.getString("name");

                                                    SharedPreferencesUtils.getInstance(LoginActivity.this)
                                                            .setValue("username",name);

                                                    startActivity(new Intent(LoginActivity.this,TabActivity.class).
                                                            putExtra("name",name));
                                                    finish();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id,name,link,first_name");
                                //parameters.putString("fields", "id,name,link,gender,email");
                                request.setParameters(parameters);
                                request.executeAsync();
                            }

                            @Override
                            public void onCancel()
                            {
                                Log.e("Facebook", "ON onCancel");

                            }

                            @Override
                            public void onError(FacebookException exception)
                            {
                                Log.e("Facebook", "ON onError"+exception);

                            }
                        });
                break;

        }

    }


}
