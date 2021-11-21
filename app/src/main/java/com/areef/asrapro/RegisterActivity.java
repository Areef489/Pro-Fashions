package com.areef.asrapro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.areef.asrapro.Fragments.SignInFragment;
import com.areef.asrapro.Fragments.SignUpFragment;

public class RegisterActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    public static boolean setSignUpFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        frameLayout = findViewById(R.id.register_framelayout);
        if (setSignUpFragment) {
            setSignUpFragment = false;
            setFragment(new SignUpFragment());
        } else {
            setFragment(new SignInFragment());
        }

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
