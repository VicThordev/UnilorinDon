package com.folahan.unilorinapp.Activity.Questions.SecondSemester100l;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import java.util.Random;

public class Chm132Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1;
    Button btnNext, btnPrev, btnEnd;
    private AlertDialog dialog;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chm132);

        questionList = new ArrayList<>();
        questionText = findViewById(R.id.questionText);
        btnEnd = findViewById(R.id.buttonGoto);
        rbOption1 = findViewById(R.id.radioA);
        rbOption2 = findViewById(R.id.radioB);
        rbOption3 = findViewById(R.id.radioC);
        rbOption4 = findViewById(R.id.radioD);
        questionNo = findViewById(R.id.question1);
        countDown = findViewById(R.id.timeText);
        random = new Random();

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

        getQuestionPhase(questionList);

        setDataView(pos);
        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.button_previous);

        btnNext.setOnClickListener(view -> {
            questionAnswered++;
            pos = random.nextInt(questionList.size());
            setDataView(pos);
        });

        setListeners();

        btnNext.setOnClickListener(view -> {
            questionAnswered++;
            pos = random.nextInt(questionList.size());
            setDataView(pos);
        });

    }

    private void setListeners() {
        rbOption1.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption1.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
        });

        rbOption2.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption2.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
        });

        rbOption3.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption3.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
        });

        rbOption4.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption4.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
        });

        btnEnd.setOnClickListener(view -> dialogAlert());
    }

    private void dialogAlert() {
        dialog = new AlertDialog.Builder(this, androidx.appcompat.R.style.ThemeOverlay_AppCompat_ActionBar)
                .setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit? \n You answered "+questionAnswered+" out of 30 questions")
                .setPositiveButton("Yes", (dialog, which) -> {
                    showButton();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setIcon(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.ic_login)).show();
    }

    protected void showButton() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet,
                (LinearLayout) findViewById(R.id.design_bottom_sheet));
        TextView scoreShow = bottomSheet.findViewById(R.id.score);
        Button goHome = bottomSheet.findViewById(R.id.btnScore);

        scoreShow.setText("Your score is \n"+pos2+" out of 30");

        goHome.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
            dialog.dismiss();
            finish();
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

        questionNo.setText("Question "+questionAnswered+" of 30");
        if (questionAnswered == 30) {
            showButton();
        }

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("The ligands that donate more than one electron pairs is called",
                "(a) Lewis acid",
                "(b) Monodentate",
                "(c) Multidentate",
                "(d) Lewis base",
                "(c) Multidentate"));

        questionList.add(new Question("The number of bonds attached to the central metal atom is called…",
                "(a) oxidation number",
                "(b) electrovalent number",
                "(c) atomic number",
                "(d) Coordination number",
                "(d) Coordination number"));

        questionList.add(new Question("The following are examples of ligands except",
                "(a) H2O",
                "(b) Br",
                "(c) Be",
                "(d) Cl",
                "(c) Be"));

        questionList.add(new Question("Transition metals are paramagnetic because of",
                "(a) Possession of paired electron",
                "(b) Possession of unpaired electron",
                "(c) They are coloured",
                "(d) They are amphoteric",
                "(b) Possession of unpaired electron"));

        questionList.add(new Question("Which of the following groups of physical properties increase from left to right " +
                "of the periodic table? \n (1) Atomic energy (2) electronegativity (3) electron affinity (4) Atomic " +
                "radius.",
                "(a) 1 & 2",
                "(b) 2 & 3 ",
                "(c) 1, 2 & 3",
                "(d) All of the above",
                "(b) 2 & 3 "));

        questionList.add(new Question("State which elements in the series Na, Mg, Al, Si, S and Cl has the highest " +
                "melting point?",
                "(a) Na",
                "(b) Mg",
                "(c) S" ,
                "(d) Cl", "(b) Mg"));

        questionList.add(new Question("Water molecules can be ligands especially when they are bonded to",
                "(a) Transition metal", "(b) alkali metals",
                "(c) alkaline metals", "(d) Group V elements.",
                "(a) Transition metal"));

        questionList.add(new Question("The bond formed between lithium and fluorine is",
                "(a) covalent", "(b) hydrogen bond",
                "(c) ionic bond",
                "(d) dative bond.",
                "(c) ionic bond"));

        questionList.add(new Question("Group 1A metals are not formed in nature because they",
                "(a) fluorite and ZnS",
                "(b) Zinc blende & wurtzite",
                "(c) ZnS & corundum",
                "(d) all of the above",
                "(b) Zinc blende & wurtzite"));

        questionList.add(new Question("Aluminium is trivalent element because",
                "(a) it is a metal",
                "(b) it is a non-metal",
                "(c) its has a valency of three",
                "(d) it is electropositive", "(c) its has a valency of three"));

        questionList.add(new Question("Silica exists in six different crystalline forms as",
                "(a) quartz",
                "(b) Rutile" ,
                "(c) fluorite",
                "(d) none of the above",
                "(a) quartz"));

        questionList.add(new Question( "Cadmium chloride occur in which of this arrangement?",
                "(a) ABABAB",
                "(b) ACBACB",
                "(c) ABCABC",
                "(d) AABBCC",
                "(c) ABCABC"));

        questionList.add(new Question( "It is possible to measure the interatomic distance between two different ion " +
                "very accurately by.",
                "(a) Mass spectroscopy",
                "(b) Infrared spectroscopy",
                "(c) X-ray crystallrgraphy",
                "(d) all of the above.",
                "(c) X-ray crystallrgraphy"));

        questionList.add(new Question( "Calculate the dipole moment of a charge on an electron of 4.8 x 10-10 esu ",
                "(a) 4.8 x 10-18 esu.cm",
                "(b) 1.44 x 10-17 esu.cm",
                "(c) 1.44 x 10-19 esu.cm ",
                "(d) " +
                        "4.8 x 10-19 esu.cm",
                "(c) 1.44 x 10-19 esu.cm "));

        questionList.add(new Question( "Antimony does not obey the octet because its valence electron is",
                "(a) 5",
                "(b) 6",
                "(c) 8",
                "(d) 7",
                "(a) 5"));

        questionList.add(new Question( "When the electronegative difference is large, the bonding is….",
                "(a) ionic",
                "(b) covalent",
                "(c) dative",
                "(d) Metallic",
                "(a) ionic"));

        questionList.add(new Question( "Which of the following molecule does not exhibit resonance?",
                "(a) N2O",
                "(b) SO2",
                "(c) HNO3",
                "(d) NH3",
                "(d) NH3"));

        questionList.add(new Question( "Which of the following can not be used to determine bonding?",
                "(a) electron affinity",
                "(b) resonance",
                "(c) electronegativity",
                "(d) Ionization energy",
                "(b) resonance"));

        questionList.add(new Question( "Tetrahedral compounds differ from square planar in ",
                "(a) bond angle",
                "(b) coordination number",
                "(c) quantum number",
                "(d) oxidation number",
                "(a) bond angle"));

        questionList.add(new Question( "What is the coordination number of a square pyramid shape?",
                "(a) 3",
                "(b) 5",
                "(c) 6",
                "(d) 4",
                "(b) 5"));

        questionList.add(new Question( "The hybridization of a trigonal bipyramidal shape is",
                "(a) SP2d",
                "(b) SP3d",
                "(c) SP2d2",
                "(d) SP3d2",
                "(c) SP2d2"));

        questionList.add(new Question( "Both square planar and octahedral compounds will exhibit.",
                "(a) Ionization",
                "(b) oxidation",
                "(c) Isomerism",
                "(d) reduction",
                "(c) Isomerism"));

        questionList.add(new Question( "Valence bond theory fails to explain which of the following properties?",
                "(a) magnetic",
                "(b) electrical",
                "(c) physical",
                "(d) all of the above",
                "(a) magnetic"));

        questionList.add(new Question( "The similarity between tetrahedral and square planar compound is that both " +
                "have",
                "(a) a coordination Number of 2",
                "(b) a coordination number of 4",
                "(c) a coordination Number of 6",
                "(d) a coordination number of 3",
                "(b) a coordination number of 4"));

        questionList.add(new Question( "The following are van der waals forces except",
                "(a) Dipole-Dipole",
                "(b) Dipole-induced dipole",
                "(c) induced dipole – induced dipole",
                "(d) ion - dipole",
                "(d) ion - dipole"));

        questionList.add(new Question( "In acetic acid, the bond connecting the atoms together is …..",
                "(a) hydrogen bond",
                "(b) dative bond",
                "(c) metallic bond",
                "(d) ionic bond",
                "(a) hydrogen bond"));

        questionList.add(new Question( "The bond in a complex can be",
                "(a) ionic",
                "(b) covalent",
                "(c) dative",
                "(d) metallic",
                "(c) dative"));

        questionList.add(new Question( "The hybridization of carbon in ethylene is",
                "(a) Sp3",
                "(b) SP2",
                "(c) SP",
                "(d) SP2d",
                "(b) SP2"));

        questionList.add(new Question( "What are the permitted values for the azimuthal quantum number (L) for an " +
                "electron with a principal quantum number n 5?",
                "(a) 0,1",
                "(b) 2,3",
                "(c) 4",
                "(d) all of the above",
                "(d) all of the above"));

        questionList.add(new Question( "Which of the following represents a reasonable set of quantum number for " +
                "a 3d electrons?",
                "(a) 3, 2, 1, ½",
                "(b) 3, 0, 2, -1/2",
                "(c) a & b",
                "(d) Neither of these",
                "(c) a & b"));
    }
}