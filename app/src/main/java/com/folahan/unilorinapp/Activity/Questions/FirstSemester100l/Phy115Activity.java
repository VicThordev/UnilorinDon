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

public class Phy115Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
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
        setContentView(R.layout.activity_phy115);

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

        questionList.add(new Question("17. Convert 0.25rev to degrees",
                "A. 13.25",
                "B. 14.33",
                "C. 12.47",
                "D. 11.23",
                "B. 14.33"));

        questionList.add(new Question("18. The period of a string is 2s. If the length of the string is doubled, what is the new period",
                "A. 1.41s",
                "B. 2.83s",
                "C. 2.00s",
                "D. 4.00s",
                "A. 1.41s"));

        questionList.add(new Question("19. Shear strain is________",
                "A. tanα",
                "B. sincosα",
                "C. cos&sinα",
                "D. cosα",
                "A. tanα"));

        questionList.add(new Question("20. Which of the following is correct about inelastic collision\n" +
                "I. KE before collision is equal to KE after collision\n" +
                "II. Objects move with a common velocity \n" +
                "III. momentum is conserved \n" +
                "IV. Objects move at different velocities\n",
                "A. I and II",
                "B. I and III",
                "C. IV and III",
                "D. II and III",
                "D. II and III"));

        questionList.add(new Question("21. Find the acceleration of a body with initial velocity, u=-3.2i+6.0j and it increased to a final velocity, v=2.9i-4.1j within 5s",
                "A. 0.68",
                "B. 0.94",
                "C. 2.36",
                "D. 2.56",
                "C. 2.36"));

        questionList.add(new Question("22. What is the potential energy at point r if the force is f= -k/r²",
                "A. k/r",
                "B. –k/r",
                "C. k/r²",
                "D. –k/r²",
                "B. –k/r"));

        questionList.add(new Question("23. A body moves in a circular motion with velocity of 0.88m/s and a radius of 0.02m. Calculate the frequency",
                "A. 44Hz",
                "B. 7Hz",
                "C. 5Hz",
                "D. 22Hz",
                "B. 7Hz"));

        questionList.add(new Question("24. A body changed velocity from 15m/s to 45m/s in 5s. Calculate the acceleration",
                "A. 15m/s²",
                "B. 45m/s²",
                "C. 6m/s²",
                "D. 5m/s²",
                "C. 6m/s²"));

        questionList.add(new Question("25. A person lifted a mass of20N through a height of 10m. Efficiency is 40%. Calculate work lost to friction.",
                "A. 200J",
                "B. 120J",
                "C. 80J",
                "D. 280J",
                "B. 120J"));

        questionList.add(new Question("26. A body of mass 50g is dropped from the top of ceiling to reach the ground within a second. Calculate the maximum potential energy of the body at the floor.",
                "A. 0J",
                "B. 256J",
                "C. 28J",
                "D. 15J",
                "A. 0J"));

        questionList.add(new Question("27. P1+(pv2)/2+pgh=P2+(pv2)/2+pgh\n" +
                "Identity the equation above",
                "A. Poisseuille's",
                "B. Bernoulli's",
                "C. Toricelli",
                "D. Continuity",
                "B. Bernoulli's"));

        questionList.add(new Question("28. Which of the following is not a natural force?",
                "A. Radioactive force",
                "B. Force between the nucleus and electron",
                "C. Centripetal and Centrifugal force",
                "D. I can't remember",
                "C. Centripetal and Centrifugal force"));

        questionList.add(new Question("29. A force of 25N drags an through a distance of 10m with a frictional force of 7N. Calculate resultant work done.",
                "A. 180N",
                "B. 250N",
                "C. 70N",
                "D. 36N",
                "A. 180N"));

        questionList.add(new Question("30. Calculate the coefficient of friction, force is 140N and mass is 60kg",
                "A. 0.24",
                "B. 0.14",
                "C. 0.30",
                "D. 0.36",
                "A. 0.24"));
    }

    private void getQuestionPhase1(List<Question> list) {

        questionList.add(new Question("1. A mass of 50g is made to swing with a period of 1.7 seconds, calculate the force constant.  B",
                "A.  0.88N/m",
                "B. 0.68N/m",
                "C. 0.56N/m",
                "D. 0.78N/m",
                "B. 0.68N/m"));

        questionList.add(new Question("2. What is the time taken for an object starting from rest to attain a velocity of 60m/s if the acceleration is 14m/s^2",
                "A. 3.29s",
                "B. 4.26s",
                "C. 4.29s",
                "D. 3.33s",
                "C. 4.29s"));

        questionList.add(new Question("3. A tension of a wire of length 0.6m is 1.5N. Calculate the tension of a wire of length 0.4m",
                "A. 3.38N",
                "B. 4.38N",
                "C. 2.36N",
                "D. 1.38N",
                "A. 3.38N"));

        questionList.add(new Question("4. Which of the following is not a derived unit?",
                "A. steradian",
                "B. radian",
                "C. amphere",
                "D. Tesla",
                "C. amphere"));

        questionList.add(new Question("5. The force attracting on a body in a circular motion attracting opposite the center of rotation",
                "A. centripetal",
                "B. centrifugal",
                "C. tension",
                "D. gravitation",
                "B. centrifugal"));

        questionList.add(new Question("6. A square of side L is floating on the surface tension T. The force required to pull out the frame from the liquid",
                "A. 8TL",
                "B. 4TL",
                "C. 6TL",
                "D. 2TL",
                "A. 8TL"));

        questionList.add(new Question("7. A mass of 25kg moving with 12m/s split into two masses m1 20kg and m2 5kg. If m1 moves with a velocity v1 10m/s, find the velocity at which m2 moves",
                "A. 20m/s",
                "B. 15m/s",
                "C. 12m/s",
                "D. 10m/s",
                "A. 20m/s"));

        questionList.add(new Question("8. A projectile is launched with speed Vo at an angle α. What is the expression for the time of flight?/s split into two masses m1 20kg and m2 5kg. If m1 moves with a velocity v1 10m/s, find the velocity at which m2 moves",
                "A. T= 2Vo sinα/g",
                "B. T= V²osinα/ 2g",
                "C. V²osinα/ g",
                "D. Vosin2α/g ",
                "A. T= 2Vo sinα/g"));

        questionList.add(new Question("9. A body of mass 50g oscillates with an amplitude of 12cm and angular velocity of ω=3.986rad/s calculate the value when V is max",
                "A. 0.48m/s",
                "B. 0.12m/s",
                "C. 0.38m/s",
                "C. 0.26m/s",
                "A. 0.48m/s"));

        questionList.add(new Question("10. A mass 0.02kg on top if a cliff 40m high is thrown to reach the horizontal. What is the K.E",
                "A. 7.74J",
                "B. 7.84J",
                "C. 7.66J",
                "D. 7.60J",
                "B. 7.84J"));

        questionList.add(new Question("11. Which of the following is not correct about collision?",
                "A. for inelastic collision K.Ei=K.Ef",
                "B. for elastic collision K.Ei=K.Ef",
                "C. for elastic collision, the momentum is conserved",
                "D. for inelastic collision, the momentum is conserved",
                "A. for inelastic collision K.Ei=K.Ef"));

        questionList.add(new Question("12. What happens in elastic collision\n" +
                "I. momentum is conserved\n" +
                "II. kinetic energy is conserved\n" +
                "III. momentum is increased\n" +
                "IV. Kinetic energy is increased\n",
                "A. I & II",
                "B. I & III",
                "C. I, II & IV",
                "D. All of the above",
                "A. I & II"));

        questionList.add(new Question("13. The unit of pressure is",
                "A. kgms²",
                "B. Nm",
                "C. Ns",
                "D. N/m²",
                "D. N/m²"));

        questionList.add(new Question("14. Which of the following types of motion are oscillatory?\n" +
                "(i) a diving board when use by a driver\n" +
                "(ii) the motion of the balance wheel of a wrist watch\n" +
                "(iii) the motion of the turn-table of a record player\n" +
                "(iv) the motion of the centre of a ten kobo piece as it rolls down an inclined plane\n" +
                "(v)   the motion of the needle of a D.C ammeter into which a low A.C current is passed\n",
                "A. I and II",
                "B. I, II and III",
                "C.  II, III and IV",
                "D. I, II and V",
                "D. I, II and V"));

        questionList.add(new Question("15. Relationship between Young modulus, stress and strain is given by",
                "A. Strain = Stress/Young modulus",
                "B. Strain = Young modulus × stress",
                "C. Strain = Young modulus/stress",
                "D. Stress = Strain/Young modulus",
                "A. Strain = Stress/Young modulus"));

        questionList.add(new Question("16. A heavy object is suspended from a string and lowered into water so that it is completely submerged. The object appears lighter because",
                "A. the density of water is less than that of the object",
                "B. the pressure is low just below the water surface",
                "C. the tension in the string neutralizes part of the weight",
                "D. it experiences an upthrust",
                "A. the density of water is less than that of the object"));

        questionList.add(new Question("17. An 80kg steel cylinder with a height of 2.0m has an area of 25cm². What is the pressure exerted by the cylinder on the floor.(take g=9.81m/s²)",
                "A. 3.1×10^5 Pa",
                "B. 3.2 ×10^-5 Pa",
                "C. 3.1×10^4 Pa",
                "D. 3.2×10^-4 Pa",
                "A. 3.1×10^5 Pa"));

        questionList.add(new Question("18. If the energy E of a body depends on it's mass M and velocity V. Where K=constant. Which of the following expressions is correct.",
                "A. E=KMV²",
                "B. E=KMV-²",
                "C. E=KMV^3",
                "D. E=KMV^-3",
                "A. E=KMV²"));

        questionList.add(new Question("19. A coiled elastic string has amplitude 12cm and a period of 1.7s and mass of 50g calculate the acceleration at 6cm?",
                "A. 1.42m/s²",
                "B. 1.53m/s²",
                "C. 1.26m/s²",
                "D. 1.36m/s²",
                "A. 1.42m/s²"));

        questionList.add(new Question("20. Calculate the angle of oscillation of a pendulum if length of arc is 10cm and radius is 80cm",
                "A. 0.13°",
                "B. 7.2°",
                "C. 8.12°",
                "D. 6.25°",
                "B. 7.2°"));

        questionList.add(new Question("21. A body of radius 30cm moved with velocity of 15m/s during 8s, what is the angular acceleration?",
                "A. 1.88rad/s²",
                "B. 6.25rad/s²",
                "C. 2.36rad/s²",
                "D. 7.36rad/s²",
                "B. 6.25rad/s²"));

        questionList.add(new Question("22. If a body of mass 12.5g with initial velocity 10m/s and final velocity of 22.2m/s. Calculate the workdone",
                "A. 2.46J",
                "B. 3.45J",
                "C. 1.45J",
                "D. 2.67J",
                "A. 2.46J"));

        questionList.add(new Question("23. A mass of 15.2kg has a velocity of 12m/s and collides with a mass of 12.2kg and velocity of 20m/s. if they move in the same direction and the collision is inelastic. Calculate the energy lost as heat or sound",
                "A. 217J",
                "B. 225J",
                "C. 210J",
                "B. 180J",
                "A. 217J"));

        questionList.add(new Question("24. A body undergoing rotational motion from rest has an acceleration of 3.18rad/s² in 8s. Calculate the revolution in rad?",
                "A. 101.76",
                "B. 203.52",
                "C. 50.88",
                "D. 25.44",
                "A. 101.76"));

        questionList.add(new Question("25. A body of mass 400g was extended by length of 35cm. Calculate the Spring Constant (Take g=9.8m/s²)",
                "A. 11.2N/m",
                "B. 10.7N/m",
                "C. 5.6N/m",
                "D. 8.8N/m",
                "A. 11.2N/m"));

        questionList.add(new Question("26. Which of the following is correct about moment of inertia and mass\n" +
                "i. Moment of inertia is for a rotational rigid body while mass.....\n" +
                "ii. Unit of inertia is Kgm² while mass is Kg\n" +
                "iii. Inertial is constant while mass varies\n",
                "A. i and ii",
                "B. i only",
                "C. i and iii",
                "D. All are correct",
                "A. i and ii"));

        questionList.add(new Question("27. For the period of pendulum bob given by T=mx ly gz. Find the values of x y and z",
                "A 0, ½, -½",
                "B ½, -½, ½ ",
                "C 0, 0, ½ ",
                "D ½, 0, ½ ",
                "A 0, ½, -½"));

        questionList.add(new Question("28. A body thrown with an initial velocity 135m/s at an angle of 45°. Calculate the maximum height attained",
                "A. 464.9m",
                "B 67.5m",
                "C 270m",
                "D 135.6m",
                "A. 464.9m"));

        questionList.add(new Question("29. A fan rotates at a angular frequency of 900rpm. If the distance from the center to the end of the arm of a blade is 20cm. Calculate its tangential velocity",
                "A. 127.60m/s",
                "B. 133.33m/s",
                "C. 171.89m/s",
                "D. 160.59m/s ",
                "C. 171.89m/s"));

        questionList.add(new Question("30. A cube has a length of 2cm and it's density is 6600g/cm³. What is the apparent mass when submerged in water. (Density of water=1000g/cm³)",
                "A. 13.2kg",
                "B. 8kg",
                "C. 6.6kg",
                "D. 4kg",
                "B. 8kg"));
    }
}