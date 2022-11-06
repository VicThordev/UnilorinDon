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
import com.folahan.unilorinapp.Model.QuestionList;
import com.folahan.unilorinapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Mth112Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1;
    Button btnNext, btnPrev, btnEnd;
    private AlertDialog dialog;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mth112);

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
            if (questionAnswered == 50) {
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


        questionList.add(new Question("If y = (x-7)^6. Find the derivative of y",
                "A. 7x(x-7)^5",
                "B. 6(x-7)^5",
                "C. 6x(x-7)^6",
                "D. 7x(x-7)^6",
                "B. 6(x-7)^5"));

        questionList.add(new Question("Given that y = (2x^2 + sin x - 3x)^5. Find dy/dx",
                "A. 5(2x^2 + sin x - 3x).(4x)",
                "B. 4(2x^2 + sin x - 3x)^-4.(4x^2 + sin x + 3)",
                "C. 5(2x^2 + sin x - 3x)^4.(4x + cos x - 3)",
                "D. 3(2x^2 + sin x - 3x)^5.(2x + cos x - 3)",
                "C. 5(2x^2 + sin x - 3x).(4x + cos x - 3)"));

        questionList.add(new Question("What is the derivative of y = tan^3(4x - 6)",
                "A. sec^2 (4x - 6)",
                "B. 4sec^2 (4x - 6)",
                "C. 4tan^2 (4x - 6)",
                "D. 4sec^6 (4x - 6)",
                "D. 4sec^6 (4x - 6)"));

        questionList.add(new Question("Find the derivative of the equation y = e^(2x-3)",
                "A. 2e^(2x-3)",
                "B. e^(2x-3)",
                "C. 2e^(2x+3)",
                "D. 2e^(2x^2-3)",
                "A. 2e^(2x-3)"));

        questionList.add(new Question("y = xe^(x²)sin x find dy/dx",
                "A. 2x^2e^(x^2)cosx",
                "B. 2xe^(x^2)cosx",
                "C. 2x^2e^(x^2)sin x",
                "D. 2x^2e^(x^2)(-cosx)",
                "A. 2x^2e^(x^2)cosx"));

        questionList.add(new Question("Find the equation of the tangent to the curve y = x² - x- 2" +
                " at point (2,-2)",
                "A. y = 2x + 6",
                "B. y = 2x - 2",
                "C. y = 3x - 8" ,
                "D. y = 2x + 4", "C. y = 3x - 8"));

        questionList.add(new Question("Determine the equation of the normal to the curve y=x³/5 at point x = 1",
                "A. (-25x + 28)/15", "B. (-2x - 14)/15",
                "C. (5x + 28)/5", "D. (-25x + 14)/14",
                "A. (-25x + 28)/15"));

        questionList.add(new Question("Determine the stationery point of the curve y = x³/3 + x² - 3x + 4",
                "A. x = -1 or -3", "B. x = 3 or -4",
                "C. x = 1 or -3",
                "D. x = -3 or 4",
                "C. x = 1 or -3"));

        questionList.add(new Question("A tank with a square base of the side x has a total surface area of 600m². " +
                "Determine the maximum volume of the tank.",
                "A. 950m³",
                "B. 970m³",
                "C. 1050m³",
                "D. 1000m³",
                "D. 1000m³"));

        questionList.add(new Question("What is the integral of sec²x?",
                "A. tan x + c",
                "B. cot x + c",
                "C. sec x cot x + c",
                "D. -sinxcosx + c", "A. tan x + c"));

        questionList.add(new Question("Integrate ∫cos3xdx",
                "A. 1/3(sin3xcosx) + c",
                "B. 1/3(-sin3x) + c" ,
                "C. 1/3(sin3x) + c",
                "D. 1/3(cos3x) + c",
                "C. 1/3(sin3x) + c"));

        questionList.add(new Question( "Solve ∫x√(x+3)dx",
                "A. 3/5(x-2)(x-3)^3/2 + c",
                "B. 5/2(x+2)(x+3)^3/2 + c",
                "C. 2/5(x+2)(x-3)^3/2 + c",
                "D. 2/5(x-2)(x+3)^3/2 + c",
                "D. 2/5(x-2)(x+3)^3/2 + c"));

        questionList.add(new Question( "Solve ∫(x²-2)/(x-1)dx",
                "A. x²/2 + x - ln(x-1) + c",
                "B. x/2 + x² - ln(x-1) + c",
                "C. x³/2 + x² - ln(x-1) + c",
                "D. x³/2 + x - ln(x+1) + c",
                "A. x²/2 + x - ln(x-1) + c"));

        questionList.add(new Question( "Obtain the maclurin series of e^x",
                "A. 2.0001",
                "B. 2.7182",
                "C. 1.8789",
                "D. 1.0911",
                "B. 2.7182"));

        questionList.add(new Question( "Obtain the maclurin of log(1+x)",
                "A. x - x²/2 + x^4/4 - x^6/6 + x^8/8",
                "B. x - x²/2 + x³/3 - x^4/4 + x^5/5",
                "C. x - x/2 + x³/3 + x^4/4 + x^5/5",
                "D. x + x²/2 - x³/3 + x^4/4 + x^5/5",
                "B. x - x²/2 + x³/3 - x^4/4 + x^5/5"));
    }

    private void getQuestionPhase1(List<Question> list) {

        questionList.add(new Question("Find the limit of x where x = cos (3x - π/2)",
                "A. 1",
                "B. -1",
                "C. -½",
                "D. √2/2",
                "B. -1"));

        questionList.add(new Question("Obtain the limit of x in the equation x =  (e^x - 1)/x; x-> 0",
                "A. 1",
                "B. -1",
                "C. -½",
                "D. 2",
                "A. 1"));

        questionList.add(new Question("Find the derivative of y = 5x/(2x²+4)",
                "A. 5(2-x²)/2(x²+2)²",
                "B. 2(2+x²)/2(x+2)²",
                "C. 5(2-x²)/2(x+2)",
                "D. 5(2+x)/2(x²+2)²",
                "A. 5(2-x²)/2(x²+2)²"));

        questionList.add(new Question("Find the derivative of y = (3x²+2)^9",
                "A. 54x(3x²+2)^9.(9x²)",
                "B. 9x(3x²+2)^8.(3x²)",
                "C. 54x(3x²+2)^8.(9x²)",
                "D. 54x(3x²+2)^8",
                "D. 54x(3x²+2)^8"));

        questionList.add(new Question("Find the derivative of y = √(1-2x³)",
                "A. -3x/√(1-2x²)",
                "B. -3x/√(1+2x³)",
                "C. -3x²/√(1-2x³)",
                "D. -x²/√(1+2x²)",
                "C. -3x²/√(1-2x³)"));

        questionList.add(new Question("If the area of a circle is increasing at the rate of 4cm²/s. " +
                "Find the rate of the change of the circumference when the radius is 6cm",
                "A. 4/6 cm/s",
                "B. 6/4 cm/s",
                "C. 6 cm/s",
                "D. 4 cm/s",
                "A. 4/6 cm/s"));

        questionList.add(new Question("The distance x covered by a car with time t is given by x =" +
                " 3t³-2t²+4t-1. Find the velocity of the car at time t = 1 & acceleration respectively",
                "A. 6m/s, 9m²/s",
                "B. 14m/s, 9m²/s",
                "C. 9m/s, 14m²/s",
                "D. 9m/s, 6m²/s",
                "A. 4/6 cm/s"));

        questionList.add(new Question("Find the derivative of y = sin(3x²+5)",
                "A. 9xcos(3x+5)",
                "B. 3xsin(3x²+5)",
                "C. 3xcos(3x²+5)",
                "D. 6xcos(3x²+5)",
                "D. 6xcos(3x²+5)"));

        questionList.add(new Question("Find dy/dx of y = x^x",
                "A. x^x(1+Log x)",
                "B. x(1+Log x^x)",
                "C. x(1+Log e)",
                "D. x^x(1+Log x^x)",
                "A. x^x(1+Log x)"));

        questionList.add(new Question("Obtain the taylor series of log(x+h)",
                "A. log x + h/x - h²/2x² + h³/2x³ - h^4/4x^4",
                "B. log x + h/x - h²/2x² + h³/2x³ - h^4/4x²",
                "C. log x - h/x + h²/2x² + h³/2x³ - h^4/4x²",
                "D. log x + h/x² + h²/2x² + h³/2x³ - h^4/4x²",
                "A. log x + h/x - h²/2x² + h³/2x³ - h^4/4x^4"));
    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question("If (1 - tan²67½)/(1 + tan²67½) = cos 135. Find tan 67½ in surd form.",
                "A. √2 + 1",
                "B. √2 - 1",
                "C. √2 - ½",
                "D. √2/2 + 1",
                "A. √2 + 1"));

        questionList.add(new Question("What is the relevance of sin(A+B)?",
                "A. sin A cos B - cos A cos B",
                "B. sin A cos B + cos A sin B",
                "C.  cos A cos B − sin A sin B",
                "D. sin A cos B − cos A sin B",
                "B. sin A cos B + cos A sin B"));

        questionList.add(new Question("Find the surd form of sin 75°",
                "A. (√3 + 1)/2√2",
                "B. (√4 + 2)/√2",
                "C.  (1 - √3)/√2",
                "D. (√3 - 6)/2√2",
                "A. (√3 + 1)/2√2"));

        questionList.add(new Question("Find the surd form of sin 15°",
                "A. (√3 + 1)/2√2",
                "B. (√4 + 2)/√2",
                "C.  (1 - √3)/√2",
                "D. (√3 - 1)/2√2",
                "D. (√3 - 1)/2√2"));

        questionList.add(new Question("Find the surd form of sin 105°",
                "A. (√3 + 1)/2√2",
                "B. (√4 + 2)/√2",
                "C.  (1 - √3)/2√2",
                "D. (√3 - 1)/2√2",
                "C.  (1 - √3)/2√2"));

        questionList.add(new Question("Simplify tan (A+B+C)",
                "A. (tan 2A + tan 2B + tan 2C - tanAtanBtanC)/ 1 - tanAtanB - tanBtanC - tanAtanC",
                "B. (tan A + tan B + tan C - tanAtanBtanC)/ 1 - tanAtanB - tanBtanC - tanAtanC",
                "C. (tan A - tan B - tan C + tanAtanBtanC)/ 1 + tanAtanB + tanBtanC - tanAtanC",
                "D. (tan² A + tan² B + tan² C - tanAtanBtanC)/ 1 - tanAtanB - tanBtanC - tanAtanC",
                "B. (tan A + tan B + tan C - tanAtanBtanC)/ 1 - tanAtanB - tanBtanC - tanAtanC"));

        questionList.add(new Question("Simplify sin 2A",
                "A. sin²Acos²A",
                "B. 2sin²Acos²A",
                "C. 2sinAcosA",
                "D. 2sec²A",
                "C. 2sinAcosA"));

        questionList.add(new Question("Simplify cos 2A",
                "A. 1 - 2sin²A",
                "B. 2 - 2sin²A",
                "C. cosec²A",
                "D. 2sec²A",
                "A. 1 - 2sin²A"));

        questionList.add(new Question("Express tan 45° in surd form",
                "A. 2 −√3",
                "B. 2 +4√3",
                "C. 1 +√3",
                "D. 2 +√3",
                "A. 2 −√3"));

        questionList.add(new Question("Evaluate sin(3π/2)",
                "A. 1",
                "B. -½",
                "C. √5",
                "D. ½",
                "B. -½"));

        questionList.add(new Question("Evaluate cos(−9π/4)",
                "A. -√2/2",
                "B. -½",
                "C. √2/2",
                "D. ½",
                "C. √2/2"));

        questionList.add(new Question("In a triangle ABC, Line AC = 7.2cm, Line AB = 8.9cm and angle BCA = 55°." +
                "Find the distance between line BC",
                "A. 12.1cm",
                "B. 10.79cm",
                "C. 11.09cm",
                "D. 8.2cm",
                "B. 10.79cm"));

        questionList.add(new Question("Express cos(A + B)",
                "A. 12.1cm",
                "B. 10.79cm",
                "C. 11.09cm",
                "D. cosAcosB - sinAsinB",
                "D. cosAcosB - sinAsinB"));
    }
}