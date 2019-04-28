package com.google.codelabs.mdc.java.jionow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.auth.api.Auth;
=======
import android.widget.Toast;

>>>>>>> 1e033004180b455d016a1d2f8a75f17f3fa0b2ca
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;

/**
 * Fragment representing the login screen for Jio Now.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

<<<<<<< HEAD
    private static final String TAG = "JioNow";
    private int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private Button disconnectButton, signOutButton;
    private LinearLayout signOutView;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
=======
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
>>>>>>> 1e033004180b455d016a1d2f8a75f17f3fa0b2ca
    private GoogleSignInClient mGoogleSignInClient;

    private SignInButton signInButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, parent, false);

        signInButton = (SignInButton) v.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), signInOptions);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        updateUI(account);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount accounts = completedTask.getResult(ApiException.class);
            updateUI(accounts);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

<<<<<<< HEAD
    private void updateUI(GoogleSignInAccount signedIn) {

        ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the next Fragment
=======
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
>>>>>>> 1e033004180b455d016a1d2f8a75f17f3fa0b2ca
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            ((NavigationHost) getActivity()).navigateTo(new MainMenu(), false);
        } else {
            Toast toast=Toast.makeText(getActivity(),"Sign in failed. Try again!", Toast.LENGTH_SHORT);
            toast.show();
            new LoginFragment();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

}
