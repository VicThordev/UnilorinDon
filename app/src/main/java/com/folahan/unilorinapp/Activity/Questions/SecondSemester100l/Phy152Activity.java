package com.folahan.unilorinapp.Activity.Questions.SecondSemester100l;

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

public class Phy152Activity extends AppCompatActivity {

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
        setContentView(R.layout.activity_phy152);

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
                "force of repulsion between the spheres is to be 10-19N?",
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
                "respectively",
                "A. 5.4 x 10^6N/C, 4.5 x 10^5N/C",
                "B. 6.4 x 10^6N/C, 5.5 x 10^5N/C",
                "C. 7.4 x 10^6N/C, 6.5 x 10^5N/C",
                "D. 8.4 x 10^6N/C, 7.5 x 10^5N/C",
                "A. 5.4 x 10^6N/C, 4.5 x 10^5N/C"));

        questionList.add(new Question( "14. Find the electric field at a point 0.2m from a charge of 20μC, what force will the electric field exert " +
                "on a charge of 10μC, placed at that point?" +
                "education. ",
                "A. 3.5 x 10" +
                        "^6N/C, 35N",
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
                "centers are separated by 1.6 x 10-14m",
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

    }
}