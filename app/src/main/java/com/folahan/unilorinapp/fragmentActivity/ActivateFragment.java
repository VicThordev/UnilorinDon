package com.folahan.unilorinapp.fragmentActivity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.folahan.unilorinapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivateFragment extends Fragment {

    RelativeLayout rl, relative;

    private CardView mcv, mcv2, mcv3;

    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public ActivateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_activate, container, false);

        mcv = view.findViewById(R.id.mCardViewPaystack);
        mcv2 = view.findViewById(R.id.mCardViewBank);
        mcv3 = view.findViewById(R.id.mCardViewNoBank);

        rl = view.findViewById(R.id.relativeNoAcct);
        relative = view.findViewById(R.id.rlTransfer);

        mcv2.setOnClickListener(view -> {
            int isVisible = relative.getVisibility();
            if (isVisible==View.VISIBLE) {
                relative.setVisibility(View.GONE);
            } else if (isVisible==View.GONE) {
                relative.setVisibility(View.VISIBLE);
            }
        });

        mcv3.setOnClickListener(view -> {
            int isVisible = rl.getVisibility();
            if (isVisible==View.VISIBLE) {
                rl.setVisibility(View.GONE);
            } else if (isVisible==View.GONE) {
                rl.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }
}