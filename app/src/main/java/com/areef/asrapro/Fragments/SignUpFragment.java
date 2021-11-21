package com.areef.asrapro.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.areef.asrapro.MainActivity;
import com.areef.asrapro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAnAccount;
    private FrameLayout parentFrameLayout;

    private Button signUpBtn;
    private ImageButton closeBtn;
    private EditText InputEmail, InputName, InputPhoneNumber, InputPassword, InputConfirmPassword;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public static boolean disableCloseBtn = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        alreadyHaveAnAccount = view.findViewById(R.id.tv_already_have_an_account);
        signUpBtn = view.findViewById(R.id.sign_up_btn);
        closeBtn = view.findViewById(R.id.sign_up_close_btn);

        InputEmail = view.findViewById(R.id.sign_up_email);
        InputName = view.findViewById(R.id.sign_up_name);
        InputPhoneNumber = view.findViewById(R.id.sign_up_phone_number);
        InputPassword = view.findViewById(R.id.sign_up_password);
        InputConfirmPassword = view.findViewById(R.id.sign_up_confirm_password);

        progressBar = view.findViewById(R.id.sign_up_progress_bar);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        if (disableCloseBtn) {
            closeBtn.setVisibility(View.GONE);
        } else {
            closeBtn.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();

            }
        });
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        InputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        InputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        InputPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        InputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        InputConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();

            }
        });
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkInputs() {
        if (!TextUtils.isEmpty(InputEmail.getText())) {

            if (!TextUtils.isEmpty(InputName.getText())) {

                if (!TextUtils.isEmpty(InputPhoneNumber.getText())) {

                    if (!TextUtils.isEmpty(InputPassword.getText()) && InputPassword.length() >= 8) {

                        if (!TextUtils.isEmpty(InputConfirmPassword.getText())) {
                            signUpBtn.setEnabled(true);
                            signUpBtn.setTextColor(Color.rgb(255, 255, 255));

                        } else {
                            signUpBtn.setEnabled(false);
                            signUpBtn.setTextColor(Color.argb(50f, 255, 255, 255));
                        }
                    } else {
                        signUpBtn.setEnabled(false);
                        signUpBtn.setTextColor(Color.argb(50f, 255, 255, 255));
                    }
                } else {
                    signUpBtn.setEnabled(false);
                    signUpBtn.setTextColor(Color.argb(50f, 255, 255, 255));
                }
            } else {
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50f, 255, 255, 255));
            }
        } else {
            signUpBtn.setEnabled(false);
            signUpBtn.setTextColor(Color.argb(50f, 255, 255, 255));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkEmailAndPassword() {

        if (InputEmail.getText().toString().matches(emailPattern)) {

            if (InputPassword.getText().toString().equals(InputConfirmPassword.getText().toString())) {

                progressBar.setVisibility(View.VISIBLE);
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50f, 255, 255, 255));

                firebaseAuth.createUserWithEmailAndPassword(InputEmail.getText().toString(), InputPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> userdata = new HashMap<>();
                            userdata.put("name", InputName.getText().toString());
                            userdata.put("email", InputEmail.getText().toString());
                            userdata.put("profile", "");
                            userdata.put("phone", InputPhoneNumber.getText().toString());
                            userdata.put("password", InputPassword.getText().toString());
                            firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                    .set(userdata)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        CollectionReference userDataReference = firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA");


                                        ///Maps
                                        Map<String, Object> wishlistMap = new HashMap<>();
                                        wishlistMap.put("list_size", (long) 0);

                                        Map<String, Object> ratingsMap = new HashMap<>();
                                        ratingsMap.put("list_size", (long) 0);

                                        Map<String, Object> cartMap = new HashMap<>();
                                        cartMap.put("list_size", (long) 0);

                                        Map<String, Object> myAddressesMap = new HashMap<>();
                                        myAddressesMap.put("list_size", (long) 0);

                                        Map<String, Object> notificationsMap = new HashMap<>();
                                        notificationsMap.put("list_size", (long) 0);

                                        ///Maps

                                        final List<String> documentNames = new ArrayList<>();
                                        documentNames.add("MY_WISHLIST");
                                        documentNames.add("MY_RATINGS");
                                        documentNames.add("MY_CART");
                                        documentNames.add("MY_ADDRESSES");
                                        documentNames.add("MY_NOTIFICATIONS");

                                        List<Map<String, Object>> documentFields = new ArrayList<>();
                                        documentFields.add(wishlistMap);
                                        documentFields.add(ratingsMap);
                                        documentFields.add(cartMap);
                                        documentFields.add(myAddressesMap);
                                        documentFields.add(notificationsMap);

                                        for (int x= 0; x < documentNames.size(); x++) {

                                            final int finalX = x;
                                            userDataReference.document(documentNames.get(x))
                                                    .set(documentFields.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if(finalX == documentNames.size() -1) {
                                                            mainIntent();
                                                        }

                                                    } else {
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        signUpBtn.setEnabled(true);
                                                        signUpBtn.setTextColor(Color.rgb(255, 255, 255));
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });
                                        }
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            signUpBtn.setEnabled(true);
                            signUpBtn.setTextColor(Color.rgb(255, 255, 255));
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                progressBar.setVisibility(View.VISIBLE);

            } else {
                InputConfirmPassword.setError("Password doesn't match!");
            }

        } else {
            InputEmail.setError("Invalid Email!");

        }
    }

    private void mainIntent() {
        if (disableCloseBtn) {
            disableCloseBtn = false;
        }else {
            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainIntent);
        }
        getActivity().finish();

    }

}
