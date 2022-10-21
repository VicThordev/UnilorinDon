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

public class Gns111Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 300000, questionAnswered = 1;
    Button btnNext, btnPrev, btnEnd;
    private boolean mTimerRunning;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gns111);

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

        setListeners();

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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("1.The top class Nigerian universities are determined to _________ the\n" +
                "quality of education",
                "(A) keep up",
                "(B) keep up with",
                "(C) keep on",
                "(D) keep at",
                "(A) keep up"));

        questionList.add(new Question("Some people are failures or under-achievers because ____________.",
                "(A) they don’t like the library. ",
                "(B) they are unable to manage their time effectively.",
                "(C) they are poor readers",
                "(D) they are quiet " +
                        "speakers ",
                "(B) they are unable to manage their time effectively."));

        questionList.add(new Question("3. Anyone who wants to communicate effectively using English must___________.",
                "(A) l earn to " +
                        "verbalize. ",
                "(B) understand how others act and feel.",
                "(C) take pains to learn the rules",
                "(D) take pains to " +
                        "listen",
                "(C) take pains to learn the rules"));

        questionList.add(new Question("4. As a s a system of communication, a given language has its own sets of _____________",
                "(A) " +
                        "conversions. ",
                "(B) conventions.", "(C) corrections.",
                "(D) conversations.",
                "(B) conventions."));

        questionList.add(new Question("5. Learning involves the following activities except______________. ",
                "(A) sleeping.",
                "(B) reflecting.", "(C) thinking.",
                "(D) organizing facts.",
                "(A) sleeping."));

        questionList.add(new Question("6. ________ is a major study skill factor.",
                "(A) the mind.",
                "(B) the foreground.",
                "(C) the background." ,
                "(D) " +
                        "the context. ", "(A) the mind."));

        questionList.add(new Question("7. It is the _________of study skills that enables the learner to be in charge of his/her own \n" +
                "learning.",
                "(A) appreciation. ", "(B) memorization",
                "(C) anticipation", "(D) application.",
                "(D) application."));

        questionList.add(new Question(" 8. Personal " +
                "positive attitude helps. ",
                "(A) contents", "(B) study habits.",
                "(C) distractions.",
                "(D) interrogations",
                "(B) study habits."));

        questionList.add(new Question("9. " +
                "Study skills are habits that instill _________ in the learner once well cultivated.",
                "(A) Content learning.",
                "(B) knowledge",
                "(C) discipline",
                "(D) comprehension.",
                "(C) discipline"));

        questionList.add(new Question("10. Which of the following is not a study skill strategy for the English as a second language \n" +
                "learner___________?",
                "(A) PQRST", "(B) time management",
                "(C) Personal motivation",
                "(D) Personal ", "(D) Personal "));

        questionList.add(new Question("11. Learners frequent interactions with colleague, teachers and schooars online help to \n" +
                "ensure__________________.",
                "(A) academic objectiveness.",
                "(B) academic currency." ,
                "(C) academic harvest.",
                "(D) academic competence.",
                "(B) academic currency."));

        questionList.add(new Question( "12. Which of the following is not a level of reading \n" +
                "comprehension?________ ",
                "(A) critical.",
                "(B) inferential.",
                "(C) liberal.",
                "(D) literal.",
                "(C) liberal."));

        questionList.add(new Question( "13. Reading has " +
                "________ nature and is purpose bound, especially at the advanced level of learning. ",
                "(A) intrusive.",
                "(B) " +
                        "invalid. ",
                "(C) intricate.",
                "(D) intrinsic.",
                "(C) intricate."));

        questionList.add(new Question( "14. The minister was alarmed _______ the drop in quality of " +
                "education. ",
                "(A) with.",
                "(B) on. ",
                "(C) at.",
                "(D) for.",
                "(C) at."));

        questionList.add(new Question( "15. For understanding to take place, the basic foundation needed is __________.",
                "(A) Information.",
                "(B) " +
                        "communication.",
                "(C) concentration. ",
                "(D) reflection.",
                "(C) concentration. "));

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

    private void getQuestionPhase2(List<Question> list) {
        questionList.add(new Question("1. She did not explain what happened________ did she?",
                "(A) rather.",
                "(B) never ",
                "(C) either",
                "(D) " +
                        "neither",
                "(C) either"));

        questionList.add(new Question("2. He was _____ weak _____ he could not climb the stairs",
                "(A) very/that",
                "(B) so/that",
                "(C) " +
                        "too/that",
                "(D) so/when",
                "(B) so/that"));

        questionList.add(new Question("4. The " +
                "University of Ilorin _____a large collection of sporting______.",
                "(A) has /equipment",
                "(B) " +
                        "have/equipments",
                "(C) had/aids",
                "(D) has/costumes",
                "(D) has/costumes"));

        questionList.add(new Question("5. The lecturers with his wife ______here.",
                "(A) " +
                        "were",
                "(B) were to be",
                "(C) are",
                "(D) was ",
                "(A) " +
                        "were"));

        questionList.add(new Question("6. Neither Hallema, nor her sisters_____attending the party \n" +
                "tomorrow. ",
                "(A) were",
                "(B) is",
                "(C) are",
                "(D) was ",
                "(C) are"));

        questionList.add(new Question("7. The boys as well as their father _____travelling.",
                "(A) " +
                        "hated ",
                "(B) love",
                "(C) loves",
                "(D) will love.",
                "(B) love"));

        questionList.add(new Question("8. Under such circumstances, one might not really be able \n" +
                "to help______.",
                "(A) oneself",
                "(B) themselves",
                "(C) myself",
                "(D) one another",
                "(A) oneself"));

        questionList.add(new Question("9. The five girls " +
                "love_______. ",
                "(A) themselves",
                "(B) ourselves",
                "(C) one another",
                "(D) each other.",
                "(C) one another"));

        questionList.add(new Question("10. The winner is the " +
                "______of the three girls. ",
                "(A) most tall",
                "(B) tallest",
                "(C) taller",
                "(D) short",
                "(B) tallest"));

        questionList.add(new Question("The tall slim, dark-complexion good looking young man in that car recently " +
                "returned from India.",
                "(A) Compound complex ",
                "(B) simple",
                "(C) complex",
                "(D) compound",
                "(C) complex"));

        questionList.add(new Question("12. They do not " +
                "know how to play draught but they are good commentators of the board game. ",
                "(A) Compound",
                "(B) simple",
                "(C) complex",
                "(D) compound-complex",
                "(A) Compound"));

        questionList.add(new Question("13. To be frank, he has severe injuries during the accident " +
                "but his parents were assured of best medical care when the teams of doctors arrived.",
                "(A) Complex",
                "(B) simple",
                "(C) compound",
                "(D) compound-complex",
                "(D) compound-complex"));

        questionList.add(new Question("14. Whenever political aspirants read out their " +
                "manifestoes, the electorates are sometimes usually downcast.",
                "(A) Compound complex",
                "(B) simple",
                "(C) compound",
                "(D) Complex",
                "(D) Complex"));

        questionList.add(new Question("15. The horse neigh; the rider fell down.",
                "(A) Compound complex",
                "(B) simple",
                "(C) compound",
                "(D) Complex",
                "(A) Compound complex"));

        questionList.add(new Question("16. Effective study skills include all of the following except: ",
                "(A) time management",
                "(B) self-indulgence",
                "(C) self-discipline",
                "(D) concentration",
                "(B) self-indulgence"));

        questionList.add(new Question("17. In English, the active learning skills include \n" +
                "______ and______. ",
                "(A) speaking and writing",
                "(B) speaking and listening",
                "(C) speaking and reading",
                "(D) writing and listening",
                "(A) speaking and writing"));

        questionList.add(new Question("18. The passive language skills in English are _______ and_______.",
                "(A) speaking and writing",
                "(B) speaking and listening",
                "(C) reading and writing",
                "(D) reading and listening",
                "(D) reading and listening"));

        questionList.add(new Question("19. The punctuation mark “e.g.” is also known as _________.",
                "(A) exempli gratia",
                "(B) exemplify gratia",
                "(C) exception graft",
                "(D) exempli gratio",
                "(A) exempli gratia"));

        questionList.add(new Question("20. It is referred to as an important language because many people in South Africa________it.",
                "(A) Are " +
                        "speaking",
                "(B) spoke",
                "(C) speak",
                "(D) speaks",
                "(C) speak"));

        questionList.add(new Question("21. All except ______ is used to mark off \n" +
                "parenthetical statements.",
                "(A) inverted comma",
                "(B) comma",
                "(C) the dash",
                "(D) question mark",
                "(D) question mark"));

        questionList.add(new Question("22. The " +
                "job was done meticulously well.",
                "(A) noun phrase",
                "(B) adverb phrase",
                "(C) verbal group",
                "(D) adjectival " +
                        "phrase",
                "(B) adverb phrase"));

        questionList.add(new Question("23. Although she invited me to her birthday party, I will not be able to attend because my examination \n" +
                "is fast approaching.",
                "(A) main clause",
                "(B) subordinate clause",
                "(C) parallel clause",
                "(D) Reciprocal clause",
                "(B) subordinate clause"));

        questionList.add(new Question("24. The school authority and not the student______responsible for the crisis on campus. ",
                "(A) are",
                "(B) were",
                "(C) was",
                "(D) is.",
                "(C) was"));

        questionList.add(new Question("25. Comprehension is a _____activity of effective reading skills.",
                "(A) " +
                        "constructive",
                "(B) cognitive",
                "(C) conjunctive",
                "(D) connective",
                "(B) cognitive"));

        questionList.add(new Question("26. ‘…’ on the network news as the " +
                "National Award winners ‘….’ The punctuation mark used is an example of. \n",
                "(A) Three dots",
                "(B) ellipses",
                "(C) elision",
                "(D) subtraction",
                "(B) ellipses"));

        questionList.add(new Question("27. ______is not one of the learning skills in English language.",
                "(A) reading",
                "(B) writing",
                "(C) communication",
                "(D) speaking.",
                "(C) communication"));

        questionList.add(new Question("28. The literacy language skills are reading and ________.",
                "(A) writing",
                "(B) listening",
                "(C) thinking",
                "(D) speaking.",
                "(A) writing"));

        questionList.add(new Question("29. Note making/taking and outlining is information _______techniques.",
                "(A) residual",
                "(B) " +
                        "refusal",
                "(C) recorder",
                "(D) reception",
                "(D) reception"));

        questionList.add(new Question("30. The Dean and Secretary ______the meeting regularly.",
                "(A) " +
                        "attends",
                "(B) were attending",
                "(C) attend ",
                "(D) attended.",
                "(A) " +
                        "attends"));
    }

    private void getQuestionPhase3(List<Question> list) {

        questionList.add(new Question("1. Were I to eat the food my brother would have been sad." +
                "",
                "(A) adverbial clause of condition",
                "(B) " +
                        "adverbial clause of manner",
                "(C) adjectival phrase",
                "(D) adverbial clause of reason.",
                "(A) adverbial clause of condition"));

        questionList.add(new Question("2. While you may not " +
                "approve my private affairs you have to agree that I am hard working",
                "(A) adverbial clause of manner",
                "(B) adverbial clause of reason",
                "(C) adverbial clause of concession",
                "(D) adverbial clause of degree",
                "(C) adverbial clause of concession"));

        questionList.add(new Question("3. As I " +
                "was late to the party, I was unable to find a seat.",
                "(A) adverbial clause of reason",
                "(B) adverbial clause of manner",
                "(C) adverbial clause of place",
                "(D) adverbial clause of time.",
                "(B) adverbial clause of manner"));

        questionList.add(new Question("4. He walked to the podium as though \n" +
                "he were a king. ",
                "(A) adverbial clause of condition",
                "(B) adverbial clause of manner",
                "(C) adverbial clause of " +
                        "result",
                "(D) adverbial clause of time.",
                "(A) adverbial clause of condition"));

        questionList.add(new Question("5. Joshua is so tall that we could easily locate him among the spectators. ",
                "(A) adverbial clause of degree",
                "(B) adverbial clause of concession",
                "(C) adverbial clause of reason",
                "(D) adjectival clause.",
                "(B) adverbial clause of concession"));

        questionList.add(new Question("6. What they told " +
                "me was wrong.",
                "(A) adverbial clause of noun.",
                "(B) noun clause",
                "(C) adjectival clause",
                "(D) noun phrase",
                "(B) noun clause"));

        questionList.add(new Question("7. We " +
                "are sorry that we came late.",
                "(A) adjectival clause",
                "(B) adverbial clause of reason",
                "(C) noun clause",
                "(D) noun " +
                        "phrase.",
                "(A) adjectival clause"));

        questionList.add(new Question("8. However hard she worked she never passed.",
                "(A) adverbial clause of contrast",
                "(B) adjectival " +
                        "clause",
                "(C) adverbial clause of reason",
                "(D) adverbial clause of degree.",
                "(A) adverbial clause of contrast"));

        questionList.add(new Question("9. I’m going I’m going where no one " +
                "can find me",
                "(A) adverbial clause of place",
                "(B) adverbial clause of reason",
                "(C) adverbial clause of purpose",
                "(D) adverbial clause of degree.",
                "(A) adverbial clause of place"));

        questionList.add(new Question("10. John saw her while she was going for the game.",
                "(A) adverbial clause " +
                        "of reason ",
                "(B) adverbial clause of time",
                "(C) adverbial clause of purpose",
                "(D) adverbial clause of manner",
                "(B) adverbial clause of time"));

        questionList.add(new Question("11. Kofi shed crocodile tears",
                "(A) profuse tears",
                "(B) a few tears",
                "(C) no tears",
                "(D) insincere tears",
                "(D) insincere tears"));

        questionList.add(new Question("12. I " +
                "had to cudgel my brain in order to solve that problem ",
                "(A) give my brain some rest",
                "(B) think hard",
                "(C) " +
                        "study my books more",
                "(D) abandon my own attempts.",
                "(A) give my brain some rest"));

        questionList.add(new Question("13. John wears his hearts on his sleeves",
                "(A) John " +
                        "is heartless",
                "(B) John is an ordinary person",
                "(C) John is kind",
                "(D) John is a thoughtful man.",
                "(A) John " +
                        "is heartless"));

        questionList.add(new Question("14. Adeola is " +
                "always jumping from the frying pan into the fire",
                "(A) taking the right decisions",
                "(B) frying pancakes on the " +
                        "fire",
                "(C) going from a bad to a worse situation",
                "(D) taking the right decisions",
                "(C) going from a bad to a worse situation"));

        questionList.add(new Question("15. James and Martha live a \n" +
                "cat-and-dog life",
                "(A) a life devoted to the care of cats and dogs",
                "(B) a happy life",
                "(C) a life full of quarrels",
                "(D) a life of reciprocating favours.",
                "(C) a life full of quarrels"));

        questionList.add(new Question("16. I watched him __________ the rice last night",
                "(A) eating",
                "(B) eats",
                "(C) eat",
                "(d) ate",
                "(C) eat"));

        questionList.add(new Question("17. " +
                "__________ you want to come to the party now that I’m going too?",
                "(A) shall",
                "(B) could",
                "(C) would",
                "(D) will",
                "(C) would"));

        questionList.add(new Question("18. If I ___________an elephant I would run!",
                "(A) saw",
                "(B) see",
                "(C) have seen",
                "(D) had seen",
                "(B) see"));

        questionList.add(new Question("19. I love ________ down your road to visit my friend",
                "(A) walking",
                "(B) walked",
                "(C) walks",
                "(D) be " +
                        "walking",
                "(A) walking"));

        questionList.add(new Question("20. He had gone away before we _______ that the money was missing.",
                "(A) discover",
                "(B) had " +
                        "discovered",
                "(C) discovered",
                "(D) be " +
                        "walking",
                "(D) have discovered"));
    }
}