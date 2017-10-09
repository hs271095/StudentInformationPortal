package bangalore.bms.studentinformationportal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegisterPage extends AppCompatActivity {
    Button register,submit;
    TextView con,pno,em,pass,ph,head;
    String company,pan,u,p,mobile;
    Context ctx=this;

    EditText name, usn, email, password, phone,otp;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    boolean mVerificationInProgress = false;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        name = (EditText) findViewById(R.id.et_com_name);
        usn = (EditText) findViewById(R.id.et_pan);
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        phone = (EditText) findViewById(R.id.et_phone);
        con=(TextView)findViewById(R.id.linearLayout5);
        em=(TextView)findViewById(R.id.constraintLayout3);
        ph=(TextView)findViewById(R.id.phone);
        pass= (TextView) findViewById(R.id.password);
        pno=(TextView)findViewById(R.id.linearLayout6);
        submit=(Button)findViewById(R.id.buttonVerifyOTP);
        head=(TextView)findViewById(R.id.textView13);
        otp=(EditText)findViewById(R.id.otp);
        register = (Button) findViewById(R.id.register_but);
        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                //Log.d(TAG, "onVerificationCompleted:" + credential);
                Toast.makeText(RegisterPage.this, "Verification done", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                //Log.w(TAG, "onVerificationFailed", e);
                Toast.makeText(RegisterPage.this, "Verification failed", Toast.LENGTH_SHORT).show();
                finish();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                    Toast.makeText(RegisterPage.this, "Verification failed Invalid Mob.no", Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    Toast.makeText(RegisterPage.this, "Verification failed SMS QUOTA exceeded", Toast.LENGTH_SHORT).show();
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
               // Log.d(TAG, "onCodeSent:" + verificationId);
                Toast.makeText(RegisterPage.this, "Code sent to Registered Mobile No", Toast.LENGTH_SHORT).show();
                con.setVisibility(View.GONE);
                usn.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
                email.setVisibility(View.GONE);
                pass.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                ph.setVisibility(View.GONE);
                pno.setVisibility(View.GONE);
                head.setVisibility(View.GONE);
                register.setVisibility(View.GONE);
                em.setVisibility(View.GONE);
                phone.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
                otp.setVisibility(View.VISIBLE);


                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // ...
            }
        };

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                company=name.getText().toString();
                pan=usn.getText().toString();
                u=email.getText().toString();
                p=password.getText().toString();
                mobile=phone.getText().toString();
                String type = "Register";
                BackgroundWorker backgroundWorker = new BackgroundWorker(ctx);
                backgroundWorker.execute(type, company, pan,u,p,mobile);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "91"+mobile,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        RegisterPage.this,  // Activity (for callback binding)
                        mCallbacks);        // OnVerificationStateChangedCallbacks

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp.getText().toString());
                signInWithPhoneAuthCredential(credential);
            }
        });


    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(RegisterPage.this, "Verification Succesful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();


                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                           //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(RegisterPage.this, "Verification failed code Invalid", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }

}

