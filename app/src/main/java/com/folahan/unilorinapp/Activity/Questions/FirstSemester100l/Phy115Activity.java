package com.folahan.unilorinapp.Activity.Questions.FirstSemester100l;

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

public class Phy115Activity extends AppCompatActivity {

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
        setContentView(R.layout.activity_phy115);

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
                int minutes = mTimeLeft / 1000 / 60;
                int secs = (mTimeLeft/1000) % 60;
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


        questionList.add(new Question("1. A curtain blown by air bla bla which of this can be used to describe its motion",
                "A. Archimedes principle",
                "B. Stokes law ", "C. Bernoulli's equation",
                "D. Equation of continuity",
                "C. Bernoulli's equation"));

        questionList.add(new Question("2. A body with initial velocity 135m/s was projected at an angle 45°. What is the time taken to reach the maximum height?",
                "A. 7.8s",
                "B. 9.74s",
                "C. 9.81s",
                "D. 6.78s",
                "B. 9.74s"));

        questionList.add(new Question("3. Which of the following rep equation of continuity",
                "A. A1/V1 = A2/V2 ",
                "B. A1V1 = A2V2", "C. A1V2 = A2V1 ",
                "D. A1/V2 = A2/V1",
                "B. A1V1 = A2V2"));

        questionList.add(new Question("4. Contact angle for a fluid rising in a tube is",
                "A. 0 degree",
                "B. Acute", "C. Obtuse",
                "D. 90 degrees",
                "B. Acute"));

        questionList.add(new Question("5. Which of these cannot be a unit of impulse?",
                "A. Js/m",
                "B. Nm-1", "C. Ns", "D. kgms-1",
                "B. Nm-1"));

        questionList.add(new Question("6. If a body of mass of 12.2kg of initial speed 15.0m/s collide with another body of mass 2.2kg of initial speed 28.4 m/s moving in the same direction. Find the kinetic energy that is transferred in the collision if the collision is inelastic",
                "A. 180J", "B. 169J",  "C. 167J",
                "D. 160J", "C. 167J"));

        questionList.add(new Question("7. For a pendulum at equilibrium at Q and maximum displacement at P & R. Which of these is not true?",
                "A. No potential energy at Q", "B. Maximum P.E at P and R",
                "C. Maximum P.E at Q", "D. Maximum K.E at Q",
                "C. Maximum P.E at Q"));

        questionList.add(new Question("8. Then the formula for flow rate is what if the cross-sectional area is A and the average speed is V",
                "A. J=A/V", "B. J=AV",
                "C. J=AV2",
                "D. J=A2/V",
                "B. J=AV"));

        questionList.add(new Question("9. If two masses, 1g and 4g, have the same Kinetic energy. Calculate the ratio of the linear momentum of the two masses",
                "A. 1: 16", "B. 1:4", "C.  1:2",
                "D. 1:8", "C.  1:2"));

        questionList.add(new Question("10. Angular position is the",
                "A. angle of reference relative to a dynamic direction",
                "B. angle of reference relative to a direction",
                "C. angle of reference relative to a fixed direction",
                "D. All the options are correct", "C. angle of reference relative to a fixed direction"));

        questionList.add(new Question("11. A particle undergoes a change in position from P1 to P2. This is called",
                "A. Angular displacement", "B. Angular acceleration" ,
                "C. Distance",
                "D. Velocity",
                "C. Distance"));

        questionList.add(new Question("12. If the inertia of a body is given as I=(Ma2/2),what is the radius of gyration?",
                "A. (a2/2)1/2",
                "B. a2/2",
                "C. a/2",
                "D. a/4",
                "A. (a2/2)1/2"));

        questionList.add(new Question("13. Which of these is true for two objects undergoing elastic collision\n" +
                "I. KEi=KEf\n" +
                "II.total momentum is conserved in an elastic collision \n" +
                "III. the two objects bounce off and move with different velocities\n/2),what is the radius of gyration?",
                "A. I & II",
                "B. I & III",
                "" +
                        "" +
                        "B. I & III\n",
                "D. All of the above",
                "D. All of the above"));

        questionList.add(new Question("14. Stress leads to/causes/2),what is the radius of gyration?",
                "A. Strain and deformation",
                "B. Strain and Reformation",
                "C. Strain and compression",
                "D. Strain and extension",
                "A. Strain and deformation"));

        questionList.add(new Question("15. Dimensions helps to ...",
                "A. reform equation",
                "B. derive equation",
                "C. all of the above",
                "D. none of the above",
                "B. derive equation"));

        questionList.add(new Question("16. The formula for maximum height is",
                "A. 2usinα/g",
                "B. u²sin²α/g",
                "C. 2usinα/g",
                "D. u²sin²α/2g",
                "D. u²sin²α/2g"));
    }
}