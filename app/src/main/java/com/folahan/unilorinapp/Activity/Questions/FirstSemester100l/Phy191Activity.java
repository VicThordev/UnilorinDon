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

public class Phy191Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=1, mTimeLeft = 600000, questionAnswered = 1;
    Button btnNext, btnPrev, btnEnd;
    private boolean mTimerRunning;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phy191);

        questionList = new ArrayList<>();
        questionText = findViewById(R.id.questionText);
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
                (LinearLayout) findViewById(R.id.design_bottom_sheet));
        TextView scoreShow = bottomSheet.findViewById(R.id.score);
        Button goHome = bottomSheet.findViewById(R.id.btnScore);

        scoreShow.setText("Your score is \n"+pos2+" out of 5");

        goHome.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
            dialog.dismiss();
            finish();
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

        btnNext.setOnClickListener(view -> {
            questionAnswered++;
            pos = random.nextInt(questionList.size());
            setDataView(pos);
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

        questionNo.setText("Question "+questionAnswered+" of 5");
        if (questionAnswered == 5) {
            showButton();
        }

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("Amphoteric elements are the elements which", "" +
                " have similar physical properties with metals but similar chemical properties with non-metals",
                "possess acidic properties only", "possess neither acidic nor basic properties",
                "possess basic properties",
                "have similar physical properties with metals but similar chemical properties with non-metals"));

        questionList.add(new Question("Sodium and Potassium belong to the same group of the periodic table. This is because",
                "both are metals",
                "both are soft and lighter than water", "both form cations by loosing electrons",
                "both have identical electronic configuration",
                "both have identical electronic configuration"));

        questionList.add(new Question("Metals are electropositive because", "they form positively charged ions",
                "they are electron acceptors", "they form negatively charged ions",
                "they form positively charged ions by losing electrons",
                "they form positively charged ions by losing electrons"));

        questionList.add(new Question("Given that 2R (g) &rarr; 2P (g) + Q(g) ?H = -x kJ Which of the <br /> following conditions\n" +
                "has no effect on the equilibrium position?", "Reducing the pressure",
                "Increasing the temperature", "adding more of Q", "Decreasing the temperature",
                "Decreasing the temperature "));

        questionList.add(new Question(" Positive peace can be defined as:", "(a) Absence of war and destructions",
                "(b) Tranquility in the society", "(c) Absence of violence and presence of social justice", "(d) Tolerance",
                "(b) Tranquility in the society"));

        questionList.add(new Question("Incompatibility of interests and competitive aspirations of party(ies) are called___\n",
                "(a) Peace", "(b) Violent conflict",  "(c) Crisis" ,"(d) Conflict", "(d) Conflict"));

        questionList.add(new Question("_____is Not a stage in the levels/phases of conflict",
                "(a) Latent", "(b) Protest", "(c) Escalation", "(d) Manifest",
                "(b) Protest"));

        questionList.add(new Question("____is the stage in conflict when the parties involved no longer solve the\n" +
                "` problem by themselves",
                "(a) De-escalation stage", "(b) Latent stage", "(c) Escalation stage",
                "(d) Formation stage", "(c) Escalation stage"));

        questionList.add(new Question("The conflict within an individual is called",
                "(a) Inter-personal conflict", "(b) Global conflict", "(c) Intra-personal conflict",
                "(d) Conflict trap", "(c) Intra-personal conflict"));

        questionList.add(new Question("In causes of conflict, _____cannot be categorized as resource\n",
                "(a) Land", "(b) Sex", "(c) Money", "(d) Values", "(b) Sex"));

        questionList.add(new Question("Nuclear Race and Terrorism can be categorized as;",
                "(a) Inter-state conflict", "(b) War conflict" , "(c) Global conflict",
                "(d) Intra-Group conflict",
                "(c) Global conflict"));

        questionList.add(new Question("What is the distinction between conflict and violence?",
                "(a) They belong to the same family of destruction",
                "(b) Conflict is negative and violence is destructive",
                "(c) It takes violence for conflict to occur",
                "(d) Conflict is competitive while violence is destructive",
                "(d) Conflict is competitive while violence is destructive"));
    }
}