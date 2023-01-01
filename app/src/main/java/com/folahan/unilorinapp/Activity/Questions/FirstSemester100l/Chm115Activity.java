package com.folahan.unilorinapp.Activity.Questions.FirstSemester100l;

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

public class Chm115Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1, clicked = 0;
    Button btnNext, btnPrev, btnEnd;
    private boolean mTimerRunning;
    private AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chm115);

        questionList = new ArrayList<>();
        questionText = findViewById(R.id.questionText);
        answerText = findViewById(R.id.txtAnswer);
        rbOption1 = findViewById(R.id.radioA1);
        btnEnd = findViewById(R.id.buttonGoto);
        rbOption2 = findViewById(R.id.radioB1);
        rbOption3 = findViewById(R.id.radioC1);
        rbOption4 = findViewById(R.id.radioD1);
        questionNo = findViewById(R.id.question1);
        countDown = findViewById(R.id.timeText);
        random = new Random();

        setListeners();

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


        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.button_previous);

        if (FirstSemesterActivity.questionRequestCode == 1) {
            getQuestionPhase(questionList);

            setDataView(pos);
        } else if (FirstSemesterActivity.questionRequestCode == 2) {
            getQuestionPhase2(questionList);

            setDataView(pos);
        } else if (FirstSemesterActivity.questionRequestCode == 3) {
            getQuestionPhase3(questionList);

            setDataView(pos);
        } else if (FirstSemesterActivity.questionRequestCode == 4) {
            getQuestionPhase4(questionList);

            setDataView(pos);
        } else if (FirstSemesterActivity.questionRequestCode == 5) {
            getQuestionPhase5(questionList);

            setDataView(pos);
        }

        btnNext.setOnClickListener(view -> {
            if (questionAnswered == 30) {
                Toast.makeText(this, "Last Question", Toast.LENGTH_SHORT).show();
            }
            questionAnswered++;
            pos++;
            setDataView(pos);
        });

        btnPrev.setOnClickListener(view -> {
            if (questionAnswered == 1) {
                Toast.makeText(this, "First Question", Toast.LENGTH_SHORT).show();
            }
            questionAnswered++;
            pos--;
            setDataView(pos);
        });
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

    private void setListeners() {
        rbOption1.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption1.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            clicked++;
        });

        rbOption2.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption2.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            clicked++;
        });

        rbOption3.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption3.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            clicked++;
        });

        rbOption4.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption4.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            clicked++;
        });

        btnEnd.setOnClickListener(view -> dialogAlert());
    }

    private void setDataView(int position) {
        questionText.setText(questionList.get(position).getQuestion());

        rbOption1.setText(questionList.get(position).getOption1());
        rbOption2.setText(questionList.get(position).getOption2());
        rbOption3.setText(questionList.get(position).getOption3());
        rbOption4.setText(questionList.get(position).getOption4());
        answerText.setText(questionList.get(position).getAnswer());

        questionNo.setText("Question "+questionAnswered+" of 30");
        if (questionAnswered == 30) {
            showButton();
        }

    }

    private void dialogAlert() {
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit? \n You answered "+clicked+" out of 30 questions")
                .setPositiveButton("Yes", (dialog, which) -> {
                    showButton();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setIcon(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.ic_cancel)).show();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {

        questionList.add(new Question("Calculate the concenteration in mol dm<sup>-3 </sup> of a solution containing 36.5g of \n" +
                "hydrogen chloride in 4.00dm<sup>3 </sup> of solution.",
                "2.5 x 10-4mol dm<sup>-3</sup>",
                "2.5 x 10-3mol dm<sup>-3</sup>",
                "2.5 x 10-1mol dm<sup>-3</sup>",
                "2.5 x 10-2mol dm<sup>-3</sup>",
                "2.5 x 10-1mol dm<sup>-3</sup>"));

        questionList.add(new Question("5.6g of Na<sub>2</sub> CO<sub>3</sub> was dissolved in 250cm<sup>3</sup> \n" +
                "standard flask with distilled water. 25 cm<sup>3</sup> of the solution was titrated against \n" +
                "H<sub>2</sub> SO<sub>4</sub> .The titre value was 15.0cm<sup>3</sup> . Calculate the \n" +
                "concentration of H<sub>2</sub> SO<sub>4</sub>",
                "0.4M",
                "0.30M",
                "0.25M",
                "0.35M",
                "0.35M"));

        questionList.add(new Question("Why do you standardize the KMnO<sub>4</sub> solution before use in redox \n" +
                "experiment?",
                "To ensure its quality",
                "To know the actual concentration",
                "KMnO<sub>4</sub> decomposes after some time",
                "All of the above",
                "All of the above"));

        questionList.add(new Question("What amount in gram of potassium hydroxide must be weghed inorder to prepared 1.0 M \n" +
                "in 1000cm<sup>3</sup> of the solution ? <br /> <br /> [KOH =56.1g/mol ",
                "0.56g",
                "5.60g",
                "56.1g",
                "0.056",
                "56.1g"));

        questionList.add(new Question("Which of these is a suitable indicator for titration involving sodium hydroxide and \n" +
                "hydrochloric acid solution?",
                "Any indicator",
                "methyl red",
                "phenolphthalein",
                "congo red",
                "methyl red"));

        questionList.add(new Question("To choose a suitable indicator for acid-base titration, it depends on ? ",
                "The strength of acid and base",
                "The basicity of acid and base",
                "The molarity of acid and base",
                "The molality of acid and base",
                "The strength of acid and base"));

        questionList.add(new Question("A well labelled sample bottle describes ?",
                "the concentration of the solution",
                "the location of the solution",
                "the colour of the solution",
                "the level of the solution",
                "the concentration of the solution"));

        questionList.add(new Question("Indicator commonly used for potassium permanganate titration is ?",
                "redox indicator",
                "self indicator",
                "starch indicator",
                "starch indicator",
                "self indicator"));

        questionList.add(new Question("During acid- base titration a point is reached when equivalent amounts of acid and base \n" +
                "have been added together.This is called?",
                "end point",
                "neutralization point",
                "equivalence point",
                "titration point",
                "equivalence point"));

        questionList.add(new Question("Standardization of KMnO<sub>4</sub> solution by sodium oxalate \n" +
                "Na<sub>2</sub>C<sub>2</sub>O<sub>4 </sub> is done in ...........medium",
                "acid",
                "basic",
                "neutral",
                "salty",
                "acid"));


    }

    private void getQuestionPhase3(List<Question> list) {

        questionList.add(new Question("The true volume of a liquid is 2.55cm<sup>3</sup> and the measured value is \n" +
                "2.58cm<sup>3</sup> , calculate the relative error.",
                "0.03",
                "1.2",
                "0.012",
                "0.12",
                "0.012"));

        questionList.add(new Question("Using an analytical procedure, the percentages of chloride (Cl<sup>-</sup>) content in a \n" +
                "standard milk sample were found to be 54.01, 54.24, 54.05,54.27and 54.11. If the accepted true " +
                "value of the chloride content of the standard milk sample is 54.20, calculate the absolute error. ",
                "-0.06",
                "0.07",
                "0.06",
                "0.11",
                "-0.06"));

        questionList.add(new Question("Calculate the relative error of the following set of data: 2.4, 2.1, 2.1 ,2.3, and 1.5 if the \n" +
                "accepted value is 2.0.",
                "40",
                "0.04",
                "0.4",
                "0.004",
                "0.04"));

        questionList.add(new Question("25.0cm<sup>3</sup> of tetraoxosulphate(VI) acid requires 32.30cm<sup>3</sup> of \n" +
                "0.1M sodium hydroxide for complete neutralization. What is the concentration in gdm<sup>-\n" +
                "3</sup> and general name of tetraoxosulphate(VI) acid",
                "6.21 , titrant",
                "0.06 , analyte",
                "6.21 , analyte",
                "0.06, titrant",
                "6.21 , analyte"));

        questionList.add(new Question("The sum of 0.508, 110.1, and 21.27 gives",
                "131.878",
                "131.88",
                "131.9",
                "131.91",
                "131.9"));

        questionList.add(new Question("1.5g of pyrite (impure FeS<sub>2</sub> ) is analysed for sulphur. If 1.14g of \n" +
                "BaSO<sub>4</sub> <br /> <br /> is precipitated out. Calculate the percentage of sulphur ion in the \n" +
                "sample",
                "5.05%",
                "10.4%",
                "15.6%",
                "21.0%",
                "10.4%"));

        questionList.add(new Question("The Ksp of AgCl at 25<sup>o</sup> C is 1.0 x 10<sup>-10</sup> , calculate the molar \n" +
                "solubility of AgCl",
                "1.0 x 10<sup>-4</sup>",
                "1.0 x 10<sup>-5</sup>",
                "1.0 x 10<sup>-6</sup>",
                "1.0 x 10<sup>-7</sup>",
                "1.0 x 10<sup>-5</sup>"));

        questionList.add(new Question("T4.00 g of sodium hydroxide is dissolved completely in 500cm<sup>3</sup> volumetric \n" +
                "flask, the concentration is (Na=23 , O=16 )",
                "0.2mol/dm<sup>3</sup>",
                "5mol/dm<sup>3</sup>",
                "2.0mol/dm<sup>3</sup>",
                "0.50g/dm<sup>3</sup>",
                "0.2mol/dm<sup>3</sup>"));

        questionList.add(new Question("Round off 36.9645 and 297846 to five significant figures",
                "36.964 and 2.9785x10<sup>4</sup>",
                "36.965 and 2.9785x10<sup>5</sup>",
                "36.964 and 2.9785 x10<sup>5 </sup>",
                "36.964 and 2.9784 x10<sup>6</sup>",
                "36.965 and 2.9785x10<sup>5</sup>"));

        questionList.add(new Question("Silver ion forms a stable 1: 1 complex with methylenetetramine called “trien” .Calculate \n" +
                "the silver ion concentration at equilibrium when 25mL of 0.010M silver nitrate is added to 50mL of \n" +
                "0.015M trien. (Kf =5.0 x 10<sup>7</sup> )",
                "9.8 x 10<sup>-11</sup> M",
                "9.8 x 10<sup>-10</sup> M",
                "9.8 x 10<sup>-9</sup> M",
                "9.8 x 10<sup>-8</sup> M",
                "9.8 x 10<sup>-9</sup> M"));

        questionList.add(new Question("The formation constant for CaY<sup>2-</sup> is 5.0 x 1010 .At pH 10, ? 4 is calculated to \n" +
                "be 0.35 to give a conditional constant of 1.8 x 1010. Calculate pCa in 100mL of a solution of 0.100M \n" +
                "Ca<sup>2+</sup> at pH 10 after addition of 0mL EDTA . ",
                "1.00",
                "2.00",
                "0.10",
                "0.15",
                "1.00"));

        questionList.add(new Question("What must be the concentration of added Ag+ to just start the precipitation of AgCl in a \n" +
                "1.0 x 10<sup>-3</sup> M solution NaCl ?[ ksp for AgCl= 1.0 x 10-10].",
                "1.0 x 10<sup>-5</sup>",
                "1.0 x 10<sup>-6</sup>",
                "1.0 x 10<sup>-7</sup>",
                "1.0 x 10<sup>-8</sup>",
                "1.0 x 10<sup>-7</sup>"));

        questionList.add(new Question("1500ml of a 15ppm solution of potassium hydroxide contains how many mg of potassium \n" +
                "hydroxide?",
                "30mg",
                "15mg",
                "22mg",
                "25mg",
                "22mg"));

        questionList.add(new Question("Volumetric titration is best described as a class of experiment where <br /> <br /> I. A \n" +
                "known property of one solution is used to infer an unknown property of another solution. <br /> <br \n" +
                "/> II. Titrant , analyte and product involve volume measurement <br /> <br /> III. Chemical \n" +
                "" +
                "indicator must be used to locate the end-point <br /> <br /> IV. A known concentration of one \n" +
                "solution is used to infer an unknown concentration of another solution ",
                "I",
                "II",
                "IV",
                "III",
                "I"));

        questionList.add(new Question("A sample of pure potassium chloride was analyzed for chloride ion (Cl<sup>-</sup> ) \n" +
                "with the following results 47.24, 47.08,47.31,47.42,47.29 and 47.38. Calculate the standard \n" +
                "deviation and percentage relative standard deviation. ",
                "0.18 and 0.38%",
                "0.19 and 0.40%",
                "0.12 and 0.25%",
                "0.13 and 0.27%",
                "0.12 and 0.25%"));

        questionList.add(new Question("A is a solution of hydrochloric acid containing 1.825g per dm<sup>3</sup> of solution. B \n" +
                "is a solution of impure sodium carbonate. 25cm<sup>3 </sup> of B requires 22.7cm<sup>3 </sup> \n" +
                "of A for complete reaction. What is the concentration of B and the percentage by mass of pure B if \n" +
                "5.00g impure B is dissolved in 1dm<sup>3</sup> of solution ",
                "2.41 gdm<sup>-3</sup> , 48.20%",
                "4.81 gdm<sup>-3</sup> , 96.20%",
                "9.63 gdm<sup>-3</sup> , 60.3%",
                "4.81gdm<sup>-3</sup> , 48.20% ",
                "2.41 gdm<sup>-3</sup> , 48.20%"));

        questionList.add(new Question("Calculate the mean and standard deviation of the following set of analytical results: \n" +
                "15.67g, 15.69g and 16.03g. ",
                "15.80 and 0.20",
                "1.580 and 2.0",
                "15.80 and 2.0",
                "15.80 and 0.04",
                "15.80 and 0.20"));

        questionList.add(new Question("Calculate the percent by weight of solution that weighs 200g and contains 25g of sodium \n" +
                "sulphate. ",
                "25% (w/v)",
                "12.5% (w/v)",
                "25%(w/w)",
                "12.5%(w/w)",
                "12.5%(w/w)"));

        questionList.add(new Question("Consider the following set of data: 2.4, 2.1, 2.1, 2.3, and 1.5, Calculate the mean and \n" +
                "absolute error if the accepted is 2.0.",
                "2.06 and -2.08",
                "2.08 and 0.08",
                "2.08 and -0.08",
                "2.06 and 0.08",
                "2.08 and 0.08"));

        questionList.add(new Question("If a 2.62g sample of material is analyzed to be 2.52g, what is the absolute error?",
                "0.10g",
                "2.08 and 0.08",
                "-0.01g",
                "0.100g",
                "-0.01g"));

        questionList.add(new Question("Manganese content of an earthcrust is determined by converting the Mn to \n" +
                "Mn<sub>3</sub>O<sub>4</sub> . 1.52g of the sample produces Mn3O4 and ignited to \n" +
                "Mn<sub>2</sub>O<sub>3</sub> . Calculate the percentage of Mn in the sample. ",
                "5.90%",
                "5.97%",
                "6.0%",
                "6.27%",
                "5.97%"));

        questionList.add(new Question("Calculate the percentage of Ba in pure BaSO<sub>4</sub> ? ( Ba = 137.3, S=32, O=16)",
                "59%",
                "70%",
                "57%",
                "60%",
                "59%"));

        questionList.add(new Question("Solution A is 0.050M H<sub>2</sub> SO<sub>4</sub> , B is a solution of sodium \n" +
                "hydroxide .The titre value obtained for the titration of A against 25cm<sup>3</sup> of B using \n" +
                "phenolphthalein indicator was 38.80cm<sup>3</sup> . Calculate the molarity and concentration of \n" +
                "solution B",
                "0.16M, 6.4 gdm-3",
                "0.08M,3.2 gdm-3",
                "0.04M, 1.6 gdm-3",
                "0.16M, 3.2 gdm-3",
                "0.16M, 6.4 gdm-3"));

        questionList.add(new Question("A titre value of 28.6cm<sup>3</sup> of a solution containing 0.4562g HCl per \n" +
                "250cm<sup>3</sup> was obtained when the acid was titrated against 25.0cm<sup>3</sup> of \n" +
                "sodium hydroxide . What is the molarity and concentration in gdm<sup>-3 </sup> of sodium \n" +
                "hydroxide?",
                "0.06 , 2.29",
                "2.29 , 0.06",
                "0.11 , 4.4",
                "0.05 , 2.00",
                "0.06 , 2.29"));

        questionList.add(new Question("A loss of 0.4mg of Zn occurs in the course of an analysis for that element. Calculate the \n" +
                "relative error due to this loss if the weight of Zn in the sample is 400mg.",
                "1.0%",
                "-1.0%",
                "0.1%",
                "0.2%",
                "-1.0%"));

        questionList.add(new Question("Solution E is 0.100M HCl. F is a solution of sodium carbonate. If 25cm<sup>3</sup> of F \n" +
                "requires 23.5cm<sup>3</sup> of E in titration using methyl orange indicator, what is the \n" +
                "concentration of F in g/dm<sup>3</sup> ? <br /> <br /> (Na=23, H=1.00, Cl=35.5, O=16, C=12)",
                "4.982gdm<sup>3</sup>",
                "0.047gdm<sup>-3</sup>",
                " 4.0982 gdm<sup>-3</sup>",
                "0.470gdm<sup>-3</sup>",
                "4.982gdm<sup>3</sup>"));

        questionList.add(new Question("A solution with pH= 2 is more acidic than one with a pH=6 by a factor of? ",
                "4",
                "12",
                "400",
                "10,000",
                "10,000"));

        questionList.add(new Question("Evaluate the addition of 0.0631, 0.0226 and 0.0821 to the correct number of significant ",
                "1.01678",
                "0.01678",
                "400",
                "1.6780",
                "1.6780"));

        questionList.add(new Question("A is a solution of hydrochloric acid containing 3.650g of HCl in 1dm<sup>3</sup> of \n" +
                "solution B is a solution of impure sodium carbonate . 25cm<sup>3</sup> of B require \n" +
                "22.7cm<sup>3</sup> of A for complete reaction . Given that 5.00g of impure B was dissolved \n" +
                "in1dm<sup>3 </sup> solution, what is the percentage by mass of pure sodium carbonate in the \n" +
                "impure sample ? <br /> <br /> (Na=23, C=12, O=16, Cl=35.5, H=1.00)",
                "80.2%",
                "78.6%",
                "40.2%",
                "60.2%",
                "80.2%"));

        questionList.add(new Question("Solution A contains 7.000g H<sub>2</sub> SO<sub>4</sub> in 1dm<sup>3</sup> and B \n" +
                "is a solution of sodium hydrogen carbonate. If 25cm<sup>3</sup> of B requires \n" +
                "26.9cm<sup>3</sup> of A for complete neutralization, what is the concentration of B in gdm<sup>-\n" +
                "3</sup> ? (S=32, Na=23, H=1.00, C=12, O=16)",
                "12.752 gdm<sup>-3</sup>",
                "15.300 gdm<sup>-3</sup>",
                "12.852 gdm<sup>-3</sup>",
                "15.400 gdm<sup>-3</sup>",
                "12.852 gdm<sup>-3</sup>"));

        questionList.add(new Question("25cm<sup>3 </sup> of a solution containing 2.10g of sodium hydrogen carbonate was \n" +
                "titrated against a dilute hydrochloric acid solution and obtained 27.50cm<sup>3</sup> ,titre value \n" +
                "calculate the concentration in mole per dm<sup>3</sup>",
                "0.91",
                "0.46",
                "3.32",
                "0.09",
                "0.09"));

        questionList.add(new Question("If three litres of a solution of H<sub>3</sub> PO<sub>4</sub> contains 294grammes of \n" +
                "H<sub>3</sub> PO<sub>4</sub> , what is the normality of the solution? Atomic weights: H=1 O=16 \n" +
                "P=31 ",
                "0.1",
                "1.0",
                "3.0",
                "9.8",
                "3.0"));
    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question("1. In the analysis of a 2.72g sample containing potassium iodide(KI) (FW=166), 0.0720g of " +
                "barium iodate, Ba(IO<sub>3</sub> )<sub>2</sub> , (FW=487.1) was recovered. Express the results " +
                "of this analysis as percent potassium iodide.(Ba-137, I-127, O-16, K-39)",
                "" +
                        "1.2% ",
                "2.8%", "1.8%",
                "2.0%",
                "1.8%"));

        questionList.add(new Question("2. Manganese in a 1.52 g sample was precipitated as Mn<sub>3</sub>O<sub>4</sub> (FW " +
                "= 228.8) weighing 0.126 g. Find percentage of Mn (FW = 54.94) in the sample. ",
                "8.97%",
                "6.97%", "5.97%",
                "7.97%",
                "5.97%"));

        questionList.add(new Question("3. A 2.5g sample of limestone containing Ca<sup>2+</sup> (aq) is dissolved in HCl and " +
                "reacted with excess ammonium oxalate solution, (NH4<sub>)</sub> 2C<sub>2</sub> " +
                "O<sub>4</sub> (aq), to precipitate the calcium ions as calcium oxalate, " +
                "CaC<sub>2</sub>O<sub>4</sub> (s) (FW = 128).The precipitate was filtered, dried and weighed " +
                "to a constant mass of 3.23g. Determine the percentage by mass of calcium in the limestone " +
                "sample. (Ca-40, C-12, O-16)",
                "38.7%",
                "28.2%",
                "36.3%",
                "40.3%",
                "40.3%"));

        questionList.add(new Question("4. What is the value of the gravimetric factor for the conversion of SO<sub>3</sub> to " +
                "BaSO<sub>4</sub> (Ba-137, S-32, 0-16)?",
                "0.3433",
                "0.2516", "0.4321",
                "0.2448",
                "0.3433"));

        questionList.add(new Question("5. The following measurements were obtained for a metal bar: 4.012g, 4.022g, 4.019g," +
                "4.011g, 4.017g, 4.018g and 4.024g. Calculate the mean.",
                "4.0175g",
                "4.018g", "4.01757g",
                "4.017g",
                "4.018g"));

        questionList.add(new Question("6. 3000ml of a 20ppm solution of ethanol contains how many mg of ethanol?",
                "20mg", "40mg",
                "60mg" ,"80mg",
                "60mg"));

        questionList.add(new Question("7. A titre value of 38.8cm3 of a solution containing 0.4563g HCl in 250cm3 was obtained " +
                "when the acid was titrated against 25.0cm3 of calcium hydroxide solution. What is the molarity and " +
                "concentration in gdm-3 of the base? <br /> (Ca=40.0 Na=23, C=12, O=16, Cl=35.5, H=1.00) ",
                "0.078 , 5.74", "0.039 , 2.21",
                "0.039 , 2.87",
                "2.87,0.039", "0.039 , 2.87"));

        questionList.add(new Question("8. C is a solution of hydrochloric acid containing 3.65g of HCl per dm<sup>3 </sup> of " +
                "solution and D is a solution of impure sodium carbonate. If 25cm<sup>3</sup> of D requires " +
                "22.7cm<sup>3</sup> of C for complete reaction, what is the molarity and concentraion of sodium " +
                "carbonate?",
                "0.045M, 3.74 g/dm3", "0.091M, 9.65 g/dm3",
                " 0.182M, 19.29 g/dm3",
                "0.045M, 4.81 g/dm3", "0.045M, 4.81 g/dm3"));

        questionList.add(new Question("9. The colour change of a chemical indicator requires an over titration of 0.03ml. Calculate " +
                "the percent relative error if the total volume of titrant is 50.00ml.",
                "0.03%", "0.06%",
                "6%", "-0.06%", "0.06%"));

        questionList.add(new Question("10. The volume of methanol in a 2-litre water solution containing 80% methanol (assuming " +
                "volumes are additive)",
                "400ml", "800ml" , "1200ml",
                "1860ml",
                "1860ml"));

        questionList.add(new Question("11. The number of significant figures in 0.0030900 and 0.002008 are?",
                "5 and 6",
                "4 and 5",
                "7 and 7",
                "6 and 4",
                "5 and 6"));

        questionList.add(new Question("12. A sample of water has a mass of 234.9g at 25<sup>o</sup> C what is the volume of the \n" +
                "water in cubic meters, given that the density of water at 25<sup>o</sup> C is 0.99707g/ml? ",
                "235.590ml",
                "236.6m<sup>3</sup>",
                "0.0002356m<sup>3</sup>",
                "0.0002356m<sup>3</sup>",
                "0.0002356m<sup>3</sup>"));

        questionList.add(new Question("13. Round off 21.9994 and 27845 to four significant figures",
                "21.99 and 2.784 x10<sup>4</sup>",
                "22.00 and 2.784 x10-4",
                "22.00 and 2.785 x10<sup>4 </sup>",
                "22.00 and 2.784 x104",
                "22.00 and 2.785 x10<sup>4 </sup>"));

        questionList.add(new Question("14. 38.80cm<sup>3</sup> of 0.05M H<sub>2</sub>SO<sub>4 </sub> was used for complete " +
                "neutralization when it was titrated against 25.0cm<sup>3</sup> of sodium hydroxide. What is the " +
                "concentration of sodium carbonate in gdm<sup>-3</sup> ?",
                "6.4",
                "3.2",
                "1.6",
                "64",
                "6.4"));

        questionList.add(new Question("15. Solution X contains 2.100g H<sub>2</sub> SO<sub>4</sub> in 250cm<sup>3</sup> and " +
                "Y is a solution of sodium hydrogen carbonate. If 25cm<sup>3</sup> of Y requires 26.8cm<sup>3 " +
                "</sup> of X for complete neutralization, what is the general name for Y and its concentration? ",
                " analyte , 0.184gdm<sup>-3</sup>",
                " titrant , 0.184gdm<sup>-3</sup>",
                " titrant , 18.400gdm<sup>-3</sup>",
                " analyte , 15.434gdm<sup>-3</sup>",
                " analyte , 15.434gdm<sup>-3</sup>"));

        questionList.add(new Question("16. What is the normality in a redox reaction of 0.100molar solution of NaHC<sub>2</sub> " +
                "O<sub>4</sub> ? ",
                "0.200N",
                "0.100N",
                "0.050N",
                "0.025N",
                "0.100N"));

        questionList.add(new Question("17. Solution A contains 2.5g H<sub>2</sub>SO<sub>4</sub> in 250cm<sup>3</sup> and B " +
                "is solution of sodium hydrogen carbonate. If 25cm<sup>3</sup> of B requires " +
                "" +
                "19.50cm<sup>3</sup> of A for complete reaction, what is the concentration of B in gdm<sup>-" +
                "3</sup> ? <br /> <br /> (H=1.00 Na=23, O=16.00, C= 12.0, S=23.0) ",
                "3.9",
                "13.44",
                "0.16",
                "0.08",
                "13.44"));

        questionList.add(new Question("18. In the analysis of chromium in three samples of stainless steel, the following results were " +
                "obtained <br /> A 6.25% 6.24% 6.26% <br /> <br /> B 6.71% 6.72% 6.71% <br /> " +
                "<br /> C 6.05% 6.57% 6.32% <br /> <br /> Suppose the true value is 6.25%,how precise " +
                "and accurate are the result? ",
                "A, B, C are accurate and precise",
                "Only A is accurate and precise",
                "A and B are accurate and precise",
                "A and C are accurate and precise",
                "Only A is accurate and precise"));


        questionList.add(new Question("19. A solution of accurately known concentration is called ?",
                "Molar solution",
                "Normal solution",
                "Standard solution",
                "Formal solution",
                "Standard solution"));

        questionList.add(new Question("20. Twenty (20) students are to be supplied each with 100ml of 0.05M NaOH solution in the " +
                "laboratory.What quantity of NaOH is requred to be weighed for this for this purpose? [NaOH] = " +
                "40gmol<sup>-1</sup>",
                "4.5g",
                "4.00g",
                "5.00g",
                "5.50g",
                "4.00g"));

        questionList.add(new Question("21. In volumetric analysis,the symbol M is often used for?",
                "mol cm<sup>-3</sup>",
                "mmol cm<sup>-3</sup>",
                "mol dm<sup>-3</sup>",
                "g cm<sup>-3</sup>",
                "mol dm<sup>-3</sup>"));

        questionList.add(new Question("22. A solid A of mass 5.00kg was gently dipped into a measuring cylinder containing water, " +
                "the level of water increased from 60cm<sup>3</sup> to 100cm<sup>3</sup> . Calculate the density " +
                "of solid A. ",
                "0.125g/cm<sup>3</sup>",
                "125g/cm<sup>3</sup>",
                "12.5g/cm<sup>3</sup>",
                "1.25g/cm<sup>3</sup>",
                "125g/cm<sup>3</sup>"));

        questionList.add(new Question("23. Iodimetry titration deals with......",
                "Indirect method of iodine estimation",
                "Direct method of iodine estimation",
                "Addition of iodine",
                "Removal of iodine",
                "Indirect method of iodine estimation"));

        questionList.add(new Question("24. What mass of the solute must be used in order to prepare 500 cm<sup>3</sup> of " +
                "0.0100 mol/dm<sup>3</sup> NaOH(aq) from NaOH(s)?",
                "2.0g",
                "0.2g",
                "2.55g",
                "0.210g",
                "0.2g"));

        questionList.add(new Question("25. Which of these steps is not necessary in acid-base titration?",
                "To rinse the burette and the pipette with the solution to be used in them",
                "To remove the funnel from the burette before titration",
                "To rinse the titration flask with the solution it is to hold",
                "All the above steps are not necessary",
                "To rinse the titration flask with the solution it is to hold"));

        questionList.add(new Question("26. 250cm<sup>3</sup> of 0.8M NaOH was diluted to 1 litre. Calculate the amount of solute " +
                "in 200cm<sup>3</sup> of the dilute solution.",
                "0.64 moles",
                "1.00 moles",
                "0.02 moles",
                "0.04 moles",
                "0.04 moles"));

        questionList.add(new Question("27. A solution which normally resist changes in pH when a small quantity of either acid or " +
                "base is being added is known as?",
                "Normal solution",
                "Molar solution",
                "Buffer solution",
                "Concentrated solution",
                "Buffer solution"));

        questionList.add(new Question("28. Which of these is not true of K<sub>2</sub> Cr<sub>2</sub>O<sub>7</sub> titration?",
                "It cannot be used in the presence of HCl or its salts",
                "colour change from deep green to blue",
                "diphenylamine in concentrated H<sub>2</sub> SO<sub>4</sub> is reduced",
                "K<sub>2</sub> Cr<sub>2</sub> O<sub>7</sub> is reduced",
                "It cannot be used in the presence of HCl or its salts"));

        questionList.add(new Question("29. Primary standards used in redox titrimetry include",
                "Ferrous ammonium sulphate",
                "Potassium dichromate",
                "Potassium iodate",
                "any of the above",
                "any of the above"));

        questionList.add(new Question("30. Which of these acids is not suitable for potassium permanganate redox titration?",
                "Both HCl and HNO<sub>3</sub>",
                "HCl and H<sub>2</sub> SO<sub>4</sub>",
                "HCl only",
                "H<sub>2</sub>SO<sub>4</sub> only",
                "Both HCl and HNO<sub>3</sub>"));

        questionList.add(new Question("31. Calculate the amount in moles of solute in 250cm<sup>3</sup> of sodium hydroxide " +
                "solution containing 1.00moldm<sup>-3 </sup>",
                "0.20moles",
                "0.30moles",
                "0.25moles",
                "0.03moles",
                "0.25moles"));

        questionList.add(new Question("32. The oxidation state of Chlorine in HOCl<sub>2</sub> , HClO<sub>3</sub> and " +
                "HClO<sub>4</sub> are respectively. ",
                "+7 +5 and +1 ",
                "+1 +5 and +7",
                "+1 , +3 and +4",
                "-1 -5 and +7",
                "+1 +5 and +7"));

        questionList.add(new Question("33. Reducing agent X reacts with oxidizing agent Y, the true statement is",
                "X is reduces",
                "Y is oxidises",
                "X gains electron",
                "Y gains election",
                "Y gains election"));

        questionList.add(new Question("34. Last drop at the pipette tip should not be blown to avoid <br /> <br /> I using more " +
                "volume II breaking the glassware <br /> <br /> III adulterating the sample ",
                "I only",
                "I and II only",
                "I and III only",
                "I, II and III",
                "I only"));

        questionList.add(new Question("35. Arrange the following compounds of manganese in order of increasing oxidation state of " +
                "manganese KMnO<sub>4</sub> , MnCl<sub>2</sub> & MnO<sub>2</sub>",
                "MnCl<sub>2</sub> , MnO<sub>2</sub> and KMnO<sub>4</sub>",
                "MnCl<sub>2</sub> , MnO<sub>2</sub> , KMnO<sub>4</sub>",
                "KMnO<sub>4</sub> , MnO<sub>2</sub> , MnCl<sub>2</sub>",
                "MnO<sub>2</sub> KMnO<sub>4</sub> MnCl<sub>2</sub> ",
                "KMnO<sub>4</sub> , MnO<sub>2</sub> , MnCl<sub>2</sub>"));

        questionList.add(new Question("36. Clamping the burette in vertical position is a precaution for which of the following " +
                "reasons? ",
                "To avoid leakage",
                "to allow proper flow of content",
                "To avoid error due to parallax",
                "To allow direct contact centrally in the conical flask content",
                "To avoid error due to parallax"));

        questionList.add(new Question("37. Which of the followings affects the determination of density of gases and liquid?",
                "concentration",
                "temperature",
                "solubility",
                "pressure",
                "temperature"));

        questionList.add(new Question("38. What volume of 0.20M NaOH when diluted to 600cm<sup>3</sup> will produce a " +
                "solution of concentration 0.05M?",
                "2400cm<sup>3</sup>",
                "150cm<sup>3</sup>",
                "100cm<sup>3</sup>",
                "200cm<sup>3</sup>",
                "150cm<sup>3</sup>"));

        questionList.add(new Question("39. Which of the following correctly describe the use of a crucible in the laboratory? ",
                "To dry the sample",
                "To ash the sample",
                "To evaporate the sample",
                "all of the above",
                "all of the above"));

        questionList.add(new Question("40. The following are true of complexometric titration except?",
                "The technique is useful for the determination of a larger number of metals",
                "Many metal ions form slightly soluble salts or slightly dissociated complexes",
                "It offers ease of metal ions and anions at the millimole level",
                "It is independent of pH",
                "It is independent of pH"));

        questionList.add(new Question("41. The number of molecules of the complexing agent is called?",
                "Ligand",
                "Stability constant",
                "Formation constant",
                "Lewis acid",
                "Ligand"));

        questionList.add(new Question("42. The ligand often depend on <br /> <br /> I. The co-ordination number of the metal " +
                "<br /> <br /> II. The number of complexing groups on the ligand molecule <br /> <br /> III. " +
                "The bonding site",
                "I only",
                "II only",
                "I and III only",
                "I and II only",
                "I and II only"));

        questionList.add(new Question("43. Ammonia is a simple complexing agent with one pair of unshared electrons that may " +
                "complex with which of these ions ?",
                "Silver",
                "Calcium",
                "Sulphate",
                "Nitrate",
                "Silver"));

        questionList.add(new Question("44. Simple complexing agent such as ammonia are rarely used as titrating agents, because",
                "a sharp endpoint corresponding to a stoichiometric complex is generally difficult to achieve",
                "a sharp end point is obtained at equilibrum",
                "the stepwise formation constant is large",
                "all of the above",
                "a sharp endpoint corresponding to a stoichiometric complex is generally difficult to achieve"));

        questionList.add(new Question("45. An organic agent that has two or more groups capable of complexing with a metal ion is " +
                "called?",
                "a chelating agent",
                "a chelate",
                "a complex",
                "a ligand",
                "a chelating agent"));

        questionList.add(new Question("46. Which of the following is not true of ethylenediamine tetra acetic acid?",
                "it has six complexing groups",
                "it is a tetraprotic acid",
                "it has four ionizable hydrogen on the four carboxyl groups",
                "it has four complexing groups ",
                "it has four complexing groups "));

        questionList.add(new Question("47. Which of these is a suitable indicator used in complexometric titration?",
                "methyl orange",
                "Eriochrome Black T",
                "methyl red",
                "phenolphthalein",
                "Eriochrome Black T"));

        questionList.add(new Question("48. Consider the reaction: Zn + CuSO<sub>4</sub> &rarr; ZnSO<sub>4</sub> + Cu. " +
                "Name the reducing agent in the above reaction",
                "Zn",
                "CuSO<sub>4</sub>",
                "ZnSO<sub>4</sub>",
                "Cu",
                "Zn"));

        questionList.add(new Question("49. A method of analysis yields weights for gold that are low by 0.3mg. Calculate the percent " +
                "relative error caused by this uncertainty if the weight of gold in the sample is 800mg./phases of conflict",
                "0.04%", "0.0375%",
                "0.038%", "-0.04%",
                "-0.04%"));

        questionList.add(new Question("50. Solution A is 0.050M HNO<sub>3</sub> and B is a solution of sodium carbonate. If " +
                "25.0cm<sup>3</sup> of B requires 19.70cm<sup>3</sup> of A in a titration to methyl orange end " +
                "point, what is the concentration of B in mole per litre ? <br /> <br /> (Ca =40.0 Na=23, C=12, O=16, " +
                "Cl=35.5, H=1.00 , N=14)",
                "0.02mol/L",
                "2.09g/dm<sup>3</sup>",
                "0.04mol/L",
                "4.18g/L",
                "0.02mol/L"));
    }

    private void getQuestionPhase4(List<Question> list) {

        questionList.add(new Question("Consider the following set of data: 4.4, 4.1, 4.2, 4.3, and 4.5, Calculate the absolute error \n" +
                "if the accepted is 4.0.",
                "4.3",
                "6.3",
                "2.5",
                "1.0",
                "6.3"));

        questionList.add(new Question("A divalent metal M<sup>2+ </sup> reacts with a ligand L to form a 1: 1 complex. Calculate \n" +
                "the concentration of M<sup>2+</sup> in a solution prepared by mixing equal volumes of 0.20M \n" +
                "M<sup>2+</sup> and 0.20M L. (Kf =1.0 x 10<sup>8</sup> ) ",
                "3.2 x 10<sup>-4</sup> M",
                "3.2 x 10<sup>-5</sup> M",
                "3.2 x 10<sup>-3</sup> M",
                "3.1 x 10<sup>-5</sup> M",
                "3.2 x 10<sup>-5</sup> M"));

        questionList.add(new Question("Gravimetric factor (GF) for the conversion of Ca2+ to CaC2O4 precipitate is <br /> <br /> \n" +
                "(Ca-40, C-12, 0-16)",
                "0.3125",
                "0.4125",
                "0.1125",
                "0.2125",
                "0.3125"));

        questionList.add(new Question("Common determinate errors are --------- except?",
                "Accidental error",
                "Instrumental error",
                "Errors of methods",
                "Operative errors",
                "Accidental error"));

        questionList.add(new Question("The oxidation number of an element in an uncombined state is",
                "1",
                "2",
                "3",
                "0",
                "0"));

        questionList.add(new Question("C is a solution of hydrochloric acid containing 3.65g of HCl per dm<sup>3</sup> of \n" +
                "solution and D is a solution of impure sodium carbonate. If 25cm<sup>3</sup> of D requires \n" +
                "22.7cm<sup>3</sup> of C for complete reaction, what is the molarity and concentraion of sodium \n" +
                "carbonate?",
                "0.045M, 3.74 gdm<sup>-3</sup>",
                "0.091M, 9.65 gdm<sup>-3</sup>",
                "0.182M, 19.29 gdm<sup>-3</sup>",
                "0.045M, 4.81 gdm<sup>-3</sup>",
                "0.045M, 4.81 gdm<sup>-3</sup>"));

        questionList.add(new Question(" In gravimetric determination, co-precipitated impurities can be removed by",
                "Peptization",
                "Washing",
                "Coagulation",
                "Post precipitation",
                "Washing"));

        questionList.add(new Question("Oxidation number of sulphur in Na<sub>2</sub>SO<sub>4</sub> is ?",
                "+6",
                "+8",
                "-6",
                "+5",
                "+6"));

        questionList.add(new Question("A is a solution of hydrochloric acid containing 2.92mol per litre of solution and B is a \n" +
                "solution of an impure sodium carbonate. If 25cm<sup>3</sup> of B requires 26.4cm<sup>3</sup> \n" +
                "of A using methyl orange indicator, what is the molarity and concentration in g/dm<sup>3</sup> \n" +
                "of solution B? <br /> <br /> (Na =23.0, C=12.0, O =16.0, H=1.0, Cl=35.5)",
                "4.48 , 0.04",
                "0.09 , 9.54",
                "0.08 , 9.01",
                "0.04 , 4.48",
                "0.04 , 4.48"));

        questionList.add(new Question("Which of the following techniques can be a very selective process?",
                "gravimetric method",
                "precipitation method",
                "colorimetric method",
                "titrimetric method",
                "precipitation method"));

        questionList.add(new Question("The titre value obtained in an acid-base titration of 25cm<sup>3</sup> of sodium \n" +
                "hydrogen carbonate with HCl solution was 27.50cm<sup>3</sup> . Given that 4.20g of " +
                "NaHCO<sub>3</sub> was dissolved in 500cm3 H<sub>2</sub> O, what is the molarity and " +
                "concentration of HCl? <br /> <br /> (Na=23.00, C=12, H=1.00, O=16, Cl=35.5) ",
                "909M, 33.18 gdm<sup>-3</sup>",
                "0.909, 3.322 gdm<sup>-3</sup>",
                "0.091M, 3.322 gdm<sup>-3</sup>",
                "0.046M, 1.679 gdm<sup>-3</sup>",
                "0.091M, 3.322 gdm<sup>-3</sup>"));

        questionList.add(new Question("Source of contamination of precipitate in gravimetric analysis could be from ?",
                "Washing",
                "Igniting",
                "Surface adsorption",
                "Weighing",
                "Surface adsorption"));

        questionList.add(new Question("The quantitative determination of a substance by precipitation followed by isolation and " +
                "weighing of the precipitate is called? ",
                "qualitative analysis",
                "gravimetric analysis",
                "titrimetric analysis",
                "colorimetric analysis",
                "gravimetric analysis"));

        questionList.add(new Question("The following factors are needed for achieving selectivity in complexometric titrations " +
                "except ",
                "The use of appropriate masking agent",
                "The pH control",
                "The temperature variation",
                "All of the above",
                "The temperature variation"));

        questionList.add(new Question("The following requirements must be met for a successful gravimetric determination " +
                "except",
                "The desired substance must be completely precipitated",
                "The weighed form of the precipitate should be a stoichiometric compound of known " +
                        "composition",
                "The precipitate must be pure and easily filtered",
                "Nucleation followed particle growth in precipitate formation",
                "Nucleation followed particle growth in precipitate formation"));

        questionList.add(new Question("Which of these is not true of the primary adsorbed ion? ",
                "Held by chemical bond",
                "The lattice ion that is in excess",
                "Fixed on separate surface",
                "Held by electrostatic attraction",
                "Held by electrostatic attraction"));

        questionList.add(new Question("Which of the following is not included in the group of analyses based on the amount of \n" +
                "sample taken?",
                "macroanalyses",
                "single constituent analyses",
                "microanalyses",
                "ultra-microanalyses",
                "single constituent analyses"));

        questionList.add(new Question("Express 3.00g/ml x1.207ml to the correct number of significant figures.",
                "3.62g",
                "3.621g",
                "3.62g/ml",
                "3.621g/ml",
                "3.62g"));

        questionList.add(new Question("The following statements are true about random error except",
                "They are indeterminate or accidental errors",
                "The error can be predicted or estimated",
                "The error follow a normal distribution or Gaussian curve",
                "Some of the error arises from statistical nature of things.",
                "The error can be predicted or estimated"));

        questionList.add(new Question("Chloride ion in 3.10g iron III chloride sample is converted to the hydrous <br /> <br /> \n" +
                "oxides and ignited to iron II oxide. If the oxide weighed 1.53g, calculate <br /> <br /> the \n" +
                "percentage of iron in the sample. <br /> <br /> (Fe=56, Cl=35.5, O=16.0 H=1.0)",
                "16.0%",
                "18.0%",
                "20.0%",
                "22.0%",
                "18.0%"));

        questionList.add(new Question("The experimental error in which its source cannot be traced is known as",
                "definite error",
                "Indeterminate or second class error",
                "Determinate error",
                "Systematic error",
                "Indeterminate or second class error"));

        questionList.add(new Question("The error in weighing an hygroscopic sample can be described by all the following except " +
                "one.",
                "Systematic error",
                "Determinate error",
                "Positive error",
                "Random error",
                "Random error"));

        questionList.add(new Question("In gravimetric analysis, measurement are based on?",
                "volume",
                "density",
                "mass",
                "pressure",
                "mass"));

        questionList.add(new Question(" In precipitation gravimetric methods, analyte is converted to",
                "a sparingly soluble precipitate of known composition",
                "a colloidal precipitation of known composition",
                "a soluble precipitation of known composition",
                "a precipitate that reacts with atmospheric constituents",
                "a sparingly soluble precipitate of known composition"));

        questionList.add(new Question("In the precipitation of chloride ion as silver chloride, all these ions are interferences " +
                "except?",
                "Cyanide ion",
                "Nitrate ion",
                "Bromide ion",
                "Iodide ion",
                "Nitrate ion"));

        questionList.add(new Question("The first step in precipitation is the formation of very tiny particles of precipitate called " +
                "nuclei. The process of forming these particles is called",
                "nucleation",
                "nucleiation",
                "nuclation",
                "nucliation",
                "nucleation"));

        questionList.add(new Question("The following are correct on counter ion except? ",
                "Held by chemical bond",
                "Held by electrostatic attraction",
                "Opposite in charge to primary adsorbed ion",
                "Loosely held in solution surrounding precipitate",
                "Held by chemical bond"));

        questionList.add(new Question("The Ksp for the ionization of AgCl in water is given by",
                "[H+][ Cl-]",
                "[Ag+][ OH-]",
                "[Ag+][ Cl-]",
                "[H+][ OH-] ",
                "[Ag+][ Cl-]"));

        questionList.add(new Question("The titration useful for determining chloride in neutral or unbuffered solutions such as \n" +
                "drinking water is?",
                "Volhard titration",
                "Acid- base",
                "Adsorption indicator",
                "Mohr titration",
                "Mohr titration"));
    }

    private void getQuestionPhase5(List<Question> list) {
        questionList.add(new Question("In the Mohr titration, the ideal pH is ?",
                "2",
                "12",
                "8",
                "5",
                "8"));

        questionList.add(new Question(" ..................... method is the most recent and accurate among the precipitation titration \n" +
                "methods?",
                "Adsorption indicator",
                "Mohr",
                "Volhard",
                "Volhard",
                "Adsorption indicator"));

        questionList.add(new Question("In the Mohr titration method, the chloride is titrated with standard AgNO<sub>3</sub> " +
                "solution using an indicator. Which of these is the indicator? ",
                "iron (III) (red at the eqiuvalence point)",
                " Diclorofluorescein (Yellow at the equivalence point)",
                "Ag<sub>2</sub> CrO<sub>4</sub> (yellow at the equivalence point)",
                "Potassium chromate (yellow before the equivalence point)",
                "Potassium chromate (yellow before the equivalence point)"));

        questionList.add(new Question("Completeness of a precipitate reaction is determined by?",
                "the solubility of the precipitate formed ",
                "the duration taken for the reaction to occur",
                "the concentrations of the reacting mixtures",
                "the colour of the precipitate formed ",
                "the solubility of the precipitate formed"));

        questionList.add(new Question("The p- function is ",
                "the log to base 10 of the molar concentration of that specie ",
                "the function of activity in precipitation reactions",
                "the negative log to base 10 of the molar concentration of that specie",
                "unity always",
                "the negative log to base 10 of the molar concentration of that specie"));

        questionList.add(new Question("The different types of precipitation titrations include all except",
                "Mohr",
                "Adsorption indicators",
                "Fajans",
                "None of the above",
                "None of the above"));
        questionList.add(new Question("In Mohr titration, there is precipitation of some ---- with more alkaline solutions.",
                "Silver carbonate",
                "silver hydroxide",
                "Silver carbonate or Silver hydroxide",
                "None of the above",
                "Silver carbonate or Silver hydroxide"));

        questionList.add(new Question("Precipitating reagent should react with the analyte",
                "either specifically or, at least selectively to form a precipitate",
                "neither specifically nor selectively",
                "to give a coloured solution",
                "None of the above",
                "either specifically or, at least selectively to form a precipitate"));

        questionList.add(new Question("Precipitate must be",
                "readily filterable and washable",
                "filterable and soluble",
                "filterable and not washable",
                "not filterable, not washable",
                "readily filterable and washable"));

        questionList.add(new Question("In the titration of Silver nitrate with chloride ion during precipitation titration, before the \n" +
                "equivalence point we have?",
                "excess Chloride ion",
                "excess of silver ion",
                "equal amount of silver ion and chloride ion",
                "None of the above",
                "excess Chloride ion"));

        questionList.add(new Question("The pH at equivalence point of a weak acid / strong base titration depends on <br /> <br \n" +
                "/> I. The dissociation constant and concentration of the weak species <br /> <br /> II. The \n" +
                "dissociation constant and concentration of the strong species. <br /> <br /> III. The dissociation \n" +
                "constant of the acid and concentration of the base <br /> <br /> IV. The dissociation constant of \n" +
                "the base and the concentration of the acid",
                "I only",
                "II only",
                "III only",
                "IV only",
                "I only"));

        questionList.add(new Question("The pH at equivalence point of a weak acid / strong base titration depends on <br /> <br \n" +
                "/> I. The dissociation constant and concentration of the weak species <br /> <br /> II. The " +
                "dissociation constant and concentration of the strong species. <br /> <br /> III. The dissociation " +
                "constant of the acid and concentration of the base <br /> <br /> IV. The dissociation constant of " +
                "the base and the concentration of the acid",
                "I only",
                "II only",
                "III only",
                "IV only",
                "I only"));

        questionList.add(new Question("An ultrapure compound that serves as the reference materials for a titrimetric method of \n" +
                "analysis is known as <br /> <br /> A. I only B. II only C. III only D. IV only ",
                "I and IV",
                "II and III",
                "I and II",
                "I and III",
                "I and II"));

        questionList.add(new Question("The volume of a cylindrical rod is given by the equation V= &pi;r<sup>2</sup> h, where r \n" +
                "= radius, h =height and &pi; =3.143. If the values for r = 4.5 +0.1cm and h =11.7+ 0.02cm, calculate \n" +
                "the percentage error in the volume.",
                "7%",
                "6%",
                "10%",
                "9%",
                "6%"));

        questionList.add(new Question("The following statement is/are true of indicator behavior in an <br /> <br /> acid/base \n" +
                "titration. <br /> <br /> I. Most people cannot read colour change until more than 1 tenth of the \n" +
                "indicator has been converted to its conjugate form. <br /> <br /> II. The indicator act as second \n" +
                "acid or base and titrate after the primary titrant. <br /> <br /> III. An acid indicator acts as a \n" +
                "conjugate base that react with primary acid. <br /> <br /> IV. A base indicator acts as a conjugate \n" +
                "acid that react with the primary base.",
                "I and IV",
                "II and III",
                "III and IV",
                "I and II",
                "I and II"));

        questionList.add(new Question("Which of these is not a unit of concentration?",
                "mg/L",
                "ppm",
                "normality",
                "moles",
                "moles"));

        questionList.add(new Question("Which of the following statement is true of end-point of acid-base titration ; <br /> <br /> " +
                "I. Equivalence quantities of reactants have been brought into reaction <br /> <br /> II. The " +
                "presence of excess of titrant that give rise to indicator reaction <br /> <br /> III. The presence of " +
                "" +
                "excess of analyte that gives rise to indicator reaction <br /> <br /> IV. Equimolar quantities of " +
                "analyte and titrant that give rise to indicator reaction.",
                "I",
                "II",
                "III",
                "IV",
                "III"));

        questionList.add(new Question("What marks the sudden change in oxidation potential <br /> in the neighbourhood of the " +
                "equivalent point in an oxidation reduction titration?",
                "Acid base indicators",
                "Oxidation reduction indicators",
                "Oxidant",
                "Reductant",
                "Oxidation reduction indicators"));

        questionList.add(new Question("An acid – base indicator <br /> <br /> I. Is an organic dyes which are weak acids having " +
                "different colours for dissociated and undissociated forms. <br /> <br /> II. Is a weak acid or base " +
                "having different colours for dissociated and undissociated forms. <br /> <br /> III. Is an organic " +
                "acid or base having different colours for dissociated and undissociated forms <br /> <br /> IV. Is " +
                "any chemical that produce different colours for dissociated and undissociated forms ",
                "III only",
                "II only",
                "II and III only",
                "IV only",
                "III only"));

        questionList.add(new Question("A primary standard solution is one which contains <br /> <br /> I. A known amount of " +
                "reagent in a definite volume of solvent. <br /> <br /> II. A known mass of analytically pure " +
                "substance of it in a suitable solvent. <br /> <br /> III. A known mass of solute in a given volume of " +
                "solvent. <br /> <br /> IV. A known mass of analytically pure substance of it.",
                "II only",
                "I and II ",
                "II and III",
                "IV only",
                "II only"));

        questionList.add(new Question("Titration of 0.1M ammonia with 0.1M hydrochloric acid solution may occur at equivalence " +
                "point of between ?",
                "5 - 6",
                "6 - 7",
                "7 - 8",
                "4 - 5",
                "5 - 6"));

        questionList.add(new Question("During a typical acid - base titration, which of these colour changes would be observed at " +
                "pH transition of 1.2 - 2.8 from acidic to basic medium when thymol blue indicator is used? ",
                "Yellow to green",
                "Red to yellow",
                "yellow to blue",
                "Blue to colourless",
                "Red to yellow"));

        questionList.add(new Question("During a typical acid - base titration, which of these colour changes would be observed at " +
                "pH transition of 3.0 - 5.0 from acidic to basic medium when congo red indicator is used? ",
                "Red to yellow",
                "Yellow to blue",
                "Blue to red",
                "Colourless to pink",
                "Blue to red"));

        questionList.add(new Question("At what pH range is expected for methyl orange indicator to change from red to yellow " +
                "(acidic to basic transition)?",
                "3.0 to 4.6",
                "3.0 to 5.0",
                "3.2 to 4.4",
                "3.8 to 5.4",
                "3.2 to 4.4"));

        questionList.add(new Question("Which of the following indicator is suitable for the titration of ethanedioic acid with " +
                "sodium hydroxide solution ?",
                "Methyl orange",
                "Congo red",
                "phenolphthalein",
                "No suitable indicator",
                "phenolphthalein"));

        questionList.add(new Question("Calculate the average titre value from these sets of volume of acid used in a typical acid -" +
                "base titration. <br /> <br /> 26.50cm<sup>3</sup> ,26.95cm<sup>3</sup> ,26.55cm<sup>3</sup> " +
                ",26.54cm3",
                "26.53cm<sup>3</sup>",
                "26.64cm<sup>3</sup>",
                "26.68cm<sup>3</sup>",
                "26.67cm<sup>3</sup>",
                "26.53cm<sup>3</sup>"));

        questionList.add(new Question("In acid - base titration of NaOH versus HCl, methyl red indicator changes colour from",
                "pink-red",
                "pink-yellow",
                "red-yellow",
                "yellow-red",
                "yellow-red"));

        questionList.add(new Question("Which of these indicators can be used for a weak acid and a weak base?",
                "methyl orange and phenolpthalein",
                "methyl red and methyl orange",
                "Phenolpthalein and methyl red",
                "No suitable indicator",
                "No suitable indicator"));

        questionList.add(new Question("Potassium permanganate solution is normally stored in ?",
                "White bottle",
                "Yellow bottle",
                "Brown bottle",
                "Pink bottle",
                "Brown bottle"));

        questionList.add(new Question("Which of these pair compounds have the same oxidation states with that of common " +
                "elements? ",
                "SO<sub>3</sub> / H<sub>2</sub> SO<sub>4</sub>",
                "K<sub>2</sub> Cr<sub>2</sub> O<sub>7</sub> & Cr (OH)<sub>3</sub>",
                "CO & CO<sub>2</sub>",
                "FeO & Fe<sub>2</sub> O<sub>3</sub>",
                "SO<sub>3</sub> / H<sub>2</sub> SO<sub>4</sub>"));
    }
}