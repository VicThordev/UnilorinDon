package com.folahan.unilorinapp.Activity.Questions.SecondSemester100l;

import androidx.appcompat.app.AppCompatActivity;

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

public class Chm112Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1;
    Button btnNext, btnPrev;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chm132);

        questionList = new ArrayList<>();
        questionText = findViewById(R.id.questionText);
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

        btnNext.setOnClickListener(view -> {
            questionAnswered++;
            pos = random.nextInt(questionList.size());
            setDataView(pos);
        });
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


        questionList.add(new Question("1. Calculate the distance between point charges, 26.0μC and 47.0μC, if the magnitude of the " +
                "electrostatic force between them is 5.70N",
                "A. 150.6cm",
                "B. 138.9cm",
                "C. 148.7cm",
                "D. 165.3cm",
                "B. 138.9cm"));

        questionList.add(new Question("2. At what distance would the repulsive force between two electrons have a magnitude of one newton?",
                "A. 0.0152pm",
                "B. 0.0142pm",
                "C. 0.0132pm",
                "D. 0.0122pm",
                "A. 0.0152pm"));

        questionList.add(new Question("3. How many excess elections must be placed on each of two small spheres spaced 3cm apart, if the " +
                "force of repulsion between the spheres is to be 10-19N?",
                "A. 125 electrons ",
                "B. 250 electrons",
                "C. 625 electrons",
                "D. 750 electrons",
                "C. 625 electrons"));

        questionList.add(new Question("4. What is the total positive charge in Coulombs, of all the protons in 1mol of Hydrogen atoms?",
                "A. 96.35kC",
                "B. 72.46kC",
                "C. 83.48kC",
                "D. 78.36kC",
                "A. 96.35kC"));

        questionList.add(new Question("5. An α-particle is a nucleus of doubly-ionised helium. It has a mass of 6.69 x 10^-27kg and a charge of -2e. " +
                "Compute the ratio of the force of electrostatic repulsion between two α-particles to the force of " +
                "gravitational attraction between them",
                "A. 6.2 x 10^35",
                "B. 4.2 x 10^35",
                "C. 3.1 x 10^35",
                "D. 2.1 x 10^35",
                "C. 3.1 x 10^35"));

        questionList.add(new Question("6. Compute the ratio of the electric force of attraction to the gravitational force of attraction between \n" +
                "the electron and the proton in a hydrogen atom assuming that their distance of separation is 0.53pm \n" +
                "(me = 9.1 x 10^-31kg, mp = 1.7 x 10^-27kg, e = 1.6 x 10^-19C)",
                "A. 2.2 x 10^39",
                "B. 1.1 x 10^39",
                "C. 2.1 x 10^39" ,
                "D. 1.2 x 10^39", "A. 2.2 x 10^39"));

        questionList.add(new Question("7. What is the magnitude of electrostatic force of attraction between an α-particle and an electron 10^-" +
                "13m apart?",
                "A. 2.3 x 10^-2N", "B. 2.6 x 10^-2N",
                "C. 4.3 x 10^-2N", "D. 4.6 x 10^-2N",
                "D. 4.6 x 10^-2N"));

        questionList.add(new Question("8. From the Coulomb's Law, what will be the slope of the graph of Log F vs Log r? ",
                "A. ½", "B. 2",
                "C. -log2",
                "D. -2",
                "A. ½"));

        questionList.add(new Question("9. Two equal charges of equal magnitude exert an attractive force of 4.0 x 10^-4N on each other. If the " +
                "magnitude of each charge is 2.0μC, how far apart are the charges?",
                "A. 9.5m",
                "B. 9.7m",
                "C. 10.5m",
                "D. 10.7m",
                "A. 9.5m"));

        questionList.add(new Question("10. What is the magnitude of a point charge whose electric field 50cm away has magnitude 2.0N/C?",
                "A. 4.56 x 10^-11C",
                "B. 4.66 x 10^-11C",
                "C. 5.56 x 10^-11C ",
                "D. 5.66 x 10^-11C", "C. 5.56 x 10^-11C "));

        questionList.add(new Question("11. What is the magnitude of an electric field in which the force on an electron is equal in magnitude to " +
                "the weight of an electron?",
                "A. 4.58 x 10^-11N/C",
                "B. 5.58 x 10^-11N/C" ,
                "C. 6.58 x 10^-11N/C",
                "D. 7.58 x 10-^11N/C",
                "B. 5.58 x 10^-11N/C"));

        questionList.add(new Question( "12. A small object carrying a charge of 5 x 10^-9" +
                "C experiences a downward force of 20 x 10^-9N when" +
                "placed at a certain point in an electric field. What's the electric field at the point?",
                "A. 100 N/C",
                "B. 40 N/C",
                "C. 10 N/C",
                "D. 4 N/C",
                "D. 4 N/C"));

        questionList.add(new Question( "13. The distance between two positive charges 6μC & 8μC is 50cm. Calculate the electric field intensity, " +
                "due to each charges, at a point p in between the two charges and 10cm from the 6μC charge" +
                "respectively",
                "A. 5.4 x 10^6N/C, 4.5 x 10^5N/C",
                "B. 6.4 x 10^6N/C, 5.5 x 10^5N/C",
                "C. 7.4 x 10^6N/C, 6.5 x 10^5N/C",
                "D. 8.4 x 10^6N/C, 7.5 x 10^5N/C",
                "A. 5.4 x 10^6N/C, 4.5 x 10^5N/C"));

        questionList.add(new Question( "14. Find the electric field at a point 0.2m from a charge of 20μC, what force will the electric field exert " +
                "on a charge of 10μC, placed at that point?" +
                "education. ",
                "A. 3.5 x 10" +
                        "^6N/C, 35N",
                "B. 4.5 x 10^6N/C, 45N",
                "C. 5.5 x 10^6N/C, 55N",
                "D. 6.5 x 10^6N/C, 65N",
                "B. 4.5 x 10^6N/C, 45N"));

        questionList.add(new Question( "15. The magnitude of electric field is 40kN/C. If an electron is placed in the same field, what force will be \n" +
                "exerted on it?",
                "A. 3.4 x 10^-15N",
                "B. 4.4 x 10^-15N",
                "C. 5.4 x 10^-15N",
                "D. 6.4 x 10^-15N",
                "D. 6.4 x 10^-15N"));

        questionList.add(new Question( "16. Words combine to form __________.",
                "(A) " +
                        "sentences",
                "(B) clauses. ",
                "(C) phrases. ",
                "(D) morphemes",
                "(C) phrases. "));

        questionList.add(new Question( "17. An important feature of language4 is its \n" +
                "___________ ",
                "(A) structure",
                "(B) texture.",
                "(C) strength.",
                "(D) stature.",
                "(A) structure"));

        questionList.add(new Question( "18. The English as a second \n" +
                "language countries include all except ________.",
                "(A) Ghana.",
                "(B) India.",
                "(C) Nigeria. ",
                "(D) USA",
                "(D) USA"));

        questionList.add(new Question( "19. " +
                "________ to come this weekend to listen to the broadcast of the football match?",
                "(A) Do you like,",
                "(B) " +
                        "Would you like. ",
                "(C) Are you like.",
                "(D) Will you like.",
                "(B) " +
                        "Would you like. "));

        questionList.add(new Question( " 20. Factors affecting study skills include all \n" +
                "except_________.",
                "(A) hybridizing.",
                "(B) the study time.",
                "(C) the study venue",
                "(D) reading aloud to " +
                        "oneself.",
                "(A) hybridizing."));

        questionList.add(new Question( "21. Computers are now cheap_______ for nearly everyone to afford it.",
                "(A) enough.",
                "(B) " +
                        "so. ",
                "(C) too.",
                "(D) quite. ",
                "(A) enough."));

        questionList.add(new Question( "22. You’re looking __________ pretty today, Temy.",
                "(A) very.",
                "(B) " +
                        "attractively. ",
                "(C) beautifully.",
                "(D) too. ",
                "(A) very."));

        questionList.add(new Question( "23. I promise to do my _________best.",
                "(A) possible.",
                "(B) " +
                        "very.",
                "(C) feasible.",
                "(D) variable. ",
                "(B) " +
                        "very."));

        questionList.add(new Question( "24. Water ________hydrogen and oxygen.",
                "(A) Varies between.",
                "(B) migrates.",
                "(C) consists of.",
                "(D) " +
                        "corresponds to",
                "(C) consists of."));

        questionList.add(new Question( "25. Agnes left the school very late, _____________________ ",
                "(A) didn’t she?",
                "(B) isn’t it?",
                "(C) " +
                        "hasn’t she? ",
                "(D) hadn’t she? ",
                "(A) didn’t she?"));

        questionList.add(new Question( "26. “Soyinka is good at play writing and Achebe is good at prose writing” Identify the sentence type.",
                "(A) Complex.",
                "(B) simple.",
                "(C) compound.",
                "(D) compound complex",
                "(C) compound."));

        questionList.add(new Question( "27. “ Mathematic _________compulsory for engineering students”.",
                "(A) is",
                "(B) as",
                "(C) are",
                "(D) " +
                        "were",
                "(A) is"));

        questionList.add(new Question( "28. The boy and the girl _______gone home.",
                "(A) has.",
                "(B) have.",
                "(C) had.",
                "(D) is. ",
                "(B) have."));

        questionList.add(new Question( "29. Which of the following is not a quality of good English?",
                "(A) coherence",
                "(B) emphasis or " +
                        "focus. ",
                "(C) unity.",
                "(D) none of the above. ",
                "(D) none of the above. "));

        questionList.add(new Question( "30. Inspite of the seemingly scarcity of marriageable men today many a girl __________ to get \n" +
                "married at the age of twenty-one.",
                "(A) Intend.",
                "(B) intended",
                "(C) plans",
                "(D) plan. ",
                "(C) plans"));
    }
}