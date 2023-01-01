package com.folahan.unilorinapp.Activity.Questions.SecondSemester100l;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.MainActivity;
import com.folahan.unilorinapp.Model.Question;
import com.folahan.unilorinapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Chm112Activity extends AppCompatActivity {

    private List<Question> questionList;
    private TextView questionText, questionNo, countDown, answerText;
    private ImageView mImageView;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1, questionDone;
    Button btnNext, btnPrev, btnEnd;
    private AlertDialog.Builder dialog;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chm112);

        questionList = new ArrayList<>();
        questionText = findViewById(R.id.questionText);
        btnEnd = findViewById(R.id.buttonGoto);
        mImageView = findViewById(R.id.imageQuestion);
        answerText = findViewById(R.id.txtAnswer);
        rbOption1 = findViewById(R.id.radioA);
        rbOption2 = findViewById(R.id.radioB);
        rbOption3 = findViewById(R.id.radioC);
        rbOption4 = findViewById(R.id.radioD);
        questionNo = findViewById(R.id.question1);
        countDown = findViewById(R.id.timeText);

        timer = new CountDownTimer(mTimeLeft,1000) {
            @Override
            public void onTick(long l) {
                mTimeLeft = (int) l;
                int minutes = (int) (mTimeLeft/1000) / 60;
                int secs = (int) (mTimeLeft/1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, secs);
                countDown.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                showButton();
            }
        }.start();

        mTimerRunning = true;

        setListeners();

        if (SecondSemesterActivity.questionRequestCode == 1) {
            getQuestionPhase(questionList);

            setDataView(pos);
        } else if (SecondSemesterActivity.questionRequestCode == 2) {
            getQuestionPhase2(questionList);

            setDataView(pos);
        } else if (SecondSemesterActivity.questionRequestCode == 3) {
            getQuestionPhase3(questionList);

            setDataView(pos);
        }
        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.button_previous);

        btnNext.setOnClickListener(view -> {
            if (questionAnswered == 40) {
                Toast.makeText(this, "Last Question", Toast.LENGTH_SHORT).show();
            } else {
                questionAnswered++;
                pos++;
                setDataView(pos);
            }
        });

        btnPrev.setOnClickListener(view -> {
            if (questionAnswered == 1) {
                Toast.makeText(this, "First Question", Toast.LENGTH_SHORT).show();
            } else {
                questionAnswered--;
                pos--;
                setDataView(pos);
            }
        });
    }

    private void setListeners() {
        rbOption1.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption1.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            questionDone++;
        });

        rbOption2.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption2.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            questionDone++;
        });

        rbOption3.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption3.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            questionDone++;
        });

        rbOption4.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption4.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            questionDone++;
        });

        btnEnd.setOnClickListener(view -> dialogAlert());
    }

    private void dialogAlert() {
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit? \n You answered "+questionAnswered+" out of 30 questions")
                .setPositiveButton("Yes", (dialog, which) -> {
                    showButton();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setIcon(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.ic_cancel)).show();
    }

    protected void showButton() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet,
                findViewById(R.id.design_bottom_sheet));
        TextView scoreShow = bottomSheet.findViewById(R.id.score);
        Button goHome = bottomSheet.findViewById(R.id.btnScore);
        Button showAnswer = bottomSheet.findViewById(R.id.btnAnswer);

        scoreShow.setText("Your score is \n"+pos2+" out of 30");

        goHome.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
            dialog.dismiss();
            finish();
        });

        showAnswer.setOnClickListener(view -> {
            timer.cancel();
            answerText.setVisibility(View.VISIBLE);
            rbOption1.setVisibility(View.GONE);
            rbOption2.setVisibility(View.GONE);
            rbOption3.setVisibility(View.GONE);
            rbOption4.setVisibility(View.GONE);
            answerText.setText(R.string.log_out);
            answerText.setText(questionList.get(pos).getAnswer());
            rbOption1.setVisibility(View.GONE);
            dialog.cancel();
        });
        dialog.setCancelable(false);
        dialog.setContentView(bottomSheet);
        dialog.show();
    }

    private void setDataView(int position) {
        questionText.setText(questionList.get(position).getQuestion());


        rbOption1.setText(questionList.get(position).getOption1());
        rbOption2.setText(questionList.get(position).getOption2());
        rbOption3.setText(questionList.get(position).getOption3());
        rbOption4.setText(questionList.get(position).getOption4());
        answerText.setText(questionList.get(position).getAnswer());
        mImageView.setImageResource(questionList.get(position).getImage());

        questionNo.setText("Question "+questionAnswered+" of 30");

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("1. _____ is an example of a compound that exhibits the chain isomerism",
                "A. Propyl Bromide",
                "B. Isopropyl",
                "C. Butane",
                "D. Di ethyl ether",
                "C. Butane"));

        questionList.add(new Question("2. Another name for chirality centre is _____",
                "A. Chiral Isomers",
                "B. Meta Isomers",
                "C. Asymmetric carbon",
                "D. Chain isomer",
                "C. Asymmetric carbon"));

        questionList.add(new Question("3. Isomers that are unidentical are called _____",
                "A. enantiomers",
                "B. potentiomers",
                "C. asymmetric isomers",
                "D. chiral centres",
                "A. enantiomers"));

        questionList.add(new Question("4. What is the absolute configuration of the molecule shown.",
                "A. This molecule is achiral",
                "B. R",
                "C. S",
                "D. 1S, 2R",
                "C. S",
                R.drawable.chm112_snip));

        questionList.add(new Question("5. What is the IUPAC name of the molecule shown?",
                "A. Z-5-Carboxy-2-pentene",
                "B. E-Pent-3-enoic acid",
                "C. E-5-Carboxy-2-pentene",
                "D. Z-pent-3-enoic acid",
                "D. Z-pent-3-enoic acid",
                R.drawable.chm112snip1));

        questionList.add(new Question("6. Which of the following differences would never describe isomers?",
                "A. Different molar masses",
                "B. Different absolute configuration",
                "C. Different rotation of plane-polarized light",
                "D. Different boiling points",
                "A. Different molar masses"));

        questionList.add(new Question("7. How many stereoisomers are possible for methylcyclopropane?",
                "A. 3", "B. 2",
                "C. 0", "D. 4",
                "C. 0"));

        questionList.add(new Question("8. How many constitutional isomers exist for the general molecular formula C4H9OH",
                "A. 3", "B. 2",
                "C. 4",
                "D. 5",
                "C. 4"));

        questionList.add(new Question("9. How many stereoisomers exist for the given compound?",
                "A. 4",
                "B. 8",
                "C. 16",
                "D. 2",
                "A. 4",
                R.drawable.chm112snip2));

        questionList.add(new Question("10. ____ is an instrument that is used to measure the amount that an optically active compound " +
                "rotates the plane of polarized light.",
                "A. Analyzer",
                "B. Polarimeter",
                "C. Condenser",
                "D. Chiralizer", "B. Polarimeter"));

        questionList.add(new Question("11. A phenomenon observed when a molecule rotates the plane of polarization.",
                "A. Enantiomer",
                "B. Optical rotation" ,
                "C. Polarization",
                "D. Chiral Analysis",
                "B. Optical rotation"));

        questionList.add(new Question( "12. What is the orientation of the given molecule?",
                "",
                "B. R",
                "C. E",
                "D. Z",
                "A. S",
                R.drawable.chm112snip3));

        questionList.add(new Question( "13. What is the absolute configuration of the molecule shown?",
                "A. 1S,3S",
                "B. 1R,3R",
                "C. 2R,1S",
                "D. 1S,3R",
                "A. 1S,3S",
                R.drawable.chm112snip4));

        questionList.add(new Question( "14. ____ is the component of a polarimeter that allows the angle of rotation of plane polarized " +
                "light to be determined.",
                "A. Condenser",
                "B. Analyzer",
                "C. Centroid",
                "D. Chiral",
                "B. Analyzer"));

        questionList.add(new Question( "15. ______ are isomers in which the carbon chain remains but the functional groups " +
                "are unchanged.",
                "A. Position isomers",
                "B. Meta isomer",
                "C. Chain isomer",
                "D. Tautomerism",
                "A. Position isomers"));
    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question("1. The following are features of optical isomers except _____",
                "A. Stereogenic centre",
                "B. Asysmetric carbon",
                "C. Stereocentre carbon",
                "D. Enantiomer",
                "D. Enantiomer"));

        questionList.add(new Question("2. The following are examples of tautomerism except _____",
                "A. Amine",
                "B. Lactam-Lactim",
                "C. Propanal",
                "D. Enamine",
                "C. Propanal"));

        questionList.add(new Question("3. What is the absolute configuration of the molecule shown?",
                "A. R",
                "B. S",
                "C. The molecule is achiral",
                "D. None of the above",
                "A. R",
                R.drawable.chm112snip5));

        questionList.add(new Question("4. Which of the following applies to the molecule shown?",
                "A. 3R",
                "B. 2R",
                "C. 2S",
                "D. 4R",
                "B. 2R",
                R.drawable.chm112snip6));

        questionList.add(new Question("4. Which of the following applies to the molecule shown?",
                "A. 3R",
                "B. 2R",
                "C. 2S",
                "D. 4R",
                "B. 2R"));

        questionList.add(new Question("5. Which of the following is the correct IUPAC name for the given compound?",
                "A. Z-5-methyl-2-octene",
                "B. E-4-methyl-2-octene",
                "C. E-5-methyl-2-octene",
                "D. Z-4-methyl-2-octene",
                "D. Z-4-methyl-2-octene",
                R.drawable.chm112snip7));

        questionList.add(new Question("6. The observed rotation of 2.0 g of a compound in 10mL of solution in a polarimeter tube 25cm long is +134. " +
                "What is the specific rotation of the compound?",
                "A. +134",
                "B. +268",
                "C. -175",
                "D. -189",
                "B. +268"));

        questionList.add(new Question("7. A mixture of equal amount of a pair of enantiomers is called _____",
                "A. polarized mixture",
                "B. racemic mixture",
                "C. chiral mixture",
                "D. multi mixture",
                "B. racemic mixture"));

        questionList.add(new Question("8. Give the IUPAC name of this compound",
                "A. Cis-1,2-dichloroethene",
                "B. Dichloro ethene",
                "C. trans-1,2-dichloroethene",
                "D. trans-1,2-dichloroethane",
                "C. trans-1,2-dichloroethene",
                R.drawable.chm112snip8));

        questionList.add(new Question("9. _____ is the existence of two or more compounds having the same " +
                "molecular formula but different structural formula.",
                "A. Isomerism",
                "B. Allotropy",
                "C. Chirality",
                "D. Acidity",
                "A. Isomerism"));

        questionList.add(new Question("9. _____ is the existence of two or more compounds having the same " +
                "molecular formula but different structural formula.",
                "A. Isomerism",
                "B. Allotropy",
                "C. Chirality",
                "D. Acidity",
                "A. Isomerism"));

        questionList.add(new Question("10. _____ is a plane that cuts a molecule into two halves.",
                "A. plane of symmetry",
                "B. angular plane",
                "C. Chiral line",
                "D. Identity line",
                "A. plane of symmetry"));

        questionList.add(new Question("11. Why does benzene resist addition reaction?",
                "A. No loose end for molecule",
                "B. strong sigma bond",
                "C. due to the decolorization of pi electron charge",
                "D. the structure do not allow for addition reaction",
                "C. due to the decolorization of pi electron charge"));

        questionList.add(new Question("12. Why do alynes not show geometric isomerism",
                "A. presence of the linear bond",
                "B. Highly unsaturated",
                "C. Substitution reaction not allowed",
                "D. None of the above",
                "B. Highly unsaturated"));

        questionList.add(new Question("13. What does LNG stands for?",
                "A. Liquefied natural gas",
                "B. Lake Nitrogen Gas",
                "C. Lime Nitrozol Grain",
                "D. None of the above",
                "A. Liquefied natural gas"));

        questionList.add(new Question("14. Which of the following equation correctly explains the chlorination of methane?",
                "A. CH₄ + Cl₂ = CH₃Cl + HCl",
                "B. CH₄ + 2O₂ = 2H₂O + CO₂",
                "C. CH₄ + 2Cl₂ = CH₃Cl + HCl",
                "D. CH₄ + 4O₂ = 2H₂O + CO₂",
                "A. CH₄ + Cl₂ = CH₃Cl + HCl"));

        questionList.add(new Question("15. Impure benzoic acid can be purified by ",
                "A. chlorination of the solution",
                "B. dissolving it in hot water and passing it over activated charcoal",
                "C. Frasch Process",
                "D. by the addition of sodium ethanote in presence of a strong catalyst",
                "B. dissolving it in hot water and passing it over activated charcoal"));
    }

    private void getQuestionPhase3(List<Question> list) {

        questionList.add(new Question("1. Nucleophile can also be classified as _____",
                "A. Lewis Acid",
                "B. Elctron acceptor",
                "C. Electrophile",
                "D. Lewis Base",
                "D. Lewis Base"));

        questionList.add(new Question("2. Mild oxidation of alkanol will result to",
                "A. Alkanoic acid",
                "B. Alkanone",
                "C. Ester",
                "D. Alkanes",
                "A. Alkanoic acid"));

        questionList.add(new Question("3. Electrophile can also be classified as _____",
                "A. Lewis Acid",
                "B. Lewis Base",
                "C. Nucleophile",
                "D. Electron donor",
                "A. Lewis Acid"));

        questionList.add(new Question("4. Decarboxylation of ethanoic acid gives",
                "A. C₂H₅OH",
                "B. CH₄ + CO₂",
                "C. CH₃COOC₂H₅",
                "D. CH₄ + H₂O",
                "B. CH₄ + CO₂"));

        questionList.add(new Question("%. ",
                "A. C₂H₅OH",
                "B. CH₄ + CO₂",
                "C. CH₃COOC₂H₅",
                "D. CH₄ + H₂O",
                "B. CH₄ + CO₂"));
    }


}

