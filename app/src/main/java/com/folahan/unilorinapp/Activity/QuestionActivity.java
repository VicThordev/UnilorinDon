package com.folahan.unilorinapp.Activity;

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

import com.folahan.unilorinapp.MainActivity;
import com.folahan.unilorinapp.Model.Question;
import com.folahan.unilorinapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=1, mTimeLeft = 300000;
    Button btnNext, btnPrev;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

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
                mTimerRunning = true;
            }
        }.start();

        mTimerRunning = true;

        getQuestionPhase(questionList);

        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.button_previous);

        btnNext.setOnClickListener(view -> {
            pos = random.nextInt(questionList.size());
            setDataView(pos);
            pos2++;
        });
        setDataView(pos);
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
        });
        dialog.setCancelable(false);
        dialog.setContentView(bottomSheet);
        dialog.show();
    }

    private void setDataView(int position) {
        questionNo.setText("Question "+pos2+" of 5");
        if (pos2 == 5) {
            showButton();
        } else {
            questionText.setText(questionList.get(position).getQuestion());

            rbOption1.setText(questionList.get(position).getOption1());
            rbOption2.setText(questionList.get(position).getOption2());
            rbOption3.setText(questionList.get(position).getOption3());
            rbOption4.setText(questionList.get(position).getOption4());
        }

    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question(" _________ is the absence of war", "(a) Violence",
                "(b) Positive peace", "(c) Terrorism", "(d) Negative peace"));

        questionList.add(new Question(" One of the following is Not a cause of conflict", "(a) Communication",
                "(b) Value", "(c) Crisis", "(d) Resources"));

        questionList.add(new Question(" ______ is the physical, psychological and structural force exerted for the purpose\n" +
                "Of injuring, damaging or abusing people or property.", "(a) Violence",
                "(b) Negotiation", "(c) Fire", "(d) War"));

        questionList.add(new Question(" The followings are categories of violence except _____", "(a) Protest violence",
                "(b) Structural violence", "(c) Physical violence", "(d) Psychological violence"));

        questionList.add(new Question(" Positive peace can be defined as:", "(a) Absence of war and destructions",
                "(b) Tranquility in the society", "(c) Absence of violence and presence of social justice", "(d) Tolerance"));

        questionList.add(new Question("Incompatibility of interests and competitive aspirations of party(ies) are called___\n",
                "(a) Peace", "(b) Violent conflict",  "(c) Crisis" ,"(d) Conflict"));

        questionList.add(new Question("_____is Not a stage in the levels/phases of conflict",
                "(a) Latent", "(b) Protest", "(c) Escalation", "(d) Manifest"));

        questionList.add(new Question("____is the stage in conflict when the parties involved no longer solve the\n" +
                "` problem by themselves",
                "(a) De-escalation stage", "(b) Latent stage", "(c) Escalation stage",
                "(d) Formation stage"));

        questionList.add(new Question("The conflict within an individual is called",
                "(a) Inter-personal conflict", "(b) Global conflict", "(c) Intra-personal conflict",
                "(d) Conflict trap"));

        questionList.add(new Question("In causes of conflict, _____cannot be categorized as resource\n",
                "(a) Land", "(b) Sex", "(c) Money", "(d) Values"));

        questionList.add(new Question("Nuclear Race and Terrorism can be categorized as;",
                "(a) Inter-state conflict", "(b) War conflict" , "(c) Global conflict",
                "(d) Intra-Group conflict"));

        questionList.add(new Question("What is the distinction between conflict and violence?",
                "(a) They belong to the same family of destruction",
                "(b) Conflict is negative and violence is destructive",
                "(c) It takes violence for conflict to occur",
                "(d) Conflict is competitive while violence is destructive"));
    }
}