package com.folahan.unilorinapp.fragmentActivity;

import static androidx.appcompat.app.AlertDialog.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.folahan.unilorinapp.Activity.LoginActivity;
import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private SwitchCompat compat1, compat2;
    private SharedPreferences.Editor preferencesEditor;
    private PreferenceManager preferenceManager;
    private AlertDialog.Builder builder;
    private boolean isDarkModeOn;
    private MaterialCardView mcv, mcv2, mcv3, mcv4;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate_the_layout_for_this_fragment
        view =  inflater.inflate(R.layout.fragment_settings, container, false);
        mcv = view.findViewById(R.id.material_dark_mode);
        //mcv2 = view.findViewById(R.id.material_log_out);
        mcv3 = view.findViewById(R.id.materialShare);
        mcv4 = view.findViewById(R.id.material_about);
        builder = new Builder(requireActivity());
        //mcv2.setOnClickListener(task -> signOut());
        mcv4.setOnClickListener(view1 -> {
            builder.setTitle("Unilorin Scholar")
                    .setMessage(R.string.about_the_app)
                    .setPositiveButton("OK", ((dialogInterface, i) -> dialogInterface.cancel()));
            builder.show()
                    .setCancelable(true);
        });

        mcv3.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String shareBody = "Download the Unilorin Scholar App using the link below";
            String shareSubject = "https://drive.google.com/folderview?id=16tUbVbBZVGnyZn1GCyizAFNnjWKeGEM4";
            intent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
            intent.putExtra(Intent.EXTRA_TEXT, shareSubject);
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Share using"));
        });

        preferenceManager = new PreferenceManager(requireActivity().getApplicationContext());

        compat1 = view.findViewById(R.id.switch1);
        compat2 = view.findViewById(R.id.switch2);
        compat1.setChecked(false);
        SharedPreferences mPreferences = requireActivity().getSharedPreferences(
                "sharedPrefs", Context.MODE_PRIVATE);
        isDarkModeOn
                = mPreferences
                .getBoolean(
                        "isDarkModeOn", false);
        preferencesEditor
                = mPreferences.edit();

        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
            compat1.setChecked(true);
        } else if (!isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
            compat1.setChecked(false);
        }



        compat1.setOnClickListener(view -> {
            if (compat1.isChecked()) {
                AppCompatDelegate
                        .setDefaultNightMode(
                                AppCompatDelegate
                                        .MODE_NIGHT_YES);
                preferencesEditor.putBoolean(
                        "isDarkModeOn", true);
                preferencesEditor.apply();
            } else {
                AppCompatDelegate
                        .setDefaultNightMode(
                                AppCompatDelegate
                                        .MODE_NIGHT_NO);
                preferencesEditor.putBoolean(
                        "isDarkModeOn", false);
                preferencesEditor.apply();
            }
        });
        mcv.setOnClickListener(view -> {
            compat1.setChecked(true);
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
            preferencesEditor.putBoolean(
                    "isDarkModeOn", true);
            preferencesEditor.apply();
        });

        final Vibrator vibrator = (Vibrator) requireActivity().getSystemService(Context.VIBRATOR_SERVICE);


        compat2.setOnClickListener(view -> {
            final VibrationEffect effect;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                effect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);
                vibrator.cancel();
                vibrator.vibrate(effect);
            }
            
        });

        /*mcv2.setOnClickListener(view -> {
            builder = new Builder(requireActivity());
            builder.setTitle("Log Out")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", (dialog, which) -> signOut())
                    .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                    .setIcon(ContextCompat.getDrawable(requireActivity().getApplicationContext(),
                            R.drawable.ic_cancel)).show();
        });*/
        return view;
    }

    public void signOut() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS)
                        .document(preferenceManager.getString(Constants.KEY_USER_ID));
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    preferenceManager.clear();
                    startActivity(new Intent(view.getContext().getApplicationContext(),
                            LoginActivity.class));
                })
                .addOnFailureListener(e ->
                        Toast.makeText(view.getContext().getApplicationContext(), "Unable to sign out", Toast.LENGTH_SHORT).show());
    }



}