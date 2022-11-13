package com.folahan.unilorinapp.Activity.Questions.SecondSemester100l;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
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
import java.util.Random;

public class Phy152Activity extends AppCompatActivity {

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
        setContentView(R.layout.activity_phy152);

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

        setDataView(pos);
        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.button_previous);

        setListeners();

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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("1. Calculate the distance between point charges, 26.0μC and 47.0μC, if the magnitude of the " +
                "electrostatic force between them is 5.70N",
                "A. 150.6cm",
                "B. 138.9cm",
                "C. 148.7cm",
                "D. 165.3cm",
                "B. 138.9cm"));

        questionList.add(new Question("2. At what distance would the repulsive force between two electrons have a magnitude of one newton?",
                "A. 0.0152pm",
                "B. 0.0142pm",
                "C. 0.0132pm",
                "D. 0.0122pm",
                "A. 0.0152pm"));

        questionList.add(new Question("3. How many excess elections must be placed on each of two small spheres spaced 3cm apart, if the " +
                "force of repulsion between the spheres is to be 10^-19N?",
                "A. 125 electrons ",
                "B. 250 electrons",
                "C. 625 electrons",
                "D. 750 electrons",
                "C. 625 electrons"));

        questionList.add(new Question("4. What is the total positive charge in Coulombs, of all the protons in 1mol of Hydrogen atoms?",
                "A. 96.35kC",
                "B. 72.46kC",
                "C. 83.48kC",
                "D. 78.36kC",
                "A. 96.35kC"));

        questionList.add(new Question("5. An α-particle is a nucleus of doubly-ionised helium. It has a mass of 6.69 x 10^-27kg and a charge of -2e. " +
                "Compute the ratio of the force of electrostatic repulsion between two α-particles to the force of " +
                "gravitational attraction between them",
                "A. 6.2 x 10^35",
                "B. 4.2 x 10^35",
                "C. 3.1 x 10^35",
                "D. 2.1 x 10^35",
                "C. 3.1 x 10^35"));

        questionList.add(new Question("6. Compute the ratio of the electric force of attraction to the gravitational force of attraction between \n" +
                "the electron and the proton in a hydrogen atom assuming that their distance of separation is 0.53pm \n" +
                "(me = 9.1 x 10^-31kg, mp = 1.7 x 10^-27kg, e = 1.6 x 10^-19C)",
                "A. 2.2 x 10^39",
                "B. 1.1 x 10^39",
                "C. 2.1 x 10^39" ,
                "D. 1.2 x 10^39", "A. 2.2 x 10^39"));

        questionList.add(new Question("7. What is the magnitude of electrostatic force of attraction between an α-particle and an electron 10^-" +
                "13m apart?",
                "A. 2.3 x 10^-2N", "B. 2.6 x 10^-2N",
                "C. 4.3 x 10^-2N", "D. 4.6 x 10^-2N",
                "D. 4.6 x 10^-2N"));

        questionList.add(new Question("8. From the Coulomb's Law, what will be the slope of the graph of Log F vs Log r? ",
                "A. ½", "B. 2",
                "C. -log2",
                "D. -2",
                "A. ½"));

        questionList.add(new Question("9. Two equal charges of equal magnitude exert an attractive force of 4.0 x 10^-4N on each other. If the " +
                "magnitude of each charge is 2.0μC, how far apart are the charges?",
                "A. 9.5m",
                "B. 9.7m",
                "C. 10.5m",
                "D. 10.7m",
                "A. 9.5m"));

        questionList.add(new Question("10. What is the magnitude of a point charge whose electric field 50cm away has magnitude 2.0N/C?",
                "A. 4.56 x 10^-11C",
                "B. 4.66 x 10^-11C",
                "C. 5.56 x 10^-11C ",
                "D. 5.66 x 10^-11C", "C. 5.56 x 10^-11C "));

        questionList.add(new Question("11. What is the magnitude of an electric field in which the force on an electron is equal in magnitude to " +
                "the weight of an electron?",
                "A. 4.58 x 10^-11N/C",
                "B. 5.58 x 10^-11N/C" ,
                "C. 6.58 x 10^-11N/C",
                "D. 7.58 x 10-^11N/C",
                "B. 5.58 x 10^-11N/C"));

        questionList.add(new Question( "12. A small object carrying a charge of 5 x 10^-9" +
                "C experiences a downward force of 20 x 10^-9N when" +
                "placed at a certain point in an electric field. What's the electric field at the point?",
                "A. 100 N/C",
                "B. 40 N/C",
                "C. 10 N/C",
                "D. 4 N/C",
                "D. 4 N/C"));

        questionList.add(new Question( "13. The distance between two positive charges 6μC & 8μC is 50cm. Calculate the electric field intensity, " +
                "due to each charges, at a point p in between the two charges and 10cm from the 6μC charge" +
                " respectively",
                "A. 5.4 x 10^6N/C, 4.5 x 10^5N/C",
                "B. 6.4 x 10^6N/C, 5.5 x 10^5N/C",
                "C. 7.4 x 10^6N/C, 6.5 x 10^5N/C",
                "D. 8.4 x 10^6N/C, 7.5 x 10^5N/C",
                "A. 5.4 x 10^6N/C, 4.5 x 10^5N/C"));

        questionList.add(new Question( "14. Find the electric field at a point 0.2m from a charge of 20μC, what force will the electric field exert " +
                "on a charge of 10μC, placed at that point?",
                "A. 3.5 x 10^6N/C, 35N",
                "B. 4.5 x 10^6N/C, 45N",
                "C. 5.5 x 10^6N/C, 55N",
                "D. 6.5 x 10^6N/C, 65N",
                "B. 4.5 x 10^6N/C, 45N"));

        questionList.add(new Question( "15. The magnitude of electric field is 40kN/C. If an electron is placed in the same field, what force will be " +
                "exerted on it?",
                "A. 3.4 x 10^-15N",
                "B. 4.4 x 10^-15N",
                "C. 5.4 x 10^-15N",
                "D. 6.4 x 10^-15N",
                "D. 6.4 x 10^-15N"));

        questionList.add(new Question( "16. An oil drop of mass 2 x 10^-14kg carries a charge of 8 x 10-18C. The drop is stationary between two " +
                "parallel plates 20mm apart with a p.d of V between them. Calculate V",
                "A. 600V",
                "B. 580V",
                "C. 550V",
                "D. 500V",
                "D. 500V"));

        questionList.add(new Question( "17. The expression Φ = ∫ E•dA = Q/ε｡is an expression of?",
                "A. Coulomb's Law",
                "B. Kirchhoff's Law",
                "C. Gauss' Law",
                "D. Thevenin's Theorem",
                "C. Gauss' Law"));

        questionList.add(new Question( "18. The total flux emerging from a given charge is independent of the medium surrounding it. This is a " +
                "statement of",
                "A. Gauss' Law",
                "B. Kirchhoff's Law",
                "C. Ohm's Law",
                "D. Coulomb's Law",
                "A. Gauss' Law"));

        questionList.add(new Question( "19. From Gauss' Law, the total flux through a finite surface area is",
                "A. Q/4πε｡",
                "B. Q²/4πε｡ ",
                "C. Q/ε｡ ",
                "D. Q²/ε｡²",
                "C. Q/ε｡ "));

        questionList.add(new Question( "20. Gauss' Law can be applied to the following except",
                "A. location of excess charge on a conductor",
                "B. the coulomb's law",
                "C. field just outside any charged conductor",
                "D. none of the above",
                "A. location of excess charge on a conductor"));

        questionList.add(new Question( "21. A 60μC charge is at the center of a cube of side 10cm. What is the total flux through the cube?",
                "A. 4.78 x 10^6Nm²/C",
                "B. 5.78 x 10^6Nm²/C",
                "C. 6.78 x 10^6Nm²/C ",
                "D. 7.78 x 106Nm²/C",
                "C. 6.78 x 10^6Nm²/C "));

        questionList.add(new Question( "22. A spherical surface of radius 5cm is placed in a uniform electric field of 250 N/C. What is the " +
                "maximum electric flux that can pass through the surface?",
                "A. 7.855Nm²/C",
                "B. 6.855Nm²/C",
                "C. 5.685Nm²/C",
                "D. 4.685Nm²/C",
                "A. 7.855Nm²/C"));

        questionList.add(new Question( "23. The electric flux through an hollow sphere is found to be 5778.8 Nm²/C. What is the charge enclosed " +
                "by this surface?",
                "A. 0.048μC",
                "B. 0.051μC",
                "C. 0.054μC",
                "D. 0.057μC",
                "B. 0.051μC"));

        questionList.add(new Question( "24. The potential at a point due to a charge q placed at a distance from a test charge is given as",
                "A. F/q",
                "B. q/4πε｡R ",
                "C. (q/4πε｡)(a-b)",
                "D. kq²/r²",
                "B. q/4πε｡R "));

        questionList.add(new Question( "25. Two plates of a capacitor are 2.50m apart while the potential difference between the plates is 72V. " +
                "What is the electric field intensity on each plate?",
                "A. 180V/m",
                "B. 144V/m",
                "C. 57.6V/m",
                "D. 28.8V/m",
                "D. 28.8V/m"));

        questionList.add(new Question( "26. The electric potential difference between points A & B is found to be 70V, find the work done by an " +
                "external agent in carrying a charge q｡= 5.0μC from A to B at a constant speed",
                "A. 3.5 x 10^-4 J",
                "B. 4.5 x 10^-4 J",
                "C. 5.5 x 10^-4 J",
                "D. 6.5 x 10^-4 J",
                "A. 3.5 x 10^-4 J"));

        questionList.add(new Question( "27. Find the potential difference required to give a helium nucleus (q=2e) a K.E of 48000eV",
                "A. 96000V",
                "B. 48000V",
                "C. 24000V",
                "D. 12000V",
                "C. 24000V"));

        questionList.add(new Question( "28. In Coulomb's law, the force between two point charges is proportional to\n" +
                "(i) the product of the charges\n" +
                "(ii) the square of the distance between the two charges\n" +
                "(iii) the permittivity of the medium",
                "A. (i) and (iii) only",
                "B. (i) and (ii) only",
                "C. (ii) only",
                "D. (i) only",
                "D. (i) only"));

        questionList.add(new Question( "29. The fission of U-236 can produce the fragments of Ba-146 and Kr-90. These nuclei have a +56e and " +
                "+36e respectively. Determine the Coulomb's force acting on each just after their formation, when their " +
                "centers are separated by 1.6 x 10^-14m",
                "A. 0.59kN",
                "B. 0.78kN",
                "C. 1.46kN",
                "D. 1.81kN",
                "D. 1.81kN"));

        questionList.add(new Question( "30. A charge of 9μC is located at the origin, and a second charge of 1μC is located on the y-axis at " +
                "y=0.7m. Calculate the electric potential energy due to this pair of charges",
                "A. 0.12J",
                "B. 0.24J",
                "C. 0.48J",
                "D. 0.96J",
                "A. 0.12J"));
    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question("1. A 1μF and a 2μF capacitor are connected in series across a 1000V supply line. Find the charge on " +
                "each capacitor",
                "A. 6.67 x 10^-4C",
                "B. 5.67 x 10^-4C",
                "C. 4.67 x 10^-4C",
                "D. 3.67 x 10^-4C",
                "A. 6.67 x 10^-4C"));

        questionList.add(new Question("2. A parallel plate air capacitor is made of tin plates of area 0.04m² and 1cm apart. What is the " +
                "capacitance of the capacitor?",
                "A. 2.54 x 10^-11F",
                "B. 3.54 x 10^-11F",
                "C. 4.54 x 10^-11F",
                "D. 5.54 x 10^-11F",
                "B. 3.54 x 10^-11F"));

        questionList.add(new Question("3. The plates of a parallel plate capacitor are 2mm apart and 5m² in area. The plates are in vacuum. " +
                "Calculate the magnitude of the capacitance of the capacitor",
                "A. 0.44nF",
                "B. 0.33nF",
                "C. 0.22nF",
                "D. 0.11nF",
                "C. 0.22nF"));

        questionList.add(new Question("4. A capacitor of capacitance C is fully charged by a 200V battery. It is then discharged through a small " +
                "coil of resistance wire embedded in a thermally insulated block of specific heat capacity 2.5 x 10^2" +
                "J/kgK " +
                "and a mass of 0.1kg. If the temperature of the block rises by 0.4K, what is the value of C?",
                "A. 2.0mF",
                "B. 1.5mF",
                "C. 1.0mF",
                "D. 0.5mF",
                "D. 0.5mF"));

        questionList.add(new Question("5. A parallel plate air capacitor is made using two plates of area 0.2m² spaced 1cm apart. It is " +
                "connected to a 50V battery. What is the charge on each plate?",
                "A. 9.85nC",
                "B. 8.85nC",
                "C. 7.85nC",
                "D. 6.85nC",
                "B. 8.85nC"));

        questionList.add(new Question("6. The dimensions of plates of a parallel plate capacitor are 8cm by 8cm, and they are separated by a " +
                "distance of 2mm. Calculate the capacitance if air is between the plates. Calculate also the capacitance if " +
                "a glass of dielectric constant 5 fills the space between the plates.",
                "A. 28.3pF, 141.6pF",
                "B. 26.3pF, 141.6pF",
                "C. 25.3pF, 128.6pF",
                "D. 24.3pF, 128.6pF",
                "A. 28.3pF, 141.6pF"));

        questionList.add(new Question("7. A 6μF capacitor and a 3μF are connected in series across and 18V D.C source. Calculate the charge " +
                "on each capacitor",
                "A. 12μC",
                "B. 24μC",
                "C. 36μC",
                "D. 48μC",
                "C. 36μC"));

        questionList.add(new Question("8. Which of the following is/are true of the capacitance C of a parallel plate capacitor?\n" +
                "(i) C is proportional to the area of the plate\n" +
                "(ii) C is proportional to the distance between the plates\n" +
                "(iii) C is proportional to the permittivity of the medium between the plates\n",
                "A. i & ii",
                "B. i & iii",
                "C. ii & iii",
                "D. All of the above",
                "B. i & iii"));

        questionList.add(new Question("9. What area of the plates of a parallel-plate capacitor gives 1F, if the separation of the plates is 1mm " +
                "and the plates are in vacuum?",
                "A. 2.26 x 10^8m²",
                "B. 1.51 x 10^8m²",
                "C. 1.13 x 10^8m²",
                "D. 0.75 x 10^8m²",
                "C. 1.13 x 10^8m²"));

        questionList.add(new Question("10. Consider two isolated spherical conductors each having a net charge Q. The spheres have radii a and " +
                "b, where b>a. Which sphere has the higher potential? (Take the zero of potential to be at )",
                "A. the sphere of radius a",
                "B. the sphere of radius b",
                "C. they have the same potential",
                "D. none of the above",
                "A. the sphere of radius a"));

        questionList.add(new Question("11. The electric field intensity at a point close to a charged plane conductor situated in air is 3.5 x 10^6" +
                "V/m. Calculate the surface charge density",
                "A. 31.0μC/m",
                "B. 32.0μC/m",
                "C. 33.0μC/m",
                "D. 34.0μC/m",
                "A. 31.0μC/m"));

        questionList.add(new Question("12. Calculate the potential difference across a capacitor of capacitance 2μF, if the charge on it is 200μC",
                "A. 50V",
                "B. 100V",
                "C. 200V",
                "D. 400V",
                "B. 100V"));

        questionList.add(new Question("13. A capacitor with air between its plates has capacitance 5.0μF. What is its capacitance when wax of " +
                "dielectric constant 2.0 is placed between it plates?",
                "A. 10μF",
                "B. 20μF",
                "C. 30μF",
                "D. 40μF",
                "A. 10μF"));

        questionList.add(new Question("14. A 20kΩ resistor is connected in series with a resistance of 40kΩ. What resistance R must be " +
                "connected in parallel with the combination so that the equivalent resistance is equal to 20kΩ?",
                "A. 20kΩ",
                "B. 30kΩ",
                "C. 40kΩ",
                "D. 60kΩ",
                "B. 30kΩ"));

        questionList.add(new Question("15. What is electric power P, where I is current, V is potential and t is time?",
                "A. P=IVt",
                "B. P=IV/t",
                "C. P=IV",
                "D. I²V/t",
                "C. P=IV"));

        questionList.add(new Question("16. Resistance of a conductor depends on which of the following\n" +
                "(i) nature of the conductor\n" +
                "(ii) length\n" +
                "(iii) area\n" +
                "(iv) temperature",
                "A. None of the above",
                "B. (i), (ii) & (iv)",
                "C. All of the above",
                "D. (i), (ii) & (iii)",
                "C. All of the above"));

        questionList.add(new Question("17. A current of 2mA flows in a radio resistor R when a p.d of 4V is connected? What are the values of " +
                "the resistance and conductance?",
                "A. 2kΩ, 1.0mS",
                "B. 2kΩ, 0.5mS",
                "C. 8kΩ, 4.0mS",
                "D. 8kΩ, 2.0mS",
                "B. 2kΩ, 0.5mS"));

        questionList.add(new Question("18. The algebraic sum of the changes in potential differences around any closed path of the circuit must " +
                "be equal to zero. This statement is",
                "A. Ohm's law",
                "B. Kirchhoff's law",
                "C. Bio-Savart Law",
                "D. Gauss' Law",
                "B. Kirchhoff's law"));

        questionList.add(new Question("19. A p.d of 2V is connected to a uniform resistance wire of length 2.0m and cross-sectional area 8 x 10^-9" +
                "m². A current of 0.1A then flows in the wire. Find the resistivity of the material?",
                "A. 40nΩm",
                "B. 60nΩm",
                "C. 80nΩm",
                "D. 90nΩm",
                "C. 80nΩm"));

        questionList.add(new Question("20. A battery of emf 4V and internal resistance 2Ω is joined to a resistor of 8Ω. Calculate the terminal p.d",
                "A. 40nΩm",
                "B. 60nΩm",
                "C. 80nΩm",
                "D. 90nΩm",
                "C. 80nΩm"));

        questionList.add(new Question("21. A 20.0m length of wire 1.50mm in diameter has a resistance of 2.5Ω. What is the resistance of a " +
                "35.0m length of wire 3.00mm in diameter made of the same material?",
                "A. 0.55Ω",
                "B. 1.10Ω",
                "C. 2.20Ω",
                "D. 3.30Ω",
                "B. 1.10Ω"));

        questionList.add(new Question("22. An electric fan draws 2.0A of current from a 220V source. Find the power rating of the fan",
                "A. 0.22kW",
                "B. 0.33kW",
                "C. 0.44kW",
                "D. 0.55kW",
                "C. 0.44kW"));

        questionList.add(new Question("23. A battery of emf 1.50V has a terminal p.d of 1.25V when a resistor of 25Ω is connected across it. " +
                "Calculate the current flowing in the circuit",
                "A. 0.05A",
                "B. 0.10A",
                "C. 0.15A",
                "D. 0.20A",
                "A. 0.05A"));

        questionList.add(new Question("24. Three resistors (3.0Ω, 6.0Ω, 9.0Ω) are in parallel with a potential difference of 18V maintained across " +
                "them. Find the current in each resistor respectively",
                "A. 6.0A, 3.0A, 1.0A",
                "B. 6.0A, 3.0A, 2.0A",
                "C. 1.0A, 3.0A, 6.0A",
                "D. 2.0A, 3.0A, 6.0A",
                "B. 6.0A, 3.0A, 2.0A"));

        questionList.add(new Question("25. A battery of emf 8V and internal resistance r is connected to a 10Ω resistor. Given that the terminal " +
                "p.d across the resistor is 5V, calculate the value of internal resistance of the battery",
                "A. 2Ω",
                "B. 3Ω",
                "C. 4Ω",
                "D. 6Ω",
                "D. 6Ω"));

        questionList.add(new Question("26. A light bulb has a resistance of 60Ω when lit. How much current will flow through it when it is " +
                "connected across 120V, its normal operating voltage?",
                "A. 2A",
                "B. 3A",
                "C. 4A",
                "D. 5A",
                "A. 2A"));

        questionList.add(new Question("27. A dry cell has an emf of 1.50V. Its terminal p.d drops to zero when a current of 10A passes through it. " +
                "What is its internal resistance?",
                "A. 0.10Ω",
                "B. 0.15Ω",
                "C. 0.20Ω",
                "D. 0.25Ω",
                "B. 0.15Ω"));

        questionList.add(new Question("28. A metal rod is 2m long and 8mm in diameter. Calculate its resistivity if the resistance of the rod is 7 x " +
                "10^-8Ω",
                "A. 3.52pΩm",
                "B. 2.34pΩm",
                "C. 1.76pΩm",
                "D. 1.17pΩm",
                "C. 1.76pΩm"));

        questionList.add(new Question("29. A wire that has a resistance of 5.0Ω is made into a new wire three times as long as the original. What " +
                "is the new resistance?",
                "A. 15Ω",
                "B. 30Ω",
                "C. 45Ω",
                "D. 90Ω",
                "C. 45Ω"));

        questionList.add(new Question("30. How many electrons per second pass through a section of a conductor carrying a current of 0.70A?",
                "A. 8.75 x 10^18 electrons",
                "B. 5.83 x 10^18 electrons",
                "C. 4.38 x 10^18 electrons",
                "D. 2.91 x 10^18 electrons",
                "C. 4.38 x 10^18 electrons"));

    }

    private void getQuestionPhase3(List<Question> list) {


        questionList.add(new Question("1. Calculate the resistance that will be connected in series with a cell of 1.54V, 0.10Ω, given that the " +
                "terminal p.d is 1.40V",
                "A. 1.0Ω",
                "B. 1.5Ω",
                "C. 2.0Ω",
                "D. 2.5Ω",
                "A. 1.0Ω"));

        questionList.add(new Question("2. Three 5Ω resistors connected in parallel have a potential difference of 60V applied across the " +
                "combination. The current in each resistor is",
                "A. 4A",
                "B. 12A",
                "C. 24A",
                "D. 36A",
                "B. 12A"));

        questionList.add(new Question("3. Calculate the current drawn from a heater having 1600W at 240V",
                "A. 2.23A",
                "B. 3.34A",
                "C. 4.45A",
                "D. 6.67A",
                "D. 6.67A"));

        questionList.add(new Question("4. Calculate the resistance of a 60W/120V bulb",
                "A. 120Ω",
                "B. 240Ω",
                "C. 320Ω",
                "D. 480Ω",
                "B. 240Ω"));

        questionList.add(new Question("5. 90C charge is transferred in one minute when a current I flows through a conductor whose terminals " +
                "are connected across a potential difference of 100V. Calculate the value of I and the power expended in " +
                "heating the conductor if all the electrical energy is converted into heat",
                "A. 1.5A, 100W",
                "B. 1.5A, 150W",
                "C. 2.0A, 200W",
                "D. 2.0A, 250W",
                "B. 1.5A, 150W"));

        questionList.add(new Question("6. The magnitude of magnetic force F on a charge moving in a magnetic field depends on the following " +
                "options except",
                "A. the magnitude of the charge q",
                "B. the magnitude of the magnetic field strength B",
                "C. the magnitude of the velocity v, of the charge" ,
                "D. cosθ, where θ is the angle between field lines and velocity v",
                "D. cosθ, where θ is the angle between field lines and velocity v"));

        questionList.add(new Question("7. Which of the following is incorrect about the magnetic field B at a point?",
                "A. Its direction is that of the magnetic force F",
                "B. Its direction is that of the magnetic field",
                "C. It is also known as the magnetic induction strength",
                "D. It is also known as the magnetic flux density",
                "A. Its direction is that of the magnetic force F"));

        questionList.add(new Question("8. A wire carries a 30A current; if the wire is 5cm long, what is the magnitude of the maximum force on " +
                "the wire, if it is placed in a magnetic field of intensity B = 0.8T?",
                "A. 0.8N", "B. 1.2N",
                "C. 1.6N",
                "D. 2.4N",
                "B. 1.2N"));

        questionList.add(new Question("9. A long straight wire carries a current of 20A. Determine the magnetic field due to the current at a" +
                " distance of 4mm from the wire",
                "A. 0.001T",
                "B. 0.002T",
                "C. 0.003T",
                "D. 0.004T",
                "A. 0.001T"));

        questionList.add(new Question("10. Which of the following is not true of the magnetic force F on a charged particle moving in a magnetic " +
                "field B?",
                "A. The particle will experience a magnetic force F",
                "B. F is perpendicular to the plane containing velocity v and B",
                "C. F is always proportional to vsinθ; θ is the angle between the direction of B and v",
                "D. F is maximum when velocity and field are in the same direction", "B. F is perpendicular to the plane containing velocity v and B"));

        questionList.add(new Question("11. An electron with K.E of 3.0keV moves horizontally in a region of space in which there is downward-directed uniform electric field of magnitude 10kV/m. What is the magnitude of the smallest uniform " +
                "magnetic field that will cause the electron to continue to move horizontally? Ignore the gravitational " +
                "force",
                "A. 0.15mT",
                "B. 0.31mT" ,
                "C. 0.60mT",
                "D. 1.21mT",
                "B. 0.31mT"));

        questionList.add(new Question( "12. An electron with K.E of 1.50keV circles in a place perpendicular to a uniform magnetic field. The orbit \n" +
                "radius is 30.0cm. Find the speed of the electron and the magnitude of the magnetic field B",
                "A. 0.44mT",
                "B. 0.88mT",
                "C. 1.32mT",
                "D. 1.76mT",
                "A. 0.44mT"));

        questionList.add(new Question( "13. A circular coil of wire of radius 2.00cm carrying a current of 35mA is placed in a magnetic field of " +
                "45mT. What's its magnetic dipole moment if the coil has 250 turns?",
                "A. 0.006Am²",
                "B. 0.011Am²",
                "C. 0.022Am²",
                "D. 0.033Am²",
                "B. 0.011Am²"));

        questionList.add(new Question( "14. A long straight wire carries a current of 1.5A. An electron travels with a speed of 5 x 10^6 cm/s parallel " +
                "to the wire 10cm from it and in the same direction as the current. What force does the magnetic field of " +
                "the current exert on the moving electron?",
                "A. 1.2 x 10^-20 N",
                "B. 2.4 x 10^-20 N",
                "C. 4.8 x 10^-20 N",
                "D. 9.6 x 10^-20 N",
                "B. 2.4 x 10^-20 N"));

        questionList.add(new Question( "15. A coil carrying a current of 10A produces a magnetic field of 2π x 10^-5" +
                "T at the center of the coil. Calculate the radius of the coil",
                "A. 0.10m",
                "B. 0.20m",
                "C. 0.30m",
                "D. 0.40m",
                "A. 0.10m"));

        questionList.add(new Question( "16. The orbit of an electron in a betatron is a circle of radius R. Suppose the electron is revolving " +
                "tangentially in this orbit with velocity v. What flux density is required to maintain the electron in this " +
                "orbit if the magnitude of its velocity is constant?",
                "A. B=mωR",
                "B. B=mv/qR",
                "C. B=Fqv",
                "D. B=mv²/r",
                "B. B=mv/qR"));

        questionList.add(new Question( "17. A rectangular 10-turn loop of wire 5cm by 2cm carries a current of 2A and is hinged at one side. " +
                "What torque acts on the loop if it is mounted with its plane an angle of 60° to the direction of the " +
                "uniform magnetic field of 0.1T?",
                "A. 1 x 10^-3 Nm",
                "B. 2 x 10^-3 Nm",
                "C. 3 x 10^-3 Nm",
                "D. 4 x 10^-3 Nm",
                "A. 1 x 10^-3 Nm"));

        questionList.add(new Question( "18. A rectangular coil of 50-turn loop of wire is suspended in a field of 0.6Wb/m². The plane of the coil is " +
                "parallel to the direction of the field. The dimensions of the coil are 20cm perpendicular to the field line " +
                "and 10cm parallel to them. What is the current in the coil if there is a torque of 5.4Nm acting on it?",
                "A. 3A",
                "B. 6A",
                "C. 9A",
                "D. 12A",
                "C. 9A"));

        questionList.add(new Question( "19. A particle of mass 1 x 10^-26kg with a negative charge of magnitude 5.0 x 10^-4 C moves at a speed of 1.0 " +
                "x 10^3 m/s along the +x direction towards a perpendicularly inclined uniform magnetic field of 0.20T. " +
                "Calculate the force on the particle just as it enters the magnetic field\n",
                "A. 0.05N",
                "B. 0.10N",
                "C. 0.15N",
                "D. 0.20N",
                "B. 0.10N"));

        questionList.add(new Question( "20. A bar magnet is divided in two pieces. Which of the following statements is/are correct?",
                "A. The bar magnet is demagnetised",
                "B. The magnetic poles are separated",
                "C. The magnetic field of each separated piece becomes stronger",
                "D. Two magnets are created",
                "D. Two magnets are created"));

        questionList.add(new Question( "21. Which two quantities are plotted on a hysteresis loop or B-H curve?",
                "A. Reluctance and flux density",
                "B. Coercivity and flux density",
                "C. Magnetising force and reluctance",
                "D. Flux density and magnetising force",
                "D. Flux density and magnetising force"));

        questionList.add(new Question( "22. What do you call the characteristic of a magnetic material whereby a change in magnetisation lags " +
                "the application of a magnetising force?",
                "A. Induction",
                "B. Retentivity",
                "C. Hysteresis",
                "D. Coercivity",
                "C. Hysteresis"));

        questionList.add(new Question( "23. Consider the following quantities:\n (1) mass,\n (2) velocity," +
                "\n (3) charge,\n (4) magnetic field strength. \n" +
                "Upon which of these quantities is the force on a charged particle moving in a magnetic field dependent",
                "A. 1 and 4 only",
                "B. 2 and 3 only",
                "C. 1, 3 and 4 only",
                "D. 2, 3 and 4 only",
                "D. 2, 3 and 4 only"));

        questionList.add(new Question( "24. A charged particle is moving in a magnetic field. What is the direction of the force on the particle due " +
                "to the magnetic field?",
                "A. in the direction of the magnetic field",
                "B. in the direction opposite to which the particle is moving",
                "C. in the direction that is perpendicular to both the magnetic field and the velocity",
                "D. in the same plane as the magnetic field and the velocity, but not in either of those two directions",
                "C. in the direction that is perpendicular to both the magnetic field and the velocity"));

        questionList.add(new Question( "25. Which of the following combinations of units is equivalent to the Tesla?",
                "A. N/Am",
                "B. NA/m",
                "C. Js/Am",
                "D. J/Cs",
                "A. N/Am"));

        questionList.add(new Question( "26. Which one of the following conditions is not a requirement for a particle to experience a magnetic " +
                "force when placed in a magnetic field?",
                "A. The particle must be moving",
                "B. The particle must be charged",
                "C. The particle must not be under the influence of any other forces",
                "D. The velocity of the particle must have a component that is perpendicular to the direction of the magnetic field",
                "C. The particle must not be under the influence of any other forces"));

        questionList.add(new Question( "27. What is Right-Hand Rule used to determine?",
                "A. Given the directions of the magnetic field and the velocity of a charged particle, it is used to find the direction of the magnetic force on the particle",
                "B. Given the directions of the magnetic field and magnetic force on a charged particle, it is used to determine the magnitude and sign of charge on the particle",
                "C. Given the magnitude and sign of the charge on a particle and the direction of the magnetic force, it is used to determine the net force on the particle",
                "D. It is used to determine the direction of the “reaction force” when applying Newton's third law of motion to the particle",
                "A. Given the directions of the magnetic field and the velocity of a charged particle, it is used to find the direction of the magnetic force on the particle"));

        questionList.add(new Question( "28. Which one of the following is not used to determine the magnetic force on a current-carrying wire in " +
                "a magnetic field?",
                "A. length of the wire",
                "B. radius of the wire",
                "C. direction of the magnetic field with respect to the direction of the current",
                "D. the strength of the magnetic field",
                "B. radius of the wire"));

        questionList.add(new Question( "29. Consider the following quantities:\n (1) current,\n (2) resistance,\n (3) coil area,\n (4) wire cross-sectional " +
                "area, and (5) magnetic field. Upon which of the quantities is the magnetic dipole moment of a current " +
                "carrying coil dependent?",
                "A. 1 and 5 only",
                "B. 1 and 2 only",
                "C. 2 and 3 only",
                "D. 1 and 3 only",
                "D. 1 and 3 only"));

        questionList.add(new Question( "30.  A 50-loop circular coil has a radius of 3cm. It is oriented so that the field lines of a magnetic field are " +
                "normal to the area of the coil. If the magnetic field is varied so that B increased from 0.10T to 0.35T in 2 " +
                "milliseconds, what is the average induced emf in the coil?",
                "A. 17.7V",
                "B. 18.7V",
                "C. 19.7V",
                "D. 20.7V",
                "A. 17.7V"));
    }

    private void getQuestionPhase4(List<Question> list) {


        questionList.add(new Question("1. A copper bar 30cm long is perpendicular to a field of flux density 0.8Wb/m² and moves at right " +
                "angles to the field with a speed of 0.5m/s. Determine the emf induced in the bar",
                "A. 0.06V",
                "B. 0.12V",
                "C. 0.24V",
                "D. 0.48V",
                "B. 0.12V"));

        questionList.add(new Question("2. The magnetic field B is a function of magnetic flux Φ and cross-sectional area A as",
                "A. B=ΦA",
                "B. B=Φ/A²",
                "C. B=Φ/A",
                "D. B=Φ²/A",
                "C. B=Φ/A"));

        questionList.add(new Question("3. The induction B in the region between the pole faces of an electromagnet is 0.5Wb/m². Find the " +
                "induced emf in a straight conductor 10cm long, perpendicular to B, and moving perpendicular both to B " +
                "and its own length with a velocity of 1m/s",
                "A. 0.02V",
                "B. 0.05V",
                "C. 0.10V",
                "D. 0.20V",
                "B. 0.05V"));

        questionList.add(new Question("4. A rectangular coil of wire of dimension 30cm by 45cm rotates at a constant speed of 450rpm in a " +
                "magnetic field 0.15T. The axis of rotation is perpendicular to the field. Find the maximum emf produced " +
                "if the number of turns in the coil is 20",
                "A. 16.1V",
                "B. 17.1V",
                "C. 18.1V",
                "D. 19.1V",
                "D. 19.1V"));

        questionList.add(new Question("5. A circular turn of wire 4cm in radius rotates with an angular velocity of 1800rpm about a diameter " +
                "which is perpendicular to a uniform magnetic field of 0.5T. What is the instantaneous induced emf in the " +
                "turn when the plane of the turn makes angle 30° with the direction of the flux?",
                "A. 0.201V",
                "B. 0.274V",
                "C. 0.411V",
                "D. 0.548V",
                "C. 0.411V"));

        questionList.add(new Question("6. A coil of area 10cm² is in a uniform magnetic field of 0.1T. The field is reduced to zero in 1ms. " +
                "Determine the value of the induced emf",
                "A. 0.1V",
                "B. 0.2V",
                "C. 0.3V" ,
                "D. 0.4V", "A. 0.1V"));

        questionList.add(new Question("7. A Boeing 747 with a wingspan of 60m flies due south at a constant altitude in the northern " +
                "hemisphere at 260m/s. If the vertical component of the Earth's magnetic field in that area was 40μT. " +
                "Calculate the emf between the wing tips",
                "A. 0.312V", "B. 0.624V",
                "C. 0.936V", "D. 1.248V",
                "B. 0.624V"));

        questionList.add(new Question("8. A rectangular coil of wire having 10 turns with dimensions of 20cm x 30cm rotates at a constant " +
                "speed of 600rev/min in a magnetic field of 0.10T. The axis of rotation is perpendicular to the field. Find " +
                "the maximum emf produced",
                "A. 1.89V", "B. 2.51V",
                "C. 3.77V",
                "D. 5.03V",
                "C. 3.77V"));

        questionList.add(new Question("9. A coil of area 250mm² is placed in a uniform magnetic field of strength 0.45T. The coil has 110 turns " +
                "and a resistance of 6Ω. It is supplied with a current from a 9V cell. Calculate the maximum torque " +
                "exerted on the coil",
                "A. 0.0124Nm",
                "B. 0.0186Nm",
                "C. 0.0248Nm",
                "D. 0.0372Nm",
                "B. 0.0186Nm"));

        questionList.add(new Question("10. A flat coil of area 10cm² and with 200 turns rotates about an axis in the plane of the coil which is at " +
                "right angles to a uniform magnetic field of 0.05T. If the speed of rotation of the coil is 25rad/s, " +
                "determine the maximum emf induced in the coil",
                "A. 0.25V",
                "B. 0.50V",
                "C. 1.00V",
                "D. 1.50V", "A. 0.25V"));

        questionList.add(new Question("11. A coil of area 400mm² was placed in a uniform magnetic field B = 0.09T and the maximum torque in " +
                "the coil is 0.12Nm. If the coil had 150 turns, what is the magnitude of the current supplied to the coil?",
                "A. 11.11A",
                "B. 22.22A" ,
                "C. 33.33A",
                "D. 44.44A",
                "B. 22.22A"));

        questionList.add(new Question( "12. A narrow coil of 10 turns and area 4 x 10-2m² is placed in a uniform magnetic field of flux density B " +
                "of 10^-2 T so that the flux links the turns normally. Calculate the average induced emf in the coil if it is " +
                "removed completely from the field in 0.5s",
                "A. 2mV",
                "B. 4mV",
                "C. 6mV",
                "D. 8mV",
                "D. 8mV"));

        questionList.add(new Question( "13. A flat coil of area 10cm² and with 200 turns rotates about an axis in the plane of the coil which is at " +
                "right angles to a uniform magnetic field of 0.05T. If the speed of rotation of the coil is 25 rad/s, " +
                "determine the instantaneous value of the emf when the coil is at an angle of 45° to the field",
                "A. 0.089V",
                "B. 0.118V",
                "C. 0.177V",
                "D. 0.236V",
                "C. 0.177V"));

        questionList.add(new Question( "14. A rectangular 10-turn loop of wire 5cm by 2cm carries a current of 2A and is hinged at one side. " +
                "What torque acts on the loop if it is mounted with its plane at an angle of 60° to the direction of the " +
                "uniform magnetic field of 0.1T?",
                "A. 1 x 10^-3 Nm",
                "B. 2 x 10^-3 Nm",
                "C. 3 x 10^-3 Nm",
                "D. 4 x 10^-3 Nm",
                "A. 1 x 10^-3 Nm"));

        questionList.add(new Question( "15. A DC motor with a resistance of 5.0Ω in its windings operates on 125V. It has a back emf of 100V " +
                "when the motor reaches full speed. What is the armature current at operating speed under a normal load?",
                "A. 5A",
                "B. 10A",
                "C. 15A",
                "D. 25A",
                "A. 5A"));

        questionList.add(new Question( "16. The resistance of the armature in an electric motor is 3.5Ω. It draws a current of 1.50A when " +
                "operating on 120V. Calculate the back emf produced",
                "A. 120.50V",
                "B. 118.20V",
                "C. 116.50V",
                "D. 114.75V",
                "D. 114.75V"));

        questionList.add(new Question( "17. A motor has a back emf of 120V and an armature current of 90A when running at 50Hz. Determine " +
                "the power and the torque developed within the armature",
                "A. 10.8kW, 32.4Nm",
                "B. 10.8kW, 34.4Nm",
                "C. 12.6kW, 32.4Nm",
                "D. 12.6kW, 34.4Nm",
                "B. 10.8kW, 34.4Nm"));

        questionList.add(new Question( "18. Complete the following sentence: The phenomenon of producing an induced emf with the aid of a " +
                "magnetic field is",
                "A. called electromotive production",
                "B. almost never observed",
                "C. only produced by changing the magnetic field in the presence of a coil of wire",
                "D. called electromagnetic induction",
                "D. called electromagnetic induction"));

        questionList.add(new Question( "19. Which of the following phrases best describe the term “magnetic flux”?",
                "A. the direction of the magnetic field relative to a surface",
                "B. the amount of magnetic field that passes through a surface",
                "C. the number of magnetic dipoles moving through a wire",
                "D. the flow of magnetons in space",
                "B. the amount of magnetic field that passes through a surface"));

        questionList.add(new Question( "20. Which of the following choices is the S.I unit for magnetic flux?",
                "A. Gauss (G)",
                "B. Tesla (T)",
                "C. Weber (Wb)",
                "D. Lumen (L)",
                "C. Weber (Wb)"));

        questionList.add(new Question( "21. Which of the following expressions equals magnetic flux?",
                "A. BAcosθ",
                "B. Bcos²θ/A",
                "C. BAsin²θ",
                "D. B/A",
                "A. BAcosθ"));

        questionList.add(new Question( "22. A coil of wire with N turns and area A is placed into a magnetic field of magnitude B. The angle of " +
                "the normal to the plane of the coil is at angle θ with respect to the magnetic field. According to " +
                "Faraday's law, which of the following changes will produce an emf in a coil of wire?",
                "A. B is decreased",
                "B. A is increased",
                "C. θ is decreased",
                "D. any of the above choices",
                "D. any of the above choices"));

        questionList.add(new Question( "23. Complete the following statement: Lenz's law indicates that the induced currents form to oppose",
                "A. a change in the magnetic field direction",
                "B. a change in the magnetic field",
                "C. the magnetic flux",
                "D. a change in the magnetic flux",
                "D. a change in the magnetic flux"));

        questionList.add(new Question( "24. Which of the following principles or laws allows one to determine the direction of an induced " +
                "current in a conducting loop of wire due to a changing magnetic flux?",
                "A. Lenz's law",
                "B. Gauss' law",
                "C. Equivalence principle",
                "D. Faraday's law",
                "A. Lenz's law"));

        questionList.add(new Question( "25. Lenz's law is a consequence of what other physical law?",
                "A. Newton's first law",
                "B. Conservation of energy",
                "C. Newton's third law",
                "D. Conservation of momentum",
                "B. Conservation of energy"));

        questionList.add(new Question( "26. Complete the following statement: Faraday's law indicates that a changing magnetic field produces",
                "A. an electric field",
                "B. an induced magnetic field",
                "C. light",
                "D. global warming",
                "B. an induced magnetic field"));

        questionList.add(new Question( "27. An electric heater is labeled 220V AC, 1500W. What is the peak current in the heater when " +
                "connected to a 220V AC supply?",
                "A. 7.64A",
                "B. 8.64A",
                "C. 9.64A",
                "D. 10.64A",
                "C. 9.64A"));

        questionList.add(new Question( "28. An inductor dissipates heat at a rate of 40W when an alternating current of 0.6A flows in it. If the " +
                "reactance of the inductor at the frequency of the supply is 60Ω, what is its impedance?",
                "A. 126.3Ω",
                "B. 226.3Ω",
                "C. 326.3Ω",
                "D. 426.3Ω",
                "A. 126.3Ω"));

        questionList.add(new Question( "29. When an alternating current flows through a resistor of resistance 5Ω, heat is dissipated at a rate of " +
                "20W. What is the rms value of the alternating current?",
                "A. 2A",
                "B. 5A",
                "C. 10A",
                "D. 20A",
                "A. 2A"));

        questionList.add(new Question( "30. If the peak current in a resistor of resistance R is given by I｡. What is the power dissipated from the resistor?",
                "A. I｡²R/2",
                "B. I｡²R/√2",
                "C. E²/R",
                "D. I｡R/√2",
                "A. I｡²R/2"));
    }
}



