package com.prolandfarming.genericlogin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.prolandfarming.genericlogin.Models.User;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth =  FirebaseAuth.getInstance();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private boolean isTryingToSignUp = false;
    private EditText mNameView;
    private EditText mSurnameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private EditText mPhoneNumberView;
    private Spinner mTitleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mEmailView = findViewById(R.id.signup_textinputedittext_email);
        mTitleSpinner = findViewById(R.id.signup_title_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.title_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTitleSpinner.setAdapter(adapter);

        mNameView = findViewById(R.id.signup_textinputedittext_name);
        mSurnameView = findViewById(R.id.signup_textinputedittext_surname);
        mPasswordView = findViewById(R.id.signup_textinputedittext_password);

        //TODO do this for last one
        /*mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    createUser();
                    return true;
                }
                return false;
            }
        });*/

        mPhoneNumberView = findViewById(R.id.signup_textinputedittext_phone);


        Button mCreateUserButton = findViewById(R.id.signup_button_signup);
        mCreateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

        ImageButton mBackButton = findViewById(R.id.signup_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mProgressView = findViewById(R.id.signup_progress);
    }

    private void createUser(){
        if(isTryingToSignUp){
            return;
        }
        isTryingToSignUp = true;

        // Reset errors.
        mNameView.setError(null);
        mSurnameView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPhoneNumberView.setError(null);

        final String name = mNameView.getText().toString();
        final String surname = mSurnameView.getText().toString();
        final String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        final String phoneNumber = mPhoneNumberView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid phone number.
        if (!TextUtils.isEmpty(password) && !PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
            mPhoneNumberView.setError(getString(R.string.error_invalid_phone_number)); // TODO msg
            focusView = mPhoneNumberView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a valid email surname.
        if (TextUtils.isEmpty(surname)) {
            mSurnameView.setError(getString(R.string.error_field_required));
            focusView = mSurnameView;
            cancel = true;
        }

        // Check for a valid email name.
        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            isTryingToSignUp = false;
        } else {
            showProgress(true);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            isTryingToSignUp = false;
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("signup", "createUserWithEmail:success");
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                String userUID = firebaseUser.getUid();
                                boolean isMale = mTitleSpinner.getSelectedItemPosition() == 0;
                                User user = new User(userUID, email, name, surname, phoneNumber, isMale);
                                mDatabase.getReference("/users/"+userUID+"/").setValue(user);
                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                intent.putExtra(getString(R.string.INTENT_PARAM_KEY_USER), user);
                                startActivity(intent);
                                showProgress(false);
                                finish();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("signup" , "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignupActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                showProgress(false);
                            }
                        }
                    });
        }


    }

    /**
     * Shows the progress UI.
     */
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

}
