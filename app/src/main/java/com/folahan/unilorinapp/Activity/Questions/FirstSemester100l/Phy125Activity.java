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

public class Phy125Activity extends AppCompatActivity {

    private List<Question> questionList;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1;
    Button btnNext, btnPrev, btnEnd;
    private AlertDialog.Builder dialog;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phy125);

        questionList = new ArrayList<>();
        questionText = findViewById(R.id.questionText);
        answerText = findViewById(R.id.txtAnswer);
        btnEnd = findViewById(R.id.buttonGoto);
        rbOption1 = findViewById(R.id.radioA);
        rbOption2 = findViewById(R.id.radioB);
        rbOption3 = findViewById(R.id.radioC);
        rbOption4 = findViewById(R.id.radioD);
        questionNo = findViewById(R.id.question1);
        countDown = findViewById(R.id.timeText);
        Random random = new Random();

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
            if (questionAnswered == 11) {
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

        setListeners();
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


        questionList.add(new Question("A motor car is approaching a road crossing with a speed of 75km/hr. A constable standing " +
                "near the crossing hears the frequency of uts horn as 260 per sec. What is the real frequency of the horn? [Speed of air = 332m/s]",
                "A. 244/sec",
                "B. 311/sec",
                "C. 75/sec",
                "D. 260/sec",
                "A. 244/sec"));

        questionList.add(new Question("The minimum intensity of audibility of a source is 10^-16 watt/cm². If the frequency " +
                "of the note is 1000 c.p.s. Calculate the amplitude of vibration of air particles. [Density of air = 0.001293g/cm³ " +
                "and velocity of sound = 340m/s]",
                "A. 1.08 x 10^-4cm",
                "B. 2.08 x 10^-9cm", "C. 2.08 x 10^-4cm",
                "D. 1.08 x 10^-9cm",
                "D. 1.08 x 10^-9cm"));

        questionList.add(new Question("Find the mass of a neon atom. The atomic mass of neon is 20.2kg/kmol",
                "A. 2.26 x 10^-26 kg",
                "B. 3.36 x 10^-26 kg",
                "C. 4.4 x 10^-26 kg",
                "D. 3.0 x 10^-18 kg",
                "B. 3.36 x 10^-26 kg"));

        questionList.add(new Question("At what temperature will molecules of an ideal gas have twice the rms speed they have " +
                "at 20° C?",
                "A. 900° C",
                "B. 180° C",
                "C. 290° C",
                "D. 250° C",
                "A. 900° C"));

        questionList.add(new Question("A typical polymer molecule in polyethylene might have a molecular mass of 15 x 10^3. " +
                "What is the mass in kg of such a molecule?",
                "A. 8 x 10^-21 kg",
                "B. 2.5 x 10^23 kg",
                "C. 3.0 x 10^20 kg", "D. 2.5 x 10^-23 kg",
                "D. 2.5 x 10^-23 kg"));

        questionList.add(new Question("The pressure of helium gas in a tube is 0.200 mmHg. If the temperature of the gas is 20° C. " +
                "What is the density of the gas? (Use M[He] = 4.0kg/kmol.)",
                "A. 4.4 x 10^-5 kg/m³",
                "B. 3.8 x 1o^-4 kg/m³",
                "C. 1.5 x 10^-12 kg/m³" ,
                "D. 2.1 x 10^-12 kg/m³", "A. 4.4 x 10^-5 kg/m³"));

        questionList.add(new Question("In a certain region of outer space there are an averagee of only five mol/cm³. The temperature " +
                "there is about 3k. What is the average pressure of this very dilute gas?",
                "A. 2.8 x 10^-15 Pa", "B. 2 x 10^-16 Pa",
                "C. 3.5 x 10^-12 Pa",
                "D. 4.4 x 10^-8 Pa",
                "B. 2 x 10^-16 Pa"));

        questionList.add(new Question("The rms speed of nitrogen molecules in the air at S.T.P. is about 490m/s. FInd their mean free path " +
                " between collisions. The radius of a nitrogen molecule can be taken to be 2.0 x 10^-10m",
                "A. 5.2 x 10^-8", "B. 4.5 x 10^-10",
                "C. 2.3 x 10^-4",
                "D. 3.4 x 10^-10", "A. 5.2 x 10^-8"));

        questionList.add(new Question("What is the mean of free path of a gas molecule (radius 2.5 x 10^-10) in an ideal gas at " +
                "500° C when the pressure is 7.0 x 10^-6 mmHg",
                "A. 5m", "B. 12m",
                "C. 2.5m",
                "D. 10m", "D. 10m"));

        questionList.add(new Question("Ordinary nitrogen gas consists of molecules of N2. Find the mass of one such molecule. " +
                "The molecular mass is 28kg/mol",
                "A. 6.02 x 10^26 kg",
                "B. 2.6 x 10^-27 kg",
                "C. 4.7 x 10^-26 kg",
                "D. 3.0 x 10^-20 kg",
                "C. 4.7 x 10^-26 kg"));

        questionList.add(new Question("Find the rms speed of a nitrogen molecule (M = 28 kg/mol) in air at " +
                "0° C.",
                "A. 0.49km/s", "B. 0.9km/s" ,
                "C. 1.2km/s",
                "D. 2.0km/s",
                "A. 0.49km/s"));

        questionList.add(new Question("Helium gas consists of separate He atoms rather than molecules. How many helium atoms, He, " +
                "are there in 2.0 g of Helium? M = 4.0kg/kmol for He.",
                "A. 2.6 x 10^-26",
                "B. 3.0 x 10^23",
                "C. 3.0 x 10^-23",
                "D. 4.5 x 10^21",
                "B. 3.0 x 10^23"));

        questionList.add(new Question("Which of the following is not part of main components to Kinetic theory?",
                "A. No energy is gained or lost when molecules collide",
                "B. The molecules in a gas take up a negligible (able to be ignored) amount of space in relation to the container they occupy",
                "C. The molecules are in constant, linear motion",
                "D. None of the above",
                "D. None of the above"));

        questionList.add(new Question("Which of the following is not a means of heat transfer?",
                "A. Convection",
                "B. Radiation",
                "C. Magnetization",
                "D. Conduction",
                "C. Magnetization"));

        questionList.add(new Question("_____ is the mode of heat transfer between a solid surface and the adjacent fliud " +
                "that is in motion",
                "A. Convection",
                "B. Radiation",
                "C. Magnetization",
                "D. Conduction",
                "A. Convection"));
    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question("If the molecular mass of Oxygen is 32.0, what is the density of oxygen at pressure of 0.19998 x 10^5Nm^-2 " +
                "and the temperature of 273.16K?",
                "A. 0.3201 kg/m³",
                "B. 0.2818 kg/m³",
                "C. 1.2001 kg/m³",
                "D. 0.5413 kg/m³",
                "B. 0.2818 kg/m³"));

        questionList.add(new Question("The behavior of molecules of an ideal gas is ______",
                "A. Inelastic",
                "B. Compact",
                "C. Perfect Elastic",
                "D. Perfect Inelastic",
                "C. Perfect Elastic"));

        questionList.add(new Question("A forced convection occurs if ______",
                "A. the fluid is forced to flow over the surface by external means",
                "B. the fluid escapes over the surface by free fall",
                "C. the fluid is forced to compress in the container",
                "D. none of the above",
                "A. the fluid is forced to flow over the surface by external means"));

        questionList.add(new Question("_____ is the energy emitted by matter in the form of electromagnetic waves as a result " +
                "of the changes in the electronic configurations of the atoms or molecules.",
                "A. Radiation",
                "B. Photoelectric emission",
                "C. Thermionic emission",
                "D. Ultra violet",
                "A. Radiation"));

        questionList.add(new Question("_____ is the energy emitted by matter in the form of electromagnetic waves as a result " +
                "of the changes in the electronic configurations of the atoms or molecules.",
                "A. Radiation",
                "B. Photoelectric emission",
                "C. Thermionic emission",
                "D. Ultra violet",
                "A. Radiation"));
    }
}


