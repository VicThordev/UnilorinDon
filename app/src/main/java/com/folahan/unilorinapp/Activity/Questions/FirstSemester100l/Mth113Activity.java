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

public class Mth113Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1, clicked = 0;
    Button btnNext, btnPrev, btnEnd;
    private AlertDialog.Builder dialog;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mth113);

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
        random = new Random();
        setListeners();

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

        btnNext.setOnClickListener(view -> {
            questionAnswered++;
            pos = random.nextInt(questionList.size());
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

        btnEnd.setOnClickListener(view -> {
            setDataView(pos);
            getQuestionPhase1(questionList);
        });
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

        questionList.add(new Question("The dot product in the vector a.(b+c) = a.b+a.c",
                "A. Unity",
                "B. Commutative", "C. Parallel",
                "D. Distributive",
                "D. Distributive"));

        questionList.add(new Question("Find the equation of the line given that m=3 passing through point (2,1)",
                "A. y - 10x = 12",
                "B. y = 3x - 5",
                "C. y = 1x - 2",
                "D. y = 3x + 10",
                "B. y = 3x - 5"));

        questionList.add(new Question("Given that a = 3i-j+2k and b = 2i+3j-k. Find a X b",
                "A. 3i + 5j + 11k",
                "B. 11j - 7j + 5k", "C. -5i + 7j + 11k", "D. -3i + 5j - 11k",
                "C. -5i + 7j + 11k"));

        questionList.add(new Question("Find the projection of a on b. Given that a = 5i+3j+7k and " +
                "b = 2i-j+2k",
                "A. 7", "B. 14",  "C. -14" ,
                "D. 2", "A. 7"));

        questionList.add(new Question("Determine the equation of a parabola given its vertex is at the origin and " +
                "the distance of the focus from vertex is 4 units.",
                "A. y² = +/- 4",
                "B. y² = +/- 24",
                "C. y² = +/- 9",
                "D. y² = +/- 16",
                "D. y² = +/- 16"));

        questionList.add(new Question("Find the equation of the parabola with centre at the origin and " +
                "passes through the point (2, -1/2) with the axis of symmetry at y-axis",
                "A. y² = x/4",
                "B. y² = x/8",
                "C. y² = -x/2",
                "D. y² = x/16",
                "B. y² = x/8"));

        questionList.add(new Question("The scalar product of any two vector at right angle to each other is always",
                "A. 1", "B. 90 degrees", "C. 0", "D. -1",
                "C. 0"));

        questionList.add(new Question("If a = 2i+3j+5k and b = 4i+j+6k. Find a.b",
                "A. 41", "C. -25", "C. 30",
                "D. 54", "A. 41"));

        questionList.add(new Question("Find the area of the triangle whose vertices are P(1,-1,0) " +
                "Q(2,1,-1) R(-1,1,2)",
                "A. 3√2", "B. 6√2", "C. 4√2",
                "D. 2√3", "A. 3√2"));

        questionList.add(new Question("Find the equation of circle with centre (0,0) and radius 3",
                "A. 2x² + 2y² = 32",
                "B. x² + y² = 3",
                "C. x² + y² = 9",
                "D. y² + x² + 27 = 0",
                "C. x² + y² = 9"));

        questionList.add(new Question("Find the equation of the ellipse with foci (0,+/-3) & vertices " +
                "(0,+/-3)",
                "A. x²/9 + y²/5 = 1",
                "B. x²/9 + y²/4 = 1",
                "C. x²/-9 + y²/5 = 1",
                "D. x²/15 + y²/9 = 1",
                "A. x²/9 + y²/5 = 1"));

        questionList.add(new Question("A car travelling initially at a speed of 16m/s begins to accelerate uniformly" +
                " at 4m/s. Find the speed after 3 secs",
                "A. 30m/s",
                "B. 28m/s",
                "C. 4m/s",
                "D. 12m/s",
                "B. 28m/s"));

        questionList.add(new Question("Find the simplest form of the equation of circle" +
                " with centre (-1,-1) and radius 3",
                "A. x² + y² - x - y = 9",
                "B. x² + y² + 2x + 2y = 7",
                "C. x² + 2y² + 2x + 2y = 8",
                "D. x² + y² + 2x + 2y + 7 = 0",
                "B. x² + y² + 2x + 2y = 7"));

        questionList.add(new Question("In the general forms of equation, the co-efficients of x² and y² " +
                "are _____",
                "A. always zero",
                "B. one",
                "C. equal",
                "D. -1",
                "C. equal"));

        questionList.add(new Question("A car travelling from rest and moving with uniform acceleration covers " +
                "a distance of 19m in the 10th sec after starting. Find the acceleration.",
                "A. 3m/s²",
                "B. 2m/s²",
                "C. 4m/s²",
                "D. 12m/s²",
                "B. 2m/s²"));
    }

    private void getQuestionPhase1(List<Question> list) {

        questionList.add(new Question("If the focus does not belong to the directrix line and e = 1, " +
                "the conic is _____",
                "A. Parabola",
                "B. Hyperbola",
                "C. Unity",
                "D. Directrix",
                "A. Parabola"));

        questionList.add(new Question("A body is projected vertically upward with a velocity of 49m/s. Find the greatest " +
                "height the body will reach and the time taken. (Take g = 9.8m/s²)",
                "A. s = 19.6m, t = 5 secs",
                "B. s = 5m, t = 22 secs",
                "C. s = 5m, t = 19.6 secs",
                "D. s = 122.5m, t = 5 secs",
                "D. s = 122.5m, t = 5 secs"));

        questionList.add(new Question("A 12g bullet is accelerated from rest to a speed of 700mls as it travels 20cm in a given barrel." +
                "Assuming the acceleration to be constant, how large is the accelerating force?",
                "A. 14800N",
                "B. 1225000N",
                "C. 800N",
                "D. 1600N",
                "A. 14800N"));

        questionList.add(new Question("A resultant force of 20N gives a body of mass m1 and acceleration of 8m/s²" +
                "and body of mass m2 and acceleration of 24m/s². What acceleration will this force cause the two masses " +
                "to acquire it fastened together?",
                "A. 8m/s²",
                "B. 32m/s²",
                "C. 6m/s²",
                "D. 16m/s²",
                "C. 6m/s²"));

        questionList.add(new Question("A body at rest and of mass 10kg is acted upon by a force of 20N for 0.5 secs. " +
                "Find the increase in momentum",
                "A. 8Ns",
                "B. 12Ns",
                "C. 10Ns",
                "D. 14Ns",
                "C. 10Ns"));

        questionList.add(new Question("A ball of mass 10kg moving at 5m/s collide with another mass of 4kg " +
                "moving at 2m/s in the same direction. If e = ½, find the velocities V1 & V2",
                "A. V1 = 31/6 & V2 = 14/6",
                "B. V1 = 26/7 & V2 = 73/14",
                "C. V1 = 33/5 & V2 = 42/8",
                "D. V1 = 5 & V2 = ½",
                "B. V1 = 26/7 & V2 = 73/14"));

        questionList.add(new Question("Find the length of a straight line joining the points A(5,4) & B(9,10)",
                "A. √52",
                "B. √32",
                "C. √10",
                "D. √27",
                "A. √52"));

        questionList.add(new Question("Find the length of a straight line joining the points A(1,3) & B(5,4)",
                "A. √52",
                "B. √92",
                "C. √10",
                "D. √17",
                "D. √17"));

        questionList.add(new Question("Find the centre & radius of the circle equation x² + y² + 2x - 6y = 15",
                "A. centre = (1,-3) and radius = 5",
                "B. centre = (2,-6) and radius = 5",
                "C. centre = (1,-6) and radius = 3",
                "D. centre = (1,-3) and radius = 3",
                "A. centre = (1,-3) and radius = 5"));

        questionList.add(new Question("Find the length of a straight line joining the points A(-5,3) & B(6,5)",
                "A. √53",
                "B. √2",
                "C. √110",
                "D. √37",
                "A. √53"));

        questionList.add(new Question("Find the co-ordinate of the mid-point of the line joining the points A(-7,-5) & " +
                "B(11,-9)",
                "A. 6,6",
                "B. 2,-2",
                "C. 5,2",
                "D. 2,1",
                "B. 2,-2"));

        questionList.add(new Question("Find the co-ordinate of the mid-point of the line joining the points T(3,4) & " +
                "R(9,8)",
                "A. 6,6",
                "B. 2,-1",
                "C. -3,2",
                "D. 2,-4",
                "A. 6,6"));

        questionList.add(new Question("Find the dot product between the vector a and b. " +
                "Given that a=1+2j+3k and b=2i-j-2k", "A. -6",
                "B. -10", "C. 16", "D. 10",
                "A. 6"));

        questionList.add(new Question("Find the co-ordinate of the mid-point of the line joining the points O(-2,3) & " +
                "P(-4,6)",
                "A. 3,6",
                "B. 2,-1",
                "C. -3,3/2",
                "D. 3,-4",
                "C. -3,3/2"));

        questionList.add(new Question("Find the equation of the line of points A(-4,3) and B(2,-5)",
                "A. 7y + 10x = 22",
                "B. 7y - 10x = 12",
                "C. y = 10x - 22",
                "D. y = 3x + 10",
                "A. 7y + 10x = 22"));
    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question("Find the equation of the line which is parallel to the line (2y+x)=3 " +
                "and passing through a mid point with points (-2,3) & (4,5)",
                "A. y + x/2 = 9/2",
                "B. y + x = 9", "C. 2y + 2x= 9",
                "D. y + 2x = 2",
                "A. y + x/2 = 9/2"));

        questionList.add(new Question("Find the equation of circle for (-1,2) and radius 3",
                "A. x² + y² + 4x + 4y = -4",
                "B. x² + y² - 2x - 4y = -4",
                "C. 2x² + 2y² + 2x - 4y = 4",
                "D. x² + y² + 2x - 4y = 4",
                "D. x² + y² + 2x - 4y = 4"));

        questionList.add(new Question("Find the equation of circle with centre (-2,3) which passes through the point (1,2).",
                "A. x² + y² + 4x + 6y = -3",
                "B. x² + y² + 4x + 6y -3 = 0",
                "C. x² + y² - 4x + 6y = -3",
                "D. x² + y² - 4x + 6y -3 = 0",
                "A. x² + y² + 4x + 6y = -3"));

        questionList.add(new Question("What is the radius of the equation x² + y² = 121",
                "A. r = 12",
                "B. r = 2",
                "C. r = 11",
                "D. r = 10",
                "C. r = 11"));

        questionList.add(new Question("Find the radius of the equation 2x² + 2y² = a²",
                "A. r = a√2/3",
                "B. r = 2√3",
                "C. r = 1√2",
                "D. r = 10√3",
                "A. r = a√2/3"));

        questionList.add(new Question("Find the simplest form of the equation of circle" +
                " with centre 0 and radius 2",
                "A. 2x² + 2y² = 0",
                "B. x² + y² = 0",
                "C. x² + 2y² = 8",
                "D. x² + y² = 4",
                "D. x² + y² = 4"));

        questionList.add(new Question("Evaluate i.i=j.j=k.k=",
                "A. 0", "B. √2", "C. 1√2", "D. 1", "D. 1"));

        questionList.add(new Question("Determine the equation of the circle with centre (3,-2) which passes " +
                "through the point (-1,-5)",
                "A. x² + y² + 4x - 6y = -12",
                "B. x² + y² - 6x + 4y - 12 = 0",
                "C. x² + y² + 4x + 6y = -2",
                "D. x² + y² - 3x + 4y = -10",
                "B. x² + y² - 6x + 4y - 12"));

        questionList.add(new Question("Which of the following equation correctly represent a circle?",
                "A. x² + y² + 3x - 1 = 0",
                "B. 2x² + 2y² - x = 0",
                "C. x² - y² + 4x - 6y = -2",
                "D. x² + 2xy² - 3x + 4 = -10",
                "B. 2x² + 2y² - x = 0"));

        questionList.add(new Question("A force F newton acts on a body of mass 5kg travelling 2.5m/s² for 0.2 secs." +
                " If the final velocity of the body is 7.5m/s. Find force F in newtons",
                "A. 100N",
                "B. 120N",
                "C. 10N",
                "D. 125N",
                "C. 125N"));

        questionList.add(new Question(" Find the centre & radius of the circle equation x² + y² + x + 4y - 2 = 0",
                "A. centre = (-2,-3) and radius = 2",
                "B. centre = (2,-6) and radius = 5",
                "C. centre = (1/2,2) and radius = 5/2",
                "D. centre = (1,-3) and radius = 3",
                "C. centre = (1/2,2) and radius = 5/2"));

        questionList.add(new Question("Find the equation of the tangent to the x² + y² + 2x + 4y = 15 " +
                "at the point (-1,2)",
                "A. 6y - x + 32 = 0",
                "B. 4y + 2x = 15",
                "C. x - y = 15",
                "D. 2x + 4y = 0",
                "A. 6y - x + 32 = 0"));

        questionList.add(new Question("Given that a = 5i+2j-k and b = i-3j+k",
                "A. -2i + 12j + 34k",
                "B. 2i + 12j + 26k",
                "C. 2i - 12j + 34k",
                "D. 2i + 12j + 34k",
                "D. 2i + 12j + 34k"));

        questionList.add(new Question("Determine the position vector of P(3,-4,12). Find the unit position in the direction of OP",
                "A. 3i/13 - 4j/13 + 12k/13",
                "B. 4i/13 - 3j/13 + 12k/13",
                "C. 3i/13 + 4j/13 - 12k/13",
                "D. i/13 - 7j/13 + 12k/13",
                "A. 3i/13 - 4j/13 + 12k/13"));

        questionList.add(new Question("Find the equation of the tangent to the x² + y² - 6x - 3y = 16 " +
                "at the point (-2,0)",
                "A. 6y - 4x + 32 = 0",
                "B. 40y + 2x = 115",
                "C. x + y = 15",
                "D. 12y + 40x + 119 = 0",
                "D. 12y + 40x + 119 = 0"));
    }

    private void getQuestionPhase3(List<Question> list) {

        questionList.add(new Question("A force of 20N is applied in the direction north east to a load." +
                "Find the component of the force",
                "A. 10√2(i+j)",
                "B. 2i+3√2j", "C. 2√2(i-j)",
                "D. i+20j",
                "A. 10√2(i+j)"));

        questionList.add(new Question("If A = (3u²+4)i + (2u-5)j + 4u³k. Find dA/du when u = 2",
                "A. 2i - 12j + 48k", "B. 12i + 2j + 48k" , "C. -5i + 12j - 18k",
                "D. 10i - 12j + 48k",
                "B. 12i + 2j + 48k"));

        questionList.add(new Question("A vendor pushes a load on a cart by force of 50N making an angle of 30 degrees " +
                "with the vertical. What is its horizontal component?",
                "A. 25√3N",
                "B. 25N", "C. 50√3",
                "D. 25√2/2",
                "B. 25N"));

        questionList.add(new Question("If e > 1, the conic is _____",
                "A. Parabola",
                "B. Hyperbola",
                "C. Unity",
                "D. Directrix",
                "B. Hyperbola"));

        questionList.add(new Question("A car's odometer reads 22,687km at the start of a trip and 22791km at the end " +
                ". The trip took 4hrs. What is the car's average speed?",
                "A. 7.22m/s",
                "B. 3.5m/s",
                "C. 22m/s",
                "D. 20m/s",
                "A. 7.22m/s"));

        questionList.add(new Question("A force acts on a 2kg mass and gives it an acceleration of 3m/s². What acceleration is " +
                "produced by the same force when acting on a mass of 4kg",
                "A. 7m/s²",
                "B. 3/2m/s²",
                "C. 6m/s²",
                "D. 3m/s²",
                "B. 3/2m/s²"));

        questionList.add(new Question("Determine the equation of the hyperbola if the distance between its vertices " +
                "is 20 & the distance between its foci is 30.",
                "A. x²/20 - y²/30 = 1",
                "B. x²/400 + y²/900 = 1",
                "C. x²/100 - y²/125 = 1",
                "D. x²/5 + y²/9 = 1",
                "C. x²/100 - y²/125 = 1"));

        questionList.add(new Question("Determine the equation of the hyperbola if the eccentricity is 2 and " +
                "the distance between its foci is 4√2",
                "A. x²/2 - y²/3 = 1",
                "B. x²/2 - y²/6 = 1",
                "C. x²/10 - y²/12 = 1",
                "D. x²/15 + y²/10 = 1",
                "B. x²/2 - y²/6 = 1"));
    }
}


