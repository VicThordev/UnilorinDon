package com.folahan.unilorinapp.Activity.Questions.FirstSemester100l;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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
import java.util.Objects;
import java.util.Random;

public class Gns111Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 300000, questionAnswered = 1, clicked = 0;
    Button btnNext, btnPrev, btnEnd;
    private boolean mTimerRunning;
    private AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gns111);

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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Objects.requireNonNull(getSupportActionBar()).hide();

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

        setListeners();

        mTimerRunning = true;

        if (FirstSemesterActivity.questionRequestCode == 1) {
            getQuestionPhase(questionList);

            setDataView(pos);
        }
        else if (FirstSemesterActivity.questionRequestCode == 2) {
            getQuestionPhase2(questionList);

            setDataView(pos);
        }
        else if (FirstSemesterActivity.questionRequestCode == 3) {
            getQuestionPhase3(questionList);

            setDataView(pos);
        }
        else if (FirstSemesterActivity.questionRequestCode == 4) {
            getQuestionPhase4(questionList);

            setDataView(pos);
        } else if (FirstSemesterActivity.questionRequestCode == 5) {
            getQuestionPhase5(questionList);

            setDataView(pos);
        }
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
            rbOption1.setClickable(false);
            rbOption2.setClickable(false);
            rbOption3.setClickable(false);
            rbOption4.setClickable(false);
            btnEnd.setText(R.string.go_home);
            btnEnd.setOnClickListener(view1 -> startActivity(new Intent(this, MainActivity.class)));
            answerText.setText(R.string.log_out);
            answerText.setText(questionList.get(pos).getAnswer());
            rbOption1.setVisibility(View.GONE);
            dialog.cancel();
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
        answerText.setText(String.format("Answer: %s", questionList.get(position).getAnswer()));

        questionNo.setText("Question "+questionAnswered+" of 50");

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

    private void dialogAlert() {
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit? \n You answered "+clicked+" out of 50 questions")
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


        questionList.add(new Question("1.The top class Nigerian universities are determined to _________ the " +
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

        questionList.add(new Question("7. It is the _________of study skills that enables the learner to be in charge of his/her own " +
                "learning.",
                "(A) appreciation. ", "(B) memorization",
                "(C) anticipation", "(D) application.",
                "(D) application."));

        questionList.add(new Question(" 8. Personal positive attitude helps. ",
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

        questionList.add(new Question("10. Which of the following is not a study skill strategy for the English as a second language " +
                "learner___________?",
                "(A) PQRST", "(B) time management",
                "(C) Personal motivation",
                "(D) Personal ", "(D) Personal "));

        questionList.add(new Question("11. Learners frequent interactions with colleague, teachers and schooars online help to " +
                "ensure__________________.",
                "(A) academic objectiveness.",
                "(B) academic currency." ,
                "(C) academic harvest.",
                "(D) academic competence.",
                "(B) academic currency."));

        questionList.add(new Question( "12. Which of the following is not a level of reading " +
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
                "(A) sentences",
                "(B) clauses. ",
                "(C) phrases. ",
                "(D) morphemes",
                "(C) phrases. "));

        questionList.add(new Question( "17. An important feature of language4 is its " +
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

        questionList.add(new Question( " 20. Factors affecting study skills include all " +
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

        questionList.add(new Question( "30. Inspite of the seemingly scarcity of marriageable men today many a girl __________ to get " +
                "married at the age of twenty-one.",
                "(A) Intend.",
                "(B) intended",
                "(C) plans",
                "(D) plan. ",
                "(C) plans"));

        questionList.add(new Question( "31. One of these is a deficiency in reading",
                "(A) vocalization",
                "(B) reads fast",
                "(C) anticipates",
                "(D) evaluate",
                "(A) vocalization"));

        questionList.add(new Question( "32. The reader who is skimming watches out for ____",
                "(A) keywords & headings",
                "(B) A & C",
                "(C) subheadings & graphic",
                "(D) only C",
                "(D) only C"));

        questionList.add(new Question( "33. Fast reading to locate dates, names, places and figures is called_____",
                "(A) leisure reading",
                "(B) regressing",
                "(C) digest",
                "(D) scanning",
                "(D) scanning"));

        questionList.add(new Question( "34. Study skills are also known as _______",
                "(A) detailed reading",
                "(B) reading strategies",
                "(C) processing",
                "(D) recollection",
                "(B) reading strategies"));

        questionList.add(new Question( "35. An example of memory aids is ____",
                "(A) meditation",
                "(B) M NEMONICS",
                "(C) silence ",
                "(D) research",
                "(B) M NEMONICS"));

        questionList.add(new Question( "36. In the SQ3R method of reading, the second R stands for ______",
                "(A) recall",
                "(B) reset",
                "(C) repeat",
                "(D) respond",
                "(A) recall"));

        questionList.add(new Question( "37. Factors affecting studying include all of the following except______",
                "(A) dedication",
                "(B) concentration",
                "(C) gender",
                "(D) place of study",
                "(C) gender"));

        questionList.add(new Question( "38. Strategies used for studying effectively include ____",
                "(A) extraction of key points",
                "(B) asking questions",
                "(C) only A",
                "(D) A & B",
                "(D) A & B"));

        questionList.add(new Question( "39. All except ________constitute major reading skills in intensive reading",
                "(A) organization",
                "(B) skimming",
                "(C) scanning",
                "(D) speed reading",
                "(A) organization"));

        questionList.add(new Question( "40. ________ is subject specific",
                "(A) meaning",
                "(B) extensive reading",
                "(C) Intensive reading",
                "(D) grammatical",
                "(C) Intensive reading"));

        questionList.add(new Question( "41. _________ is an information reception technique",
                "(A) scanning",
                "(B) note-making",
                "(C) smiling",
                "(D) driving",
                "(B) note-making"));

        questionList.add(new Question( "42. ________dictionaries are for specific professions",
                "(A) thesaurus",
                "(B) formal",
                "(C) specialized",
                "(D) general",
                "(C) specialized"));

        questionList.add(new Question( "43. The dictionary can be used for all of the following except_______",
                "(A) spelling",
                "(B) pronunciation",
                "(C) building",
                "(D) word entry",
                "(C) building"));

        questionList.add(new Question( "44. The ______ indicates the end of a complete statement.",
                "(A) full stop",
                "(B) dash",
                "(C) colon",
                "(D) comma",
                "(A) full stop"));

        questionList.add(new Question( "45. Another name for concord is _____",
                "(A) ellipsis",
                "(B) brackets",
                "(C) mood",
                "(D) agreement",
                "(D) agreement"));

        questionList.add(new Question( "46. A ______ can contain one or more clauses",
                "(A) phrase",
                "(B) sentence",
                "(C) word",
                "(D) morpheme",
                "(B) sentence"));

        questionList.add(new Question( "47. ________ are used as substitutes to nouns",
                "(A) pronouns",
                "(B) adjective",
                "(C) prepositions",
                "(D) verbs",
                "(A) pronouns"));

        questionList.add(new Question( "48. ________ verbs have receivers of actions",
                "(A) auxiliary",
                "(B) finite",
                "(C) transitive",
                "(D) main",
                "(C) transitive"));

        questionList.add(new Question( "49. Non-finite verbs are in forms",
                "(A) two",
                "(B) three",
                "(C) four",
                "(D) six",
                "(B) three"));

        questionList.add(new Question( "50. There are _____ major types of conjunctions",
                "(A) seven",
                "(B) three",
                "(C) five",
                "(D) two",
                "(B) three"));


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
                "(A) attends",
                "(B) were attending",
                "(C) attend ",
                "(D) attended.",
                "(A) attends"));

        questionList.add(new Question("31. _______ conjunction connects equal words, phrases or clauses",
                "(A) co-ordinating",
                "(B) exclamative",
                "(C) subordinating",
                "(D) separating",
                "(A) co-ordinating"));

        questionList.add(new Question("32. Words or sounds used for exclamation are called _____",
                "(A) adverbials",
                "(B) interjections",
                "(C) gerunds",
                "(D) nouns",
                "(B) interjections"));

        questionList.add(new Question("33. ________ are used to mark the beginning of a sentence",
                "(A) semi-colons",
                "(B) brackets",
                "(C) capital letters",
                "(D) commas",
                "(C) capital letters"));

        questionList.add(new Question("34. Adjectives are also called ______ words",
                "(A) Joining",
                "(B) doing",
                "(C) naming",
                "(D) describing",
                "(D) describing"));

        questionList.add(new Question("35. He pressed on until he found his uncle, ______he found after questioning " +
                "hundreds of people.",
                "(A) who",
                "(B) whom",
                "(C) that who ",
                "(D) for who",
                "(B) whom"));

        questionList.add(new Question("36. She is one of those persons who _____never wrong",
                "(A) Is",
                "(B) are being ",
                "(C) be",
                "(D) are",
                "(D) are"));

        questionList.add(new Question("37. That belt isn’t ____ is it?",
                "(A) of you",
                "(B) your’s",
                "(C) of yours",
                "(D) yours",
                "(D) yours"));

        questionList.add(new Question("38. Would you like to take – bananas with you to eat with your lunch?",
                "(A) a little",
                "(B) a few",
                "(C) a small",
                "(D) few",
                "(B) a few"));

        questionList.add(new Question("39. If I _______you. I would not give him so much money",
                "(A) have",
                "(B) am",
                "(C) was",
                "(D) were",
                "(D) were"));

        questionList.add(new Question("40. I prefer coffee _______ tea",
                "(A) than",
                "(B) better than",
                "(C) by",
                "(D) to",
                "(D) to"));

        questionList.add(new Question("41. We drank ______coffee ______tea.",
                "(A) not only …… but",
                "(B) not only ……. But also",
                "(C) not …….. but",
                "(D) not …….. but",
                "(B) not only ……. But also"));

        questionList.add(new Question("42. In “Jospark teaches English”, Jospark is a ________ noun",
                "(A) common",
                "(B) mass",
                "(C) proper",
                "(D) abstract",
                "(C) proper"));

        questionList.add(new Question("43. The sentence “What about it ?” is_______",
                "(A) imperative",
                "(B) interrogative",
                "(C) declarative",
                "(D) exclamatory",
                "(B) interrogative"));

        questionList.add(new Question("44. Three ______quarters of the hostel _____been painted green",
                "(A) will have",
                "(B) has",
                "(C) have",
                "(D) had",
                "(B) has"));

        questionList.add(new Question("45. The available definitions of language emphasis it ______function",
                "(A) communicative",
                "(B) interactive",
                "(C) transactional",
                "(D) visual",
                "(A) communicative"));

        questionList.add(new Question("46. From the words letter A to D, choose the word that has the same sound)s) as the " +
                "one represented by the letter(s) CAPITALISED \n kitES",
                "(A) last",
                "(B) breeze",
                "(C) sing",
                "(D) looks",
                "(D) looks"));

        questionList.add(new Question("47. From the words letter A to D, choose the word that has the same sound)s) as the " +
                "one represented by the letter(s) CAPITALISED \n impressED",
                "(A) moved",
                "(B) wicked",
                "(C) tall",
                "(D) kite",
                "(C) tall"));

        questionList.add(new Question("48. From the words letter A to D, choose the word that has the same sound)s) as the " +
                "one represented by the letter(s) CAPITALISED \n dIRt",
                "(A) warm",
                "(B) cart",
                "(C) wet",
                "(D) girl",
                "(D) girl"));

        questionList.add(new Question("49. From the words letter A to D, choose the word that has the same sound)s) as the " +
                "one represented by the letter(s) CAPITALISED \n rouGH",
                "(A) fight",
                "(B) bought",
                "(C) dough",
                "(D) love",
                "(A) fight"));

        questionList.add(new Question("50. From the words letter A to D, choose the word that has the same sound)s) as the " +
                "one represented by the letter(s) CAPITALISED \n plAIt",
                "(A) pace",
                "(B) cat",
                "(C) race",
                "(D) gate",
                "(B) cat"));
    }

    private void getQuestionPhase3(List<Question> list) {

        questionList.add(new Question("1. Were I to eat the food my brother would have been sad.",
                "(A) adverbial clause of condition",
                "(B) adverbial clause of manner",
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

        questionList.add(new Question("4. He walked to the podium as though " +
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

        questionList.add(new Question("15. James and Martha live a " +
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

        questionList.add(new Question("21. I don’t know his name but I remember ________ him in London",
                "(A) to meet",
                "(B) to have met",
                "(C) meeting",
                "(D) to be meeting",
                "(C) meeting"));

        questionList.add(new Question("22. __________ by the teacher, John did not enter the classroom",
                "(A) haven been punished ",
                "(B) being punished ",
                "(C) having been punished",
                "(D) haven being punished",
                "(C) having been punished"));

        questionList.add(new Question("23. The children listened attentively to the stories of the _________ man",
                "(A) grey-haired wise old",
                "(B) wise grey-haired old",
                "(C) old grey-haired wise",
                "(D) wise old grey-haired",
                "(D) wise old grey-haired"));

        questionList.add(new Question("24. Toyin was pleased with the ______skirt her mother gave to her",
                "(A) beautiful new pale yellow cotton",
                "(B) pale yellow beautiful new cotton",
                "(C) new beautiful yellow pale cotton",
                "(D) beautiful yellow cotton new pale",
                "(A) beautiful new pale yellow cotton"));

        questionList.add(new Question("25. The woman said she saw the victims ______ from the roof",
                "(A) to have fallen",
                "(B) to be falling",
                "(C) fall",
                "(D) fell",
                "(D) fell"));

        questionList.add(new Question("26. He fell in love with the ______ graduate",
                "(A) young intelligent British",
                "(B) intelligent young British",
                "(C) young British intelligent",
                "(D) British young intelligent ",
                "(B) intelligent young British"));

        questionList.add(new Question("27. Julian prides herself _______ her cooking",
                "(A) with",
                "(B) about",
                "(C) on",
                "(D) for",
                "(B) about"));

        questionList.add(new Question("28. Peter accused his friend _____ taking his pencil",
                "(A) of",
                "(B) in",
                "(C) for",
                "(D) about",
                "(A) of"));

        questionList.add(new Question("29. Mrs. Thomas was advised not to submit _________her husband’s rough treatment",
                "(A) In",
                "(B) at",
                "(C) to",
                "(D) for",
                "(C) to"));

        questionList.add(new Question("30. The painter appears to be worthy _________ his wages.",
                "(A) with",
                "(B) for",
                "(C) of",
                "(D) to",
                "(C) of"));

        questionList.add(new Question("31. Composition and comprehension skills are required in _____",
                "(A) outlining",
                "(B) essay",
                "(C) intonation",
                "(D) summary writing",
                "(A) outlining"));

        questionList.add(new Question("32. Which of the following factors does not affect study skills?",
                "(A) examination invigilator",
                "(B) study venue",
                "(C) study time",
                "(D) underlining",
                "(A) examination invigilator"));

        questionList.add(new Question("33. What is used to describe rise and fall in pitch of utterances?",
                "(A) syllable",
                "(B) stress",
                "(C) intonation",
                "(D) rhythm",
                "(C) intonation"));

        questionList.add(new Question("34. The use of the sound /e/ instead of /eI/ is an instance of ____",
                "(A) hypercorrection",
                "(B) re-interpretation of sound",
                "(C) under-differentiation",
                "(D) sound substitution",
                "(D) sound substitution"));

        questionList.add(new Question("35. The form of language used by an individual is known as ____",
                "(A) socialite",
                "(B) dialect",
                "(C) idiolect",
                "(D) register",
                "(C) idiolect"));

        questionList.add(new Question("36. The underlined parts of the sentence, “We were impatient to start the " +
                "engine is an instance of ______phrase",
                "(A) Infinitive",
                "(B) participle",
                "(C) gerund",
                "(D) prepositional",
                "(A) Infinitive"));

        questionList.add(new Question("37. The use of electronic media and information and communication " +
                "technologies in the school system is known as _____",
                "(A) CALL",
                "(B) ICT",
                "(C) EMIS",
                "(D) E-learning",
                "(D) E-learning"));

        questionList.add(new Question("38. Another concept of selective reading is _______",
                "(A) scanning",
                "(B) skimming",
                "(C) glancing",
                "(D) perusing",
                "(B) skimming"));

        questionList.add(new Question("39. ______ Skill is less emphasized in GNS 111",
                "(A) writing",
                "(B) speaking",
                "(C) listening",
                "(D) reading",
                "(A) writing"));

        questionList.add(new Question("40. Baba and ______participated in the tournament",
                "(A) him",
                "(B) his",
                "(C) he",
                "(D) he's",
                "(C) he"));

        questionList.add(new Question("41. He keeps his______ clean always",
                "(A) surroundings",
                "(B) surrounding",
                "(C) premise",
                "(D) environment",
                "(A) surroundings"));

        questionList.add(new Question("42. Some of the food ____ spoilt",
                "(A) is",
                "(B) are",
                "(C) were",
                "(D) have",
                "(A) is"));

        questionList.add(new Question("43. The fact that language can be broken up into units and smaller units " +
                "of language make up larger ones makes it _____",
                "(A) systematic",
                "(B) rule-governed",
                "(C) structured",
                "(D) dynamic",
                "(C) structured"));

        questionList.add(new Question("44. The punctuation mark is used to separate alternative word or phrases " +
                "Is known as ______.",
                "(A) ellipses",
                "(B) oblique",
                "(C) parenthesis",
                "(D) apostrophe",
                "(B) oblique"));

        questionList.add(new Question("45. The word “internalization” has how many morphemes”",
                "(A) four",
                "(B) five",
                "(C) six",
                "(D) seven",
                "(B) five"));

        questionList.add(new Question("46. It is high time the students ____ the class.",
                "(A) leave",
                "(B) left",
                "(C) have to leave",
                "(D) do leave",
                "(B) left"));

        questionList.add(new Question("47. Amaka Igwe film production, in conjunction with corporate pictures, " +
                "______ “The New Dawn”",
                "(A) present",
                "(B) presents",
                "(C) do present",
                "(D) is presenting",
                "(B) presents"));

        questionList.add(new Question("48. The suspect ______ when the DPO entered the office.",
                "(A) has been questioned",
                "(B) have been questions",
                "(C) was being questioned",
                "(D) Is being questions",
                "(C) was being questioned"));

        questionList.add(new Question("49. The students were advised to look _______ difficult words in the dictionary",
                "(A) at",
                "(B) in",
                "(C) of",
                "(D) with",
                "(A) at"));

        questionList.add(new Question("50. The student _______ before the principal arrived.",
                "(A) has to be punished",
                "(B) has been punished",
                "(C) have been punished",
                "(D) had been punished",
                "(D) had been punished"));

    }

    private void getQuestionPhase4(List<Question> list) {

        questionList.add(new Question("1. The baby was eating _____food.",
                "(A) Its’",
                "(B) it’s",
                "(C) it’s own",
                "(D) its",
                "(D) its"));

        questionList.add(new Question("2. The teacher collected his book and _________",
                "(A) everyone’s else",
                "(B) everyone else",
                "(C) everyone’s else’s",
                "(D) every persons",
                "(B) everyone else"));

        questionList.add(new Question("3. This is not your cup; it must be ________.",
                "(A) of her’s",
                "(B) hers",
                "(C) for her",
                "(D) her’s",
                "(B) hers"));

        questionList.add(new Question("4. You have now gotten _______ to complete the investigation.",
                "(A) a sufficient information",
                "(B) many informations",
                "(C) sufficient information",
                "(D) plenty of informations",
                "(C) sufficient information"));

        questionList.add(new Question("5. Have you heard ________ news about him since he left?",
                "(A) plenty",
                "(B) many",
                "(C) several",
                "(D) much",
                "(D) much"));

        questionList.add(new Question("6. They must have been blind not to have seen him, __________?",
                "(A) didn’t they?",
                "(B) musn’t they?",
                "(C) weren’t they?",
                "(D) wasn’t it?",
                "(C) weren’t they?"));

        questionList.add(new Question("7. John said that we could attend his birthday party, ________?",
                "(A) couldn’t we?",
                "(B) didn’t he?",
                "(C) did he?",
                "(D) won’t we?",
                "(B) didn’t he?"));

        questionList.add(new Question("8. Choose the word that is rightly stressed from the options",
                "(A) Idea",
                "(B) iDea ",
                "(C) iDEA ",
                "(D) idEa",
                "(C) iDEA "));

        questionList.add(new Question("9. Choose the word that is rightly stressed from the options",
                "(A) COfisicate",
                "(B) CONfisticate",
                "(C) confisTICATE",
                "(D) confISTicate",
                "(A) COfisicate"));

        questionList.add(new Question("10. Choose the word that is rightly stressed from the options",
                "(A) OVERcome",
                "(B) overcoME",
                "(C) Overcome",
                "(D) overCOME",
                "(D) overCOME"));

        questionList.add(new Question("11. Choose one word among those lettered A-D that contains the sound represented " +
                "by PHONETIC SYMBOL /U:/",
                "(A) cook",
                "(B) book",
                "(C) tomb",
                "(D) look",
                "(C) tomb"));

        questionList.add(new Question("12. Choose one word among those lettered A-D that contains the sound represented " +
                "by PHONETIC SYMBOL /n/",
                "(A) under",
                "(B) uncle",
                "(C) mountain",
                "(D) night",
                "(B) uncle"));

        questionList.add(new Question("13. Choose the one word among those lettered A-D that has the same " +
                "Consonant sound as the sound represented by the letter(s) CAPITALISED " +
                "In the capitalized word \n voLUme",
                "(A) virtue",
                "(B) vulgar",
                "(C) volunteer",
                "(D) value",
                "(D) value"));

        questionList.add(new Question("14. Choose the one word among those lettered A-D that has the same " +
                "Consonant sound as the sound represented by the letter(s) CAPITALISED " +
                "In the capitalized word \n pleaSure",
                "(A) music",
                "(B) dazzle",
                "(C) fusion",
                "(D) nurture",
                "(C) fusion"));

        questionList.add(new Question("15. Choose the one word among those lettered A-D that has the same " +
                "Consonant sound as the sound represented by the letter(s) CAPITALISED " +
                "In the capitalized word \n aCCent",
                "(A) mix",
                "(B) call",
                "(C) chart ",
                "(D) size",
                "(B) call"));

        questionList.add(new Question("16. The study skill that enhances the storage and retrieval of information in " +
                "The process of learning is ________",
                "(A) personal motivation",
                "(B) paragraphing",
                "(C) note-making",
                "(D) information integration",
                "(C) note-making"));

        questionList.add(new Question("17. A learner takes notes under the following situations except______",
                "(A) when listening to a lecture",
                "(B) when participating in a seminar",
                "(C) during consultation in the library",
                "(D) listening to a pre-recorded tape on a subject matter",
                "(C) during consultation in the library"));

        questionList.add(new Question("18. Note-making comes to play when a learner does the following " +
                "except_______",
                "(A) when reading a textbook",
                "(B) when consulting a lecture note",
                "(C) when going through a journal",
                "(D) when listening to a lecture",
                "(D) when listening to a lecture"));

        questionList.add(new Question("19. A dictionary shows how a word is pronounced through one of the following",
                "(A) stress",
                "(B) syllable",
                "(C) inflections",
                "(D) rhyme",
                "(A) stress"));

        questionList.add(new Question("20. The study skill which allows the learners to focus on core elements in a " +
                "material under study is called _______________________",
                "(A) note making",
                "(B) note taking",
                "(C) outlining",
                "(D) comprehension",
                "(C) outlining"));

        questionList.add(new Question("21. The first step in the use of a dictionary is to _____________",
                "(A) consult the dictionary straightaway",
                "(B) break the word into smaller unit",
                "(C) determine the contextual usage of the word",
                "(D) determine the word’s part of speech",
                "(C) determine the contextual usage of the word"));

        questionList.add(new Question("22. The following registers can be found in ‘administration’ except_____",
                "(A) directing",
                "(B) planning",
                "(C) organization",
                "(D) system",
                "(D) system"));

        questionList.add(new Question("23. Registers are influenced by the relationship that exists between a " +
                "speaker/writer and the listener/reader. The relationship is called_______",
                "(A) field of discourse",
                "(B) tenor of discourse",
                "(C) mode of discourse",
                "(D) nature of discourse",
                "(B) tenor of discourse"));

        questionList.add(new Question("24. “Good-for-Nothing” is an example of which of theses classes of nouns:",
                "(A) common noun",
                "(B) collective noun",
                "(C) compound noun",
                "(D) proper noun",
                "(C) compound noun"));

        questionList.add(new Question("25. The following verbs strengthen or add special shade of meaning to the real " +
                "action performed except_________",
                "(A) be",
                "(B) is",
                "(C) are",
                "(D) writes",
                "(D) writes"));

        questionList.add(new Question("26. The constituents of a clause include the following except________",
                "(A) word",
                "(B) phrase",
                "(C) sentence",
                "(D) clause",
                "(C) sentence"));

        questionList.add(new Question("27. A grammatical structure which contains a subject and predicate is " +
                "called________",
                "(A) a word",
                "(B) a phrase",
                "(C) a sentence",
                "(D) paragraph",
                "(C) a sentence"));

        questionList.add(new Question("28. A man cannot be successful, *except he is determined*. The starred" +
                "structure is a _____",
                "(A) dependent clause",
                "(B) independent clause",
                "(C) main clause",
                "(D) none of the above",
                "(A) dependent clause"));

        questionList.add(new Question("29. When there is a subordinator at the beginning of a clause then, the clause is " +
                "an example of a/an_________",
                "(A) independent clause",
                "(B) dependent clause",
                "(C) main clause",
                "(D) all of the above",
                "(B) dependent clause"));

        questionList.add(new Question("30. A noun clause functions in the following ways except_____",
                "(A) subject of the clause",
                "(B) object of the clause",
                "(C) qualifier",
                "(D) as gerund",
                "(C) qualifier"));

        questionList.add(new Question("31. A sentence is a complete grammatical utterance which contains ______",
                "(A) a linearly arranged words",
                "(B) a structurally related words",
                "(C) independent and dependent clause",
                "(D) all of the above",
                "(D) all of the above"));

        questionList.add(new Question("32. A statement is also referred to as _________",
                "(A) an imperative sentence",
                "(B) interrogative sentence",
                "(C) a declarative sentence",
                "(D) an exclamatory",
                "(C) a declarative sentence"));

        questionList.add(new Question("33. Which of these sentence types state a verifiable truth or situation?",
                "(A) an exclamatory sentence",
                "(B) an imperative sentence",
                "(C) a declarative sentence",
                "(D) an interrogative sentence",
                "(C) a declarative sentence"));

        questionList.add(new Question("34. Which of these is true of a simple sentence?",
                "(A) it contains only one independent clause",
                "(B) it begins with a capital letter and ends a full stop",
                "(C) it contains a subject and predicate",
                "(D) all of the above",
                "(D) all of the above"));

        questionList.add(new Question("35. The subject position in a sentence is occupied by which of the following " +
                "clauses of words",
                "(A) noun",
                "(B) adverb",
                "(C) adjective",
                "(D) verb",
                "(A) noun"));

        questionList.add(new Question("36. When two simple sentences are formed with the use of a coordinating " +
                "conjunction., they become a________",
                "(A) complex sentence",
                "(B) compound sentence",
                "(C) compound complex sentence",
                "(D) multiple sentence",
                "(B) compound sentence"));

        questionList.add(new Question("37. A sentence that has one independent and one or more dependent clause is " +
                "a _____________",
                "(A) complex sentence",
                "(B) compound sentence",
                "(C) compound complex sentence",
                "(D) multiple sentence",
                "(A) complex sentence"));

        questionList.add(new Question("38. “Teachers are always happy when their students perform well because they" +
                "are fulfilled.” The sentence above is a _______",
                "(A) simple",
                "(B) compound",
                "(C) complex",
                "(D) compound complex",
                "(C) complex"));

        questionList.add(new Question("39. “Kola is my enemy” is not a statement that comes from me, I can only quote " +
                "the statement using _______",
                "(A) question mark ",
                "(B) parenthesis",
                "(C) an hyphen",
                "(D) an inverted comma",
                "(D) an inverted comma"));

        questionList.add(new Question("40. A full stop performs one of the following functions __________",
                "(A) marks the end of a sentence/thought",
                "(B) shows surprise",
                "(C) makes additional information in a sentence",
                "(D) list items",
                "(A) marks the end of a sentence/thought"));

        questionList.add(new Question("41. Which of these is/are the function(s) of a comma?",
                "(A) listing items",
                "(B) separating an introductory or transitional words from other parts of a " +
                        "sentence",
                "(C) gives additional information",
                "(D) all of the above",
                "(D) all of the above"));

        questionList.add(new Question("42. The chairman as well as his executive members________ invited to the " +
                "meeting last week",
                "(A) is",
                "(B) was",
                "(C) were",
                "(D) was",
                "(B) was"));

        questionList.add(new Question("43. The president and commander-in-chief seeking a re-election in 2015",
                "(A) is",
                "(B) is been",
                "(C) are",
                "(D) were",
                "(A) is"));

        questionList.add(new Question("44. In “Shade speaks fluently”,",
                "(A) a singular subject agrees with a singular verb",
                "(B) a plural subject agrees with a plural verb",
                "(C) a singular subject agrees with a plural verb",
                "(D) none of the above",
                "(A) a singular subject agrees with a singular verb"));

        questionList.add(new Question("45. A sentence is a compound complex sentence because of _____",
                "(A) the number of clauses in it",
                "(B) the complex thought in it",
                "(C) because it is the longest of all the sentence types though not all " +
                        "situations",
                "(D) all of the above",
                "(D) all of the above"));

        questionList.add(new Question("46. Effective reading involves ________ cognitive processing",
                "(A) passive",
                "(B) unconscious",
                "(C) active",
                "(D) redundant",
                "(C) active"));

        questionList.add(new Question("47. Reading is a process in which our minds translate _______symbols into " +
                "ideas",
                "(A) printed",
                "(B) spoken",
                "(C) carved",
                "(D) coloured",
                "(A) printed"));

        questionList.add(new Question("48. Reading is a _______ skill",
                "(A) Expressive",
                "(B) receptive",
                "(C) primary",
                "(D) symbolic",
                "(B) receptive"));

        questionList.add(new Question("49. Reading for pleasure involves reading such materials like ___",
                "(A) novels",
                "(B) newspapers",
                "(C) magazines",
                "(D) all of the above",
                "(D) all of the above"));

        questionList.add(new Question("50. Qualities of an efficient reader include the following except ____",
                "(A) forecasts",
                "(B) flexible",
                "(C) enjoys reading",
                "(D) regresses",
                "(D) regresses"));


    }

    private void getQuestionPhase5(List<Question> list) {

        questionList.add(new Question("1. The two students did snot appear before the _______panel to look into " +
                "their demands",
                "(A) five man",
                "(B) five-man",
                "(C) five men",
                "(D) five-men",
                "(C) five men"));

        questionList.add(new Question("2. The president’s speech ______ at 9.p.m. yesterday",
                "(A) Is broadcast",
                "(B) has been broadcast",
                "(C) were broadcast",
                "(D) was broadcast",
                "(B) has been broadcast"));

        questionList.add(new Question("3. The young manager has caused his bank to ______ millions of naira",
                "(A) lost",
                "(B) loss",
                "(C) lose",
                "(D) loose",
                "(D) loose"));

        questionList.add(new Question("4. ________ has been circulated that the workers are planning to go on " +
                "industrial strike.",
                "(A) an information",
                "(B) Informations",
                "(C) the information",
                "(D) some informations",
                "(C) the information"));

        questionList.add(new Question("5. One of the robbers who snatched the minister’s car ________has been " +
                "Arrested by the police",
                "(A) has",
                "(B) have",
                "(C) had",
                "(D) was",
                "(A) has"));

        questionList.add(new Question("6. Neither the commissioners not the governor _______ present at the " +
                "political rally yesterday.",
                "(A) was",
                "(B) were",
                "(C) is",
                "(D) are",
                "(A) was"));

        questionList.add(new Question("7. The students ______ politics when the lecturer entered the lecture room",
                "(A) are discussing",
                "(B) were discussing about",
                "(C) are discussing on",
                "(D) were discussing",
                "(D) were discussing"));

        questionList.add(new Question("8. ______ were sent out of the class by the teacher",
                "(A) I and Ade",
                "(B) Ade and me",
                "(C) Ade and I",
                "(D) myself and Ade",
                "(C) Ade and I"));

        questionList.add(new Question("9. The pastor addressed his ______in a sonorous voice",
                "(A) audience",
                "(B) masses",
                "(C) spectators",
                "(D) congregation",
                "(D) congregation"));

        questionList.add(new Question("10. The executive committee meeting was _______ till the next morning",
                "(A) postponed",
                "(B) proscribed",
                "(C) delayed",
                "(D) adjourned",
                "(D) adjourned"));

        questionList.add(new Question("11. The man said he has never ______ in his life.",
                "(A) being so",
                "(B) so being",
                "(C) been so",
                "(D) so been",
                "(C) been so"));

        questionList.add(new Question("12. The Vice-chancellor with all the deputy Vice-chancellors ________ " +
                "the new site every week.",
                "(A) visited",
                "(B) visits",
                "(C) visit",
                "(D) visiting",
                "(B) visits"));

        questionList.add(new Question("13. Olu says he is better at Mathematics _________",
                "(A) as me",
                "(B) than I am",
                "(C) as I am",
                "(D) with me",
                "(B) than I am"));

        questionList.add(new Question("14. “Ladies and gentlemen, let us come together to know _____ " +
                "better”",
                "(A) each other",
                "(B) one another",
                "(C) us",
                "(D) ourselves",
                "(B) one another"));

        questionList.add(new Question("15. The new group is committed to _____ for international peace",
                "(A) work",
                "(B) worked",
                "(C) working",
                "(D) be working",
                "(C) working"));

        questionList.add(new Question("16. The governor _______ graduated from one of the best universities.",
                "(A) would have",
                "(B) must have",
                "(C) shall have",
                "(D) will have",
                "(B) must have"));

        questionList.add(new Question("17. The health workers’ strike was _____ as a result of the new minister’s " +
                "Intervention.",
                "(A) called back",
                "(B) called in",
                "(C) called off",
                "(D) called up",
                "(C) called off"));

        questionList.add(new Question("18. The principal as well as the teachers ______the students every Monday",
                "(A) address",
                "(B) addresses",
                "(C) addressed",
                "(D) are addressing",
                "(B) addresses"));

        questionList.add(new Question("19. The civil servants in the country are not likely ______promoted until next " +
                "year.",
                "(A) to be",
                "(B) to have been",
                "(C) to have",
                "(D) to being",
                "(A) to be"));

        questionList.add(new Question("20. When he visited the country last year, we asked him if he _____ " +
                "pounded yam as lunch.",
                "(A) want",
                "(B) wants",
                "(C) wanted",
                "(D) will has",
                "(C) wanted"));

        questionList.add(new Question("21. The first level or stage of reading is _______",
                "(A) studying",
                "(B) skimming",
                "(C) normal reading",
                "(D) scanning",
                "(D) scanning"));

        questionList.add(new Question("22. The smallest meaning-bearing unit of a language is ________",
                "(A) sentence",
                "(B) morpheme",
                "(C) phrase",
                "(D) word",
                "(B) morpheme"));

        questionList.add(new Question("23. “James will man the team”. As used in the sentence, the word “man” " +
                "Is a/an ________",
                "(A) adjective",
                "(B) adverb",
                "(C) noun",
                "(D) verb",
                "(D) verb"));

        questionList.add(new Question("24. The expression: “He had been stealing for long before he was caught this " +
                "afternoon” is in _______ tense.",
                "(A) past",
                "(B) simple past",
                "(C) past perfect",
                "(D) past perfect progressive",
                "(D) past perfect progressive"));

        questionList.add(new Question("25. The old man, as well as his children, _______ being interrogated by the " +
                "police",
                "(A) has",
                "(B) have",
                "(C) are",
                "(D) is",
                "(D) is"));

        questionList.add(new Question("26. Neither the boys nor the girl ______ responsible for breaking the glass " +
                "yesterday",
                "(A) Is",
                "(B) was",
                "(C) were",
                "(D) are",
                "(B) was"));

        questionList.add(new Question("27. “Modifier, headword and qualifier (mhq) is the structure of a/an ________ " +
                "phrase",
                "(A) noun",
                "(B) adjectival",
                "(C) adverbial",
                "(D) prepositional",
                "(A) noun"));

        questionList.add(new Question("28. Honesty, etc._________ what every politician should possess",
                "(A) are",
                "(B) is",
                "(C) shall be",
                "(D) none of the above",
                "(B) is"));

        questionList.add(new Question("29. A group of related words without a finite verb, functioning as a particular " +
                "word class is called ______",
                "(A) clause",
                "(B) sentence",
                "(C) pre-modifier",
                "(D) phrase",
                "(D) phrase"));

        questionList.add(new Question("30. A morpheme that is parasitic in nature is ______ morpheme",
                "(A) bound",
                "(B) free",
                "(C) derivational",
                "(D) inflectional",
                "(D) inflectional"));

        questionList.add(new Question("31. The language used in a particular field, discipline or profession is " +
                "called_______",
                "(A) idiom",
                "(B) register",
                "(C) phrasal",
                "(D) connotation",
                "(B) register"));

        questionList.add(new Question("32. The relation of lexical items along the horizontal axis is known " +
                "as ________ relations.",
                "(A) syntagmatic",
                "(B) syntactic",
                "(C) paradigmatic",
                "(D) lexical",
                "(C) paradigmatic"));

        questionList.add(new Question("33. The ________ is the largest unit on the grammatical rankscale",
                "(A) clause",
                "(B) phrase",
                "(C) morpheme",
                "(D) sentence",
                "(D) sentence"));

        questionList.add(new Question("34. A bound morpheme that changes the word class of the free morpheme to " +
                "which it is added is called a/an_________ morpheme",
                "(A) directional",
                "(B) derivational",
                "(C) inflectional",
                "(D) instructional",
                "(B) derivational"));

        questionList.add(new Question("35. Because he was unserious, the student failed the examination. The " +
                "expression: “because he was unserious” is a/an __________ clause.",
                "(A) adverbial",
                "(B) adjectival",
                "(C) noun",
                "(D) prepositional",
                "(A) adverbial"));

        questionList.add(new Question("36. One of the factors determining the choice of register is the ________ " +
                "of discourse",
                "(A) mode",
                "(B) modal",
                "(C) modality",
                "(D) modification",
                "(A) mode"));

        questionList.add(new Question("37. ‘Un__’ in the word “unconditional” is a/an ________.",
                "(A) suffix",
                "(B) prefix",
                "(C) infixation",
                "(D) affixation",
                "(B) prefix"));

        questionList.add(new Question("38. If I had got a ticket, I ______travelled since yesterday.",
                "(A) would have",
                "(B) will",
                "(C) should",
                "(D) shall",
                "(A) would have"));

        questionList.add(new Question("39. The man *whose son won an award* has arrived. The starred part of the " +
                "sentence is a/an _______clause.",
                "(A) adverbial",
                "(B) prepositional",
                "(C) adjectival",
                "(D) noun",
                "(C) adjectival"));

        questionList.add(new Question("40. I will marry you when I’m rich. The underlined part of the sentence is an " +
                "adverbial clause of ________.",
                "(A) reason",
                "(B) degree",
                "(C) time",
                "(D) place",
                "(C) time"));

        questionList.add(new Question("41. The prize was won by John and ______.",
                "(A) I",
                "(B) me",
                "(C) myself",
                "(D) we",
                "(B) me"));

        questionList.add(new Question("42. Each of the medical students ______expected here later",
                "(A) is",
                "(B) are",
                "(C) was",
                "(D) were",
                "(A) is"));

        questionList.add(new Question("43. Both Jonathan and ______must rule in 2015.",
                "(A) me",
                "(B) myself",
                "(C) I",
                "(D) us",
                "(C) I"));

        questionList.add(new Question("44. “Will have been dancing” is an example of _______ phrase",
                "(A) noun",
                "(B) verb",
                "(C) adjectival",
                "(D) adverbial",
                "(B) verb"));

        questionList.add(new Question("45. “Was dancing” can be a good example of _______ tense",
                "(A) past",
                "(B) past perfect",
                "(C) past perfect continuous",
                "(D) Past continuous",
                "(D) Past continuous"));

        questionList.add(new Question("46. “Had been dancing” can be a good example of _______tense",
                "(A) past",
                "(B) past perfect",
                "(C) past perfect continuous",
                "(D) Past continuous",
                "(C) past perfect continuous"));

        questionList.add(new Question("47. The _______ is the most mobile element in a sentence",
                "(A) subject",
                "(B) predicate",
                "(C) complement",
                "(D) adjunct",
                "(D) adjunct"));

        questionList.add(new Question("48. I, as well as members of my cabinet _____ here to rejoice with you now.",
                "(A) am",
                "(B) are",
                "(C) is",
                "(D) have",
                "(A) am"));

        questionList.add(new Question("49. The role or power relations between participants in a discussion are known " +
                "as the ______ of discourse.",
                "(A) mode",
                "(B) tenor",
                "(C) field",
                "(D) modality",
                "(B) tenor"));

        questionList.add(new Question("50. Four house _______ too much for this exam.",
                "(A) is",
                "(B) are",
                "(C) have been",
                "(D) were",
                "(A) is"));
    }



}