package com.folahan.unilorinapp.fragmentActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import android.view.View;

import com.folahan.unilorinapp.R;
import com.folahan.unilorinapp.databinding.FragmentSettingsBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    private FragmentSettingsBinding binding;
    private View view;

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate_the_layout_for_this_fragment
        view =  inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }*/

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_xml, rootKey);
    }

}