package com.folahan.unilorinapp.fragmentActivity;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.Model.Constants;
import com.folahan.unilorinapp.Model.PreferenceManager;
import com.folahan.unilorinapp.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivateFragment#} factory method to
 * create an instance of this fragment.
 */
public class ActivateFragment extends Fragment {

    RelativeLayout rl, relative;
    private ImageView mImageThumbUp, mImageThumbDown, mImageStar1, mImageStar2,
            mImageStar3, mImageStar4;
    private EditText edtPay;
    private Button mImagePay;
    private TextView mTextActivated, mTextNotActivated;
    private PreferenceManager manager;
    View view;

    public ActivateFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        PreferenceManager preferenceManager = new PreferenceManager(requireActivity().getApplicationContext());
        view =  inflater.inflate(R.layout.fragment_activate, container, false);
        mTextActivated = view.findViewById(R.id.txtActivated);
        mImagePay = view.findViewById(R.id.search_payment);
        edtPay = view.findViewById(R.id.edtPayment);
        manager = new PreferenceManager(requireActivity().getApplicationContext());
        mTextNotActivated = view.findViewById(R.id.txtNotActivated);
        mImageThumbUp = view.findViewById(R.id.imgThumbUp);
        mImageThumbDown = view.findViewById(R.id.imgThumbDown);
        mImageStar1 = view.findViewById(R.id.star1);
        mImageStar2 = view.findViewById(R.id.star2);
        mImageStar3 = view.findViewById(R.id.star3);
        mImageStar4 = view.findViewById(R.id.star4);
        mImagePay.setOnClickListener(view1 -> {
            String message = edtPay.getText().toString();
            if (message.equals(preferenceManager.getString(Constants.KEY_PAID))) {
                Toast.makeText(requireActivity(), "Payment Done", Toast.LENGTH_SHORT).show();
                get();
                manager.putBoolean(Constants.KEY_TRUE_PAID, true);
            } else {
                Toast.makeText(requireActivity(), "Pls meet with the developer and get your pin", Toast.LENGTH_LONG).show();
            }
        });
        CardView mcv2 = view.findViewById(R.id.mCardViewBank);


        relative = view.findViewById(R.id.rlTransfer);

        mcv2.setOnClickListener(view -> {
            int isVisible = relative.getVisibility();
            if (isVisible==View.VISIBLE) {
                relative.setVisibility(View.GONE);
            } else if (isVisible==View.GONE) {
                relative.setVisibility(View.VISIBLE);
            }
        });

        if (manager.getBoolean(Constants.KEY_TRUE_PAID)) {
            get();
        }

        return view;
    }

    private void get() {

            mTextNotActivated.setVisibility(View.GONE);
            mTextActivated.setVisibility(View.VISIBLE);
            mImageThumbUp.setVisibility(View.VISIBLE);
            mImageThumbDown.setVisibility(View.INVISIBLE);
            mImageStar1.setVisibility(View.VISIBLE);
            mImageStar2.setVisibility(View.VISIBLE);
            mImageStar3.setVisibility(View.VISIBLE);
            mImageStar4.setVisibility(View.VISIBLE);
            edtPay.setVisibility(View.INVISIBLE);
            mImagePay.setText("ACTIVATED");
            mImagePay.setClickable(false);
        }
}