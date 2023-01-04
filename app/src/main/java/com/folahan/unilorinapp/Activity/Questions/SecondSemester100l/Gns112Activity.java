package com.folahan.unilorinapp.Activity.Questions.SecondSemester100l;

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

public class Gns112Activity extends AppCompatActivity {

    private List<Question> questionList;

    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=1, mTimeLeft = 600000, questionAnswered = 1, clicked = 0;
    Button btnNext, btnPrev, btnEnd;
    private AlertDialog.Builder dialog;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gns112);

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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Objects.requireNonNull(getSupportActionBar()).hide();

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

        if (SecondSemesterActivity.questionRequestCode == 1) {
            getQuestionPhase(questionList);

            setDataView(pos);
        } else if (SecondSemesterActivity.questionRequestCode == 2) {
            getQuestionPhase2(questionList);

            setDataView(pos);
        } else if (SecondSemesterActivity.questionRequestCode == 3) {
            getQuestionPhase3(questionList);

            setDataView(pos);
        }

        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.button_previous);

        btnNext.setOnClickListener(view -> {
            if (questionAnswered == 30) {
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
                .setMessage("Are you sure you want to submit? \n You answered "+clicked+" out of 30 questions")
                .setPositiveButton("Yes", (dialog, which) -> {
                    showButton();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setIcon(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.ic_cancel)).show();
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

    private void setDataView(int position) {
        questionText.setText(questionList.get(position).getQuestion());

        rbOption1.setText(questionList.get(position).getOption1());
        rbOption2.setText(questionList.get(position).getOption2());
        rbOption3.setText(questionList.get(position).getOption3());
        rbOption4.setText(questionList.get(position).getOption4());
        answerText.setText(questionList.get(position).getAnswer());

        questionNo.setText("Question "+questionAnswered+" of 30");

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("1. The writer seems to suggest that ……….",
                "A. secretary were best trained by retired chief typist",
                "B. there was nothing wrong having a male as secretaries",
                "C. the ordinary national diploma was sufficient for secretarie",
                "D. only graduate who studied abroad were secretaries",
                "B. there was nothing wrong having a male as secretaries"));

        questionList.add(new Question("2. The expression “utter disdain” as used in the " +
                "passage means…………",
                "A. professional conduct",
                "B. undue criticism",
                "C. deserved respect",
                "D. complete contempt",
                "D. complete contempt"));

        questionList.add(new Question("3. One of these is not a characteristics of an effective writing",
                "A. simplicity",
                "B. correctness",
                "C. economy",
                "D. accessibility",
                "D. accessibility"));

        questionList.add(new Question("4. The type of reading that is suitable for reading " +
                "newspaper may be …",
                "A. scanning",
                "B. skimming",
                "C. critical reading",
                "D. cramming",
                "B. skimming"));

        questionList.add(new Question("5. Listening to a sound without attaching much " +
                "importance to it, can be said to be …………… listening",
                "A. analytical",
                "B. marginal",
                "C. partial",
                "D. appreciative.",
                "B. marginal"));

        questionList.add(new Question("6. Vowels can generally be ………… and ……………",
                "A. monothongs and diphthongs",
                "B. monothongs and voiceless",
                "C. voice and diphthongs" ,
                "D. voice and voiceless", "A. monothongs and diphthongs"));

        questionList.add(new Question("7. The primary skill of oracy is ……………",
                "A. writing", "B. speaking",
                "C. listening", "D. reading",
                "B. speaking"));

        questionList.add(new Question("8. “The wall were covered with red spot the red army " +
                "was everywhere on the wall on the floor”. This " +
                "sentence can be written correctly as……………",
                "A. The walls were covered with red spot. the red " +
                        "army was everywhere, on the wall, on the floor",
                "B. The wall were covered with red spot. The red " +
                        "army was everywhere on the wall, on the floor",
                "C. The wall were covered with red spot. the red " +
                        "army was everywhereon the wall, on the floor",
                "D. The walls were covered with red spot. The red " +
                        "army was everywhere, on the wall, on the " +
                        "floor",
                "D. The walls were covered with red spot. The red " +
                        "army was everywhere, on the wall, on the " +
                        "floor"));

        questionList.add(new Question("9. In a formal letter, the recipient’s address is " +
                "situated……….",
                "A. top right corner after the writer’s address ",
                "B. top left corner after the writer’s address",
                "C. top left corner before the writer’s address",
                "D. top right corner before the writer’s address",
                "B. top left corner after the writer’s address"));

        questionList.add(new Question("10. ……………. Is a section of a piece of writing",
                "A. Spacing",
                "B. paragraphs",
                "C. hyphen",
                "D. semi column", "C. hyphen"));

        questionList.add(new Question("11. Writing can be described as ______",
                "(a) a receptive skill in communication",
                "(b) a receptive skill which can be developed through practice" ,
                "(c) the primary of the four language sills",
                "(d) a productive skill in language use",
                "(d) a productive skill in language use"));

        questionList.add(new Question( "12. A good writing should have a _____",
                "(a) synthesis of the thesis statement, occasion, style and the conclusion",
                "(b) synthesis of the audience, occasion, content, form and style.",
                "(c) synthesis of the dominant thesis, audience style and conclusion",
                "(d) synthesis of the introduction, topic sentences, audience and conclusion",
                "(b) synthesis of the audience, occasion, content, form and style."));

        questionList.add(new Question( "13. A topic sentence is the sentence which ____",
                "(a) reveals the writer’s attitude to the subject matter",
                "(b) contains the central idea of a paragraph",
                "(c) defines the scope and purpose of the essay",
                "(d) directs the movement and organisation of all ideas in the essay",
                "(b) contains the central idea of a paragraph"));

        questionList.add(new Question( "14. The process of writing involves _____",
                "(a) Pre-writing, writing and publishing",
                "(b) Pre-writing, topic selection and post-writing",
                "(c) Pre-writing, writing and editing",
                "(d) Pre-writing, topic selection and writing",
                "(c) Pre-writing, writing and editing"));

        questionList.add(new Question( "15. Which of the following topics would you consider most limited in scope for a" +
                " class essay?",
                "(a) Under development in Nigeria",
                "(b) Under development in Third world countries",
                "(c) Under development in Africa : Causes and solution",
                "(d) The causes of underdevelopment in Nigeria",
                "(d) The causes of underdevelopment in Nigeria"));

        questionList.add(new Question( "16. A good paragraph makes good use of the following combination",
                "(a) Unity, thesis statement, completeness, emphasis, coherence, transitional markers",
                "(b) Unity, topic sentence, completeness, emphasis, coherence, transitional markers",
                "(c) Unity, thesis statement, completeness, emphasis, coherence",
                "(d) Thesis statement, completeness, emphasis, coherence and detailed descriptions",
                "(b) Unity, topic sentence, completeness, emphasis, coherence, transitional markers"));

        questionList.add(new Question( "17. Shade *has write* the letter.",
                "(a) have wrote",
                "(b) have write",
                "(c) have written",
                "(d) has written.",
                "(d) has written."));

        questionList.add(new Question( "18. One of the boys *is coming* in every day.",
                "(a) come",
                "(b) comes",
                "(c) is come",
                "(d) are coming",
                "(b) comes"));

        questionList.add(new Question( "19. Romeo and Juliet *are* my best book",
                "(a) has being",
                "(b) have being",
                "(c) is been",
                "(d) has been",
                "(d) has been"));

        questionList.add(new Question( " 20. The Vice-chancellor commended the committee for *their* prompt response",
                "(a) his",
                "(b) it",
                "(c) its",
                "(d) it’s",
                "(c) its"));

        questionList.add(new Question( "21. “The drawback is that it prevent people from " +
                "thinking for themselves and causes political hysteria " +
                "rather than logical thinking. “The phrase political " +
                "hysteria as used in the passage means ………",
                "A. hatred and rivalries",
                "B. anxiety and misunderstanding",
                "C. crisis and confusion",
                "D. tension and ill feeling",
                "D. tension and ill feeling"));

        questionList.add(new Question( "22. All These are attribute attached to a formal letters " +
                "except one ",
                "A. the last line carries the name of the writer",
                "B. the title of the letter are usually written in lower case and not underline",
                "C. the body of the letter has 3 structural component",
                "D. the body of the letter must contain paragraphs",
                "B. the title of the letter are usually written in lower case and not underline"));

        questionList.add(new Question( "23. One of these is not a language skills",
                "A. studying",
                "B. reading",
                "C. speaking",
                "D. writing",
                "A. studying"));

        questionList.add(new Question( "24. Learning may require all but none of the following activities",
                "A. thinking",
                "B.reflecting",
                "C. organizing",
                "D. none of the above",
                "D. none of the above"));

        questionList.add(new Question( "25. Which of these represent the required tools for " +
                "effective comprehension and practice",
                "A. reading skill",
                "B. learning skill",
                "C. none of the above",
                "D. sleeping skills",
                "C. none of the above"));

        questionList.add(new Question( "26. Which of these represent a receptive level of " +
                "language skills ",
                "A. writing",
                "B. speaking",
                "C. reading",
                "D. Writing skill",
                "B. speaking"));

        questionList.add(new Question( "27. The main objective of the library is all of these " +
                "except………",
                "A. store book",
                "B. prevent it from getting stolen",
                "C. play with book",
                "D. read books",
                "C. play with book"));

        questionList.add(new Question( "28. I can’t attend the dance with you when I have " +
                "……….to do",
                "A. many work ",
                "B. a work",
                "C. a great deal of work",
                "D. so much work",
                "C. a great deal of work"));

        questionList.add(new Question( "29. We have received _____ from him",
                "A. few information",
                "B. sufficient information",
                "C. an information",
                "D. some information",
                "B. sufficient information"));

        questionList.add(new Question( "30. The principal bought………….. for the chemistry " +
                "laboratory",
                "A. some equipment",
                "B. an equipment",
                "C. many equipment",
                "D. plenty equipment.",
                "A. some equipment"));
    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question( "1. The recent rainstorm did ……. To our farms",
                "A. much damage",
                "B. many damages",
                "C. plenty damage",
                "D. many more damages",
                "A. much damage"));

        questionList.add(new Question( "2. ………… yet about the principal?",
                "A. are there news",
                "B. are there some news",
                "C. is there any news",
                "D. is there some news",
                "C. is there any news"));

        questionList.add(new Question( "3. There is not ……… sense in what that politician " +
                "has just said",
                "A. many",
                "B. lot of",
                "C. much",
                "D. more",
                "C. much"));

        questionList.add(new Question( "4. Don’t listen to any of the …………",
                "A. fool",
                "B. fools",
                "C. foolses",
                "D. fooled",
                "A. fool"));

        questionList.add(new Question( "5. My neighbour’s children always make ……........ " +
                "when he is not at home",
                "A. noises",
                "B. Plenty noise",
                "C. A lot of noise",
                "D. A lot of noises",
                "C. A lot of noise"));

        questionList.add(new Question( "6. The expression ………… sincerely, is written at the " +
                "end of a letter",
                "A. your’s",
                "B. your",
                "C. yours",
                "D. yours’",
                "C. yours"));

        questionList.add(new Question( "7. My work is neater than ……………",
                "A. your’s",
                "B. your",
                "C. yours",
                "D. yours’",
                "C. yours"));

        questionList.add(new Question( "8. Neither Musa nor Idris ………….the examination",
                "A. fail",
                "B. fails",
                "C. failed",
                "D. failure",
                "B. fails"));

        questionList.add(new Question( "9. Nigeria, like most other African countries,………… " +
                "fertile land for",
                "A. have",
                "B. has",
                "C. had",
                "D. is",
                "B. has"));

        questionList.add(new Question( "10. The evidence of the two witnesses ………… noted " +
                "by the principle",
                "A. have",
                "B. was",
                "C. had",
                "D. is",
                "B. was"));

        questionList.add(new Question( "11. When the students got home, they discovering that their room was empty",
                "A. discover",
                "B. discovery",
                "C. discovered",
                "D. disscovered",
                "C. discovered"));

        questionList.add(new Question( "12. A request written by your association to the Head of your department seeking the " +
                "department’s financial support should take the form of _________",
                "A. an essay",
                "B. a business letter",
                "C. a field report",
                "D. a personal letter",
                "B. a business letter"));

        questionList.add(new Question( "13. The features of a business/public letter are arranged as follows _____________",
                "A. Reference Number, Address and date, the greeting, letter heading, body of " +
                        "The letter and complimentary close",
                "B. Address and Date, Name/Designation and address of receiver, Reference " +
                        "Number, salutation, letter heading body of the letter and complimentary close",
                "C. Address, Date, greeting, main body of letter and complimentary close",
                "D. Salutation/greeting, address, date, main body of letter and complimentary Close",
                "D. Salutation/greeting, address, date, main body of letter and complimentary Close"));

        questionList.add(new Question( "14. Which of the following is true about reports of meetings?",
                "A. Every single word spoken at the meeting is included in the report",
                "B. Only main ideas of discussions are reported",
                "C. Sections of minutes are not numbered",
                "D. Non-verbatim reports do not make use of the reported speech",
                "B. Only main ideas of discussions are reported"));

        questionList.add(new Question( "15. A fieldwork report has the following features . . . .",
                "A. Introduction, Requirement, Method, Readings, Calculations and Conclusion",
                "B. Headlines, Lead, body of the story conclusion and acknowledgement",
                "C. Introduction, Procedure, Observations, Discussion and Evaluation, Conclusion and acknowledgement",
                "D. Attendance, Opening, Matters Arising, News Business, Any Other Business" +
                        "and closing",
                "C. Introduction, Procedure, Observations, Discussion and Evaluation, Conclusion and acknowledgement"));

        questionList.add(new Question( "16. ___________________ is a type of report which usually has a source and a " +
                "headline",
                "A. Media report",
                "B. Field report ",
                "C. Laboratory report",
                "D. Report of meetings",
                "A. Media report"));

        questionList.add(new Question( "17. The natural order of the acquisition of language skills is ___________",
                "A. Listening, Reading, Writing and Speaking",
                "B. Reading, Listening, Writing and Speaking",
                "C. Reading, Writing, Listening and Speaking",
                "D. Listening, Speaking, Reading and Writing",
                "D. Listening, Speaking, Reading and Writing"));

        questionList.add(new Question( "18. ______________ is regarded as the most primary of the four language skills?",
                "A. Writing",
                "B. Reading",
                "C. Speaking",
                "D. Listening",
                "D. Listening"));

        questionList.add(new Question( "19. What type of listening takes place when we listen to messages like music with " +
                "a view to evaluating it?",
                "A. Attentive listening",
                "B. Appreciative listening",
                "C. Analytic listening",
                "D. Marginal listening",
                "C. Analytic listening"));

        questionList.add(new Question( "20. The listening process involves ______________________",
                "A. Recording sound, Focusing, accepting and responding",
                "B. Receiving, Focusing, Deciphering, Accepting and Storing",
                "C. Receiving, Deciphering, Recording, Accepting and Responding",
                "D. Receiving, Accepting, Storing, Recalling and Responding",
                "B. Receiving, Focusing, Deciphering, Accepting and Storing"));

        questionList.add(new Question( "21. ÛI will get ……………. the train at the next station",
                "A. off",
                "B. down",
                "C. over",
                "D. of",
                "A. off"));

        questionList.add(new Question( "22. I am disappointed ……….. the way he conducted " +
                "himself at the party",
                "A. in",
                "B. by",
                "C. at",
                "D. on",
                "C. at"));

        questionList.add(new Question( "23. She can’t sing ……………",
                "A. is she?",
                "B. isn’t she?",
                "C. can she?",
                "D. she can",
                "C. can she?"));

        questionList.add(new Question( "24. He has gone hasn’t he?",
                "A. no, he hasn’t",
                "B. yes, he had",
                "C. yes, he has",
                "D. yes, he hasn’t",
                "C. yes, he has"));

        questionList.add(new Question( "25. He ran ………… than I expect ",
                "A. fast",
                "B. faster",
                "C. fastest",
                "D. fasts",
                "B. faster"));

        questionList.add(new Question( "26. The student………… the story vividly",
                "A. narated",
                "B. naratted",
                "C. narrated",
                "D. narratted",
                "C. narrated"));

        questionList.add(new Question( "27. I bid him ………… before I travelled to lagos",
                "A. farewell",
                "B. farewell",
                "C. fearwell",
                "D. fearwel",
                "A. farewell"));

        questionList.add(new Question( "28. …...... was provided for him in the hotel",
                "A. accomodation",
                "B. accommodation",
                "C. accommdation",
                "D. acommodation",
                "B. accommodation"));

        questionList.add(new Question( "29. A ………….. was appointed to discuss the matter",
                "A. committee",
                "B. comittee",
                "C. commitee",
                "D. comitee",
                "A. committee"));

        questionList.add(new Question( "30. /I/",
                "A. market",
                "B. Time",
                "C. Steel",
                "D. Raid",
                "A. market"));
    }

    private void getQuestionPhase3(List<Question> list) {

        questionList.add(new Question( "1. The listening process involves ______________",
                "A. Recording sound, Focusing, accepting and responding",
                "B. Receiving, Focusing, Deciphering, Accepting and Storing",
                "C. Receiving, Deciphering, Recording, Accepting and Responding",
                "D. Receiving, Accepting, Storing, Recalling and Responding",
                "B. Receiving, Focusing, Deciphering, Accepting and Storing"));

        questionList.add(new Question( "2. A speaker who pauses/maintains silence in order to drive home a " +
                "point makes use of",
                "A. implicit cue",
                "B. explicit cue",
                "C. verbal cue",
                "D. non-verbal cue",
                "D. non-verbal cue"));

        questionList.add(new Question( "3. _________ is a pre-listening tip",
                "A. Taking notes",
                "B. Anticipating words or phrases a lecture will use",
                "C. Asking relevant questions ",
                "D. Preparing questions on a topic",
                "D. Preparing questions on a topic"));

        questionList.add(new Question( "4. Procedural tips of listening can be categorized as ________",
                "A. Aural, situational, electronic and tactile",
                "B. Orientation towards listening task, subject, speaker and self",
                "C. Pre-listening, during listening and post-listening",
                "D. Aural, situational, electronic and note taking",
                "C. Pre-listening, during listening and post-listening"));

        questionList.add(new Question( "5. ______________ logically follows in the acquisition of communication " +
                " skills.",
                "A. Writing",
                "B. Speaking",
                "C. Reading",
                "D. Hearing",
                "B. Speaking"));

        questionList.add(new Question( "6. A seminar presentation would require",
                "A. an impromptu delivery",
                "B. a memorized delivery",
                "C. discussion delivery",
                "D. reading delivery",
                "D. reading delivery"));

        questionList.add(new Question( "7. Which of the following strategies would you adopt when giving an impromptu" +
                " speech in front of all your lecturers?",
                "A. Standing confidently, speaking clearly and lowering your gaze",
                "B. Standing confidently, lowering your gaze and making your speech long",
                "C. Standing confidently, maintaining eye contact and speaking clearly",
                "D. Lowering your gaze, speaking clearly and making your speech long",
                "C. Standing confidently, maintaining eye contact and speaking clearly"));

        questionList.add(new Question( "8. The following are effective delivery strategies except",
                "A. comportment",
                "B. statement of purpose",
                "C. eye contact",
                "D. jerky sentence",
                "D. jerky sentence"));

        questionList.add(new Question( "9. __________ is not an organ of speech",
                "A. Pharyngeal cavity",
                "B. Maxilla cavity",
                "C. Nasal cavity",
                "D. Oral cavity",
                "B. Maxilla cavity"));

        questionList.add(new Question( "10. The following are principles that can be adopted in persuasive speaking " +
                " except",
                "A. Creating a vivid picture",
                "B. Keeping to one theme",
                "C. Use of abstract phrases",
                "D. Generating true emotion",
                "C. Use of abstract phrases"));

        questionList.add(new Question( "11. /p/",
                "A. appear",
                "B. Photo",
                "C. receipt",
                "D. coup",
                "A. appear"));

        questionList.add(new Question( "12. /n/",
                "A. King",
                "B. harvest",
                "C. Having",
                "D. Heaven",
                "D. Heaven"));

        questionList.add(new Question( "13. /j/",
                "A. jest",
                "B. unit",
                "C. city",
                "D. grudge",
                "B. unit"));

        questionList.add(new Question( "14. ……… is the key to a library’s collection",
                "A. catalogue",
                "B. artifact",
                "C. cards",
                "D. Books",
                "A. catalogue"));

        questionList.add(new Question( "15. Which of these materials is not found in the library",
                "A. fiction",
                "B. newspaper",
                "C. reference books",
                "D. none of the above",
                "D. none of the above"));

        questionList.add(new Question( "16. Library users are not expected to do all of the " +
                "following in the library except………",
                "A. make noise",
                "B. discuss issue",
                "C. keep silence",
                "D. don’t talk at all",
                "D. don’t talk at all"));

        questionList.add(new Question( "17. Writing was initially developed in all of these " +
                "countries except……",
                "A. Greece",
                "B. Babylon",
                "C. Ethiopia",
                "D. China",
                "C. Ethiopia"));

        questionList.add(new Question( "18. The 4 language skills are ………",
                "A. Listening, speaking, reading, writing",
                "B. Listening , writing , reading, scanning",
                "C. Speaking, learning, listening, skimmimg",
                "D. Talking, speaking, listening, hearing",
                "A. Listening, speaking, reading, writing"));

        questionList.add(new Question( "19. …………. Is a phonological unit",
                "A. intonation",
                "B. sound",
                "C. stress",
                "D. homophones",
                "C. stress"));

        questionList.add(new Question( "20. All of these except one isn’t goal of reading",
                "A. listening",
                "B. speaking",
                "C. communicating",
                "D. comprehension",
                "D. comprehension"));

        questionList.add(new Question( "21. Amina did her wedding anniversary last week should be",
                "A. Amina do her wedding anniversary last week",
                "B. Amina is doing her wedding anniversary last week",
                "C. Amina performed her wedding anniversary last week",
                "D. Amina celebrated her wedding anniversary last week",
                "D. Amina celebrated her wedding anniversary last week"));

        questionList.add(new Question( "22. I washed a nice show at the tiata should be",
                "A. I watch a nice show at the thearte",
                "B. I wash a nice show at the theatre",
                "C. I watched a nice show at the theatre",
                "D. I washed a nice show at the theatre",
                "C. I watched a nice show at the theatre"));

        questionList.add(new Question( "23. Share this ice cream ________ three of you",
                "A. among",
                "B. between",
                "C. amongst",
                "D. in-between",
                "A. among"));

        questionList.add(new Question( "24. Ijeoma is always asking",
                "A. 'When will you come visit me'?",
                "B. ‘When will you visit me?’",
                "C. When are you to visit me?",
                "D. When are you visiting your?",
                "B. ‘When will you visit me?’"));

        questionList.add(new Question( "25. Every man, woman and child __________ requested to assemble in the " +
                "departure lounge",
                "A. is",
                "B. are",
                "C. were",
                "D. was",
                "A. is"));

        questionList.add(new Question( "26. My lecture asked us to try ____________ do better",
                "A. and",
                "B. to",
                "C. for",
                "D. by",
                "B. to"));

        questionList.add(new Question( "27. You and ___________ are going to represent the class",
                "A. me",
                "B. I",
                "C. mine",
                "D. us",
                "B. I"));

        questionList.add(new Question( "28. If I __________ you I would refuse to go",
                "A. was",
                "B. were",
                "C. am",
                "D. he",
                "B. were"));

        questionList.add(new Question( "29. Mr. Jatto say that he _________________ swimming",
                "A. disliked",
                "B. dislikes",
                "C. mislike",
                "D. disliking",
                "B. dislikes"));

        questionList.add(new Question( "30. Can you sit on this __________________?",
                "A. stood",
                "B. stool",
                "C. stand",
                "D. stake",
                "B. stool"));
    }
}


