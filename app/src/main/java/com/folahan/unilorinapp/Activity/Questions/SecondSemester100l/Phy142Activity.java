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

public class Phy142Activity extends AppCompatActivity {

    private List<Question> questionList;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 900000, questionAnswered = 1, clicked = 0;
    Button btnNext, btnPrev, btnEnd;
    private AlertDialog.Builder dialog;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phy142);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        questionList = new ArrayList<>();
        questionText = findViewById(R.id.questionText);
        btnEnd = findViewById(R.id.buttonGoto);
        answerText = findViewById(R.id.txtAnswer);
        rbOption1 = findViewById(R.id.radioA);
        rbOption2 = findViewById(R.id.radioB);
        rbOption3 = findViewById(R.id.radioC);
        rbOption4 = findViewById(R.id.radioD);
        questionNo = findViewById(R.id.question1);
        countDown = findViewById(R.id.timeText);


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
        } else if (SecondSemesterActivity.questionRequestCode == 4) {
            getQuestionPhase4(questionList);

            setDataView(pos);
        } else if (SecondSemesterActivity.questionRequestCode == 5) {
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

        setListeners();
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
            btnEnd.setText(R.string.go_home);
            btnEnd.setOnClickListener(view1 ->
                    startActivity(new Intent(this, MainActivity.class)));
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

    private void setDataView(int position) {
        questionText.setText(questionList.get(position).getQuestion());


        rbOption1.setText(questionList.get(position).getOption1());
        rbOption2.setText(questionList.get(position).getOption2());
        rbOption3.setText(questionList.get(position).getOption3());
        rbOption4.setText(questionList.get(position).getOption4());
        answerText.setText(questionList.get(position).getAnswer());

        answerText.setVisibility(View.VISIBLE);
        answerText.setText(questionList.get(position).getAnswer());

        questionNo.setText("Question "+questionAnswered+" of 30");


    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("What is the absolute configuration of 2/2 the molecule shown?",
                "A. 150.6cm",
                "B. 138.9cm",
                "C. 148.7cm",
                "D. 165.3cm",
                "B. 138.9cm"));

        questionList.add(new Question("At what distance would the repulsive force between two electrons have a magnitude of one newton?",
                "A. 0.0152pm",
                "B. 0.0142pm",
                "C. 0.0132pm",
                "D. 0.0122pm",
                "A. 0.0152pm"));

        questionList.add(new Question("How many excess elections must be placed on each of two small spheres spaced 3cm apart, if the " +
                "force of repulsion between the spheres is to be 10-19N?",
                "A. 125 electrons ",
                "B. 250 electrons",
                "C. 625 electrons",
                "D. 750 electrons",
                "C. 625 electrons"));

        questionList.add(new Question("What is the total positive charge in Coulombs, of all the protons in 1mol of Hydrogen atoms?",
                "A. 96.35kC",
                "B. 72.46kC",
                "C. 83.48kC",
                "D. 78.36kC",
                "A. 96.35kC"));

        questionList.add(new Question("An α-particle is a nucleus of doubly-ionised helium. It has a mass of 6.69 x 10^-27kg and a charge of -2e. " +
                "Compute the ratio of the force of electrostatic repulsion between two α-particles to the force of " +
                "gravitational attraction between them",
                "A. 6.2 x 10^35",
                "B. 4.2 x 10^35",
                "C. 3.1 x 10^35",
                "D. 2.1 x 10^35",
                "C. 3.1 x 10^35"));

        questionList.add(new Question("Compute the ratio of the electric force of attraction to the gravitational force of attraction between \n" +
                "the electron and the proton in a hydrogen atom assuming that their distance of separation is 0.53pm \n" +
                "(me = 9.1 x 10^-31kg, mp = 1.7 x 10^-27kg, e = 1.6 x 10^-19C)",
                "A. 2.2 x 10^39",
                "B. 1.1 x 10^39",
                "C. 2.1 x 10^39" ,
                "D. 1.2 x 10^39", "A. 2.2 x 10^39"));

        questionList.add(new Question("What is the magnitude of electrostatic force of attraction between an α-particle and an electron 10^-" +
                "13m apart?",
                "A. 2.3 x 10^-2N", "B. 2.6 x 10^-2N",
                "C. 4.3 x 10^-2N", "D. 4.6 x 10^-2N",
                "D. 4.6 x 10^-2N"));

        questionList.add(new Question("From the Coulomb's Law, what will be the slope of the graph of Log F vs Log r? ",
                "A. ½", "B. 2",
                "C. -log2",
                "D. -2",
                "A. ½"));

        questionList.add(new Question("Two equal charges of equal magnitude exert an attractive force of 4.0 x 10^-4N on each other. If the " +
                "magnitude of each charge is 2.0μC, how far apart are the charges?",
                "A. 9.5m",
                "B. 9.7m",
                "C. 10.5m",
                "D. 10.7m",
                "A. 9.5m"));

        questionList.add(new Question("What is the magnitude of a point charge whose electric field 50cm away has magnitude 2.0N/C?",
                "A. 4.56 x 10^-11C",
                "B. 4.66 x 10^-11C",
                "C. 5.56 x 10^-11C ",
                "D. 5.66 x 10^-11C", "C. 5.56 x 10^-11C "));

        questionList.add(new Question("What is the magnitude of an electric field in which the force on an electron is equal in magnitude to " +
                "the weight of an electron?",
                "A. 4.58 x 10^-11N/C",
                "B. 5.58 x 10^-11N/C" ,
                "C. 6.58 x 10^-11N/C",
                "D. 7.58 x 10-^11N/C",
                "B. 5.58 x 10^-11N/C"));

        questionList.add(new Question( "A small object carrying a charge of 5 x 10^-9" +
                "C experiences a downward force of 20 x 10^-9N when" +
                "placed at a certain point in an electric field. What's the electric field at the point?",
                "A. 100 N/C",
                "B. 40 N/C",
                "C. 10 N/C",
                "D. 4 N/C",
                "D. 4 N/C"));

        questionList.add(new Question( "The distance between two positive charges 6μC & 8μC is 50cm. Calculate the electric field intensity, " +
                "due to each charges, at a point p in between the two charges and 10cm from the 6μC charge" +
                "respectively",
                "A. 5.4 x 10^6N/C, 4.5 x 10^5N/C",
                "B. 6.4 x 10^6N/C, 5.5 x 10^5N/C",
                "C. 7.4 x 10^6N/C, 6.5 x 10^5N/C",
                "D. 8.4 x 10^6N/C, 7.5 x 10^5N/C",
                "A. 5.4 x 10^6N/C, 4.5 x 10^5N/C"));

        questionList.add(new Question( "Find the electric field at a point 0.2m from a charge of 20μC, what force will the electric field exert " +
                "on a charge of 10μC, placed at that point?" +
                "education. ",
                "A. 3.5 x 10" +
                        "^6N/C, 35N",
                "B. 4.5 x 10^6N/C, 45N",
                "C. 5.5 x 10^6N/C, 55N",
                "D. 6.5 x 10^6N/C, 65N",
                "B. 4.5 x 10^6N/C, 45N"));

        questionList.add(new Question( "The magnitude of electric field is 40kN/C. If an electron is placed in the same field, what force will be \n" +
                "exerted on it?",
                "A. 3.4 x 10^-15N",
                "B. 4.4 x 10^-15N",
                "C. 5.4 x 10^-15N",
                "D. 6.4 x 10^-15N",
                "D. 6.4 x 10^-15N"));

        questionList.add(new Question( "Wave-particle duality theory implies that:",
                "A Light wave can diffract and scatter",
                "B Light wave can effect Compton effect",
                "C Light wave can undergo interference and cause photoemission of electrons " +
                        "on a metal.",
                "D Light wave can be seen as a particle and as well effect photoelectric effect",
                "C Light wave can undergo interference and cause photoemission of electrons " +
                        "on a metal."));

        questionList.add(new Question( "Compton effect obtained when X-rays are scattered by a plane of lattice electrons Implies:",
                "A Radiation absorption",
                "B Particle nature of electromagnetic waves.",
                "C X-rays control.",
                "D Crystallographic application of X-rays",
                "B Particle nature of electromagnetic waves."));

        questionList.add(new Question( "An entity exhibits particle nature by possessing:",
                "A Energy and wavelength.",
                "B Momentum and frequency.",
                "C Energy and momentum.",
                "D Wavelength and frequency.",
                "C Energy and momentum."));

        questionList.add(new Question( "Calculate the mass of a de Broglie’s particle traveling at a speed of 30m/s with wavelength 1.49x10^-26nm:",
                "A 0.43 x 10^-3kg.",
                "B 0.15 kg",
                "C 0.35 x 10^-3kg.",
                "D 0.96 kg.",
                "B 0.15 kg"));

        questionList.add(new Question( "Bohr proved electrons as a particle while Schrödinger proved it as a wave. The two " +
                "ideas can be combined and represented as:",
                "A Angular momentum =h/mv",
                "B Angular momentum =nh/2π",
                "C Angular momentum = nλ/2π",
                "D Angular momentum = 2 π r/n",
                "B Angular momentum =nh/2π"));

        questionList.add(new Question( "Calculate the wavelength of the electron-wave for electrons fixed round an orbit " +
                "whose diameter is 1.2 nm if 24 complete waves are formed round the orbit:",
                "A 1.6 x 10^-10 m",
                "B 3.4 x 10^-12 m",
                "C 4.3 x 10^-10 m",
                "D 9.6 x 10-12 m",
                "A 1.6 x 10^-10 m"));

        questionList.add(new Question( "‘HARD` X-rays can be produced by:",
                "A. Increasing the cathode temperature.",
                "B. Altering the accelerating voltage ",
                "C. Evacuating the tube completely.",
                "D. Making the electrons move faster",
                "D. Making the electrons move faster"));

        questionList.add(new Question( "All of these are components in X-ray production tube except:",
                "A. Concave cathode, Lead shield, Lead anode.",
                "B. X-ray window, cooling fins, hot cathode.",
                "C. Accelerating voltage, Electron beam, Low melting point target metal.",
                "D. A and C.",
                "D. A and C."));

        questionList.add(new Question( "The following are the properties of X- rays except:",
                "A. They are not deflected by electric fields.",
                "B. ‘Soft` X-rays can pass through human skull",
                "C. They can be used to discharge gold-leaf electroscope",
                "D. They can release photoelectrons.",
                "B. ‘Soft` X-rays can pass through human skull"));

        questionList.add(new Question( "Reflection of X-rays that fell on two electron planes separated by distance" +
                " 3.1 x 10^-10m were obtained. Calculate Bragg’s glancing angle, if the total path " +
                " difference between the reflected waves from the two planes is 6.2 x 10^-10 m.",
                "A. 60°",
                "B. 45°",
                "C. 73°",
                "D. 90°",
                "D. 90°"));

        questionList.add(new Question( "Calculate the wavelength of X-rays emitted when electrons accelerated through " +
                " 30kV strike a target, given that charge electron",
                "A. 4.1 x 10^-11 m",
                "B. 17.11 x 10-10m",
                "C. 0.5 x 10-11m",
                "D. 5.7 x 10-10 m",
                "A. 4.1 x 10^-11 m"));

        questionList.add(new Question( "What is the minimum potential difference between the cathode and anode of an X-ray tube if rays of wavelength " +
                "0.05nm where produced",
                "A. 16 kV",
                "B. 45 kV",
                "C. 25 kV",
                "D. 99 kV",
                "C. 25 kV"));

        questionList.add(new Question( "Calculate the thickness of the patient’s skin if 40% of the incident X-rays were " +
                " absorbed by his flesh, let absorption coefficient be 2 units.",
                "A. 0.53m",
                "B. 0.30m",
                "C. 27 cm",
                "D. 1.2 cm",
                "B. 0.30m"));

        questionList.add(new Question( "Diode valve works on principle of :",
                "A. Photoemission",
                "B. Thermionic emission",
                "C. Compton scattering",
                "D. Electron drifting. ",
                "B. Thermionic emission"));

        questionList.add(new Question( "If sodium surface in a vacuum is illuminated with 200 nm wavelength beam. " +
                "Calculate the maximum velocity of the photoelectrons released [Take work " +
                "function of sodium to be 2.0 x 10-19 J.}",
                "A. 19.2 x 10^8 m/s",
                "B. 4.7 x 10^8 m/s",
                "C. 16.0 x 10^7 m/s",
                "D. 1.9 x 10^8 m/s",
                "C. 16.0 x 10^7 m/s"));

        questionList.add(new Question( "The major use of diode valve is for:",
                "A. Rectification",
                "B. Amplification",
                "C. Filtering",
                "D. Modification",
                "A. Rectification"));

        questionList.add(new Question( "The major impression of Moseley’s law pertaining the line spectra obtained during" +
                " the production of X-rays holds that:",
                "A. The frequency of the line is proportional to the X-ray intensity",
                "B. The frequency of the line is smaller for atom with one atomic number, than for those with higher atomic numbers. ",
                "C. The frequency of the line is proportional to the wavelength of the X-rays",
                "D. The frequency of the line reduces time",
                "B. The frequency of the line is smaller for atom with one atomic number, than for those with higher atomic numbers. "));

        questionList.add(new Question( "All of these are correct about photoelectric emission except:",
                "A. No emission if work function and photon energy are equal.",
                "B. High work function makes the photoelectrons to move faster",
                "C. A and B",
                "D. Excess energy of photon serves as kinetic energy for the photoelectrons to move",
                "A. No emission if work function and photon energy are equal."));

        questionList.add(new Question( "If ΔE, Δx and Δp are the smallest uncertainty measurements within the smallest " +
                "uncertainty time Δt, in energy, position and momentum the uncertainty principle can be stated as:",
                "A. ΔE. Δx = h/2π",
                "B. Δp. ΔE = h/2π",
                "C. Δp. Δx = h/2π",
                "D. Δt. Δx = h/2π",
                "C. Δp. Δx = h/2π"));

        questionList.add(new Question( "Calculate threshold frequency in a photoelectric emission process if a photon of " +
                "1.6 x 10^-19 J released an electron to move with velocity of 2.4 x 10^5 m/s ",
                "A. 1.4 x 10^15 Hz",
                "B. 9.1 x 10^-18 Hz",
                "C. 4.1 x 10^-17 Hz",
                "D. 2.0 x 10^14 Hz",
                "D. 2.0 x 10^14 Hz"));

        questionList.add(new Question( "All of these are instances when an entity behaves like a particle:",
                "A. Compton effect and diffraction",
                "B. Photoelectric effect and refraction.",
                "C. X-Ray production and interference",
                "D. Photoemission and Compton effect",
                "D. Photoemission and Compton effect"));

        questionList.add(new Question( "A Photon of 1.6 x 10^-19 J did a work of 0.3 x 10^-19 J to free an electron whose " +
                "mass is 9.1 x 10^-31 Kg. Calculate the velocity of the electron after been released",
                "A. 5.3 x10^5 m/s",
                "B. 60.1 x 10^5 m/s",
                "C. 9.2 x 10^5 m/s",
                "D. 1.12 x 10^5 m/s",
                "A. 5.3 x10^5 m/s"));

        questionList.add(new Question( "One of the following is not a proper use of X-rays:",
                "A. Crystallographic study",
                "B. Identification of alteration made on artistic works",
                "C. Mapping the internal organ such as bone marrow in human body",
                "D. to analyze the internal organ of metal machines ",
                "A. Crystallographic study"));

        questionList.add(new Question( "Current dies down in a working diode value, without an accelerating potential, because:",
                "A. Anode is shielded",
                "B. Of the presence of space charge",
                "C. Temperature of the cathode reduces",
                "D. Diode values usually have short life span",
                "B. Of the presence of space charge"));

        questionList.add(new Question( "Which of the following is not true of J .J Thomson model of atom?",
                "A. There is a central nucleus in which protons are bound",
                "B. Positive charges are spread throughout the atom forming a kind of paste in which electrons are suspended",
                "C. Electrons move around the nucleus randomly",
                "D. Electron occupy only discrete shells",
                "B. Positive charges are spread throughout the atom forming a kind of paste in which electrons are suspended"));

        questionList.add(new Question( "In Ernest Rutherford experiment",
                "A. Alpha particles were bombarded by gold particles",
                "B. No alpha particles were deflected",
                "C. All alpha particles were deflected",
                "D. Atoms of gold were bombarded with alpha particles",
                "D. Atoms of gold were bombarded with alpha particles"));

        questionList.add(new Question( "According to Ernest Rutherford",
                "A. Electrons are concentrated at the centre of the atom",
                "B. Electrons are occupying different orbits or energy levels",
                "C. Electrons revolve randomly around the nucleus",
                "D. Electrons maintain their fixed position around the nucleus",
                "C. Electrons revolve randomly around the nucleus"));

        questionList.add(new Question( "The shortest wavelength of the Balmer’s series is obtained when n is",
                "A. 0",
                "B. 1",
                "C. 2",
                "D. 3",
                "D. 3"));

        questionList.add(new Question( "Which of the following represents the wavelength of the Paschen series",
                "a. 1/⋋ = R (1/3^2 – 1/n^2)",
                "b. 1/⋋ = R (1/2^2– 1/n^2)",
                "c. 1/⋋ = R (1/1^2 – 1/n^2)",
                "d. 1/⋋ = R (1/0^2 – 1/n^2",
                "a. 1/⋋ = R (1/3^2 – 1/n^2)"));

        questionList.add(new Question( "An atom is assumed to have zero energy in the ground state and its energy in the " +
                "first, second and third excited states are 1.635 x 10^-18J, 1.93 x 10^-18J and 2.024 x " +
                "10^-18J respectively. What is the wavelength of the photon which would excite the atom from the first excited state to the second excited state?",
                "a. 6.61 x 10^-7m",
                "b. 4.24 x 10^-7m",
                "c. 3.24 x 10^-7m",
                "d. 3.0 x 10^-7m",
                "a. 6.61 x 10^-7m"));

        questionList.add(new Question( "The longest and the shortest wavelength of the Balmer’s are ",
                "a. 365nm, 656nm",
                "b. 656nm, 365nm",
                "c. 823nm, 109nm",
                "d. 109nm, 823nm",
                "d. 109nm, 823nm"));

        questionList.add(new Question( "Which of the following is true of Bohr’s model of hydrogen atom",
                "a. The total energy of the atom is positive",
                "b. The total energy is dependent on the radius of orbit",
                "c. No force is exerted on electrons inside an orbit",
                "d. Total energy is independent of the radius",
                "b. The total energy is dependent on the radius of orbit"));

        questionList.add(new Question( "Bohr model does not apply where more than one electron are present in an orbit " +
                "round the nucleus because",
                "a. The model does not account for the electrostatic forces that electrons exert on each other",
                "b. The energy of such atoms does not follow simple theory",
                "c. The idea of photon and quanta do not apply to such atoms",
                "d. The number of orbit will be more than one",
                "c. The idea of photon and quanta do not apply to such atoms"));

        questionList.add(new Question( "What is the energy of the second excited state of hydrogen",
                "a. -13.6eV",
                "b. -3.40eV",
                "c. -15.6eV",
                "d. +13.6eV",
                "b. -3.40eV"));

        questionList.add(new Question( "The line spectrum emitted by atomic hydrogen when electrons transit from high " +
                "energy levels to the third excited state is called",
                "a. Paschen series",
                "b. Balmer’s series",
                "c. Lyman series",
                "d. Bracket series",
                "d. Bracket series"));
    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question("Determine the wavelength that correspond to the transition from ni = 6 to nf =4",
                "a. 4050nm",
                "b. 2629nm",
                "c. 1050nm",
                "d. 3050nm",
                "b. 2629nm"));

        questionList.add(new Question("The difference between spontaneous emission and stimulated emission is that",
                "a. Spontaneous emission is a sum of both stimulated emission term and spontaneous emission",
                "b. Spontaneous emission is self emission which does not need external photon",
                "c. Stimulated emission does not need external photon",
                "d. Stimulated emission occurs when electrons move lower energy to higher energy",
                "a. Spontaneous emission is a sum of both stimulated emission term and spontaneous emission"));

        questionList.add(new Question("In the production of cathode rays using photoelectric effect, the most important part in the discharge tube is",
                "a. Cathode space",
                "b. The bright region",
                "c. Faraday dark region",
                "d. The positive column",
                "a. Cathode space"));

        questionList.add(new Question("Cathode rays are produced in the discharge tube when voltages between the plates is increased by means of",
                "a. Break down",
                "b. Acceleration",
                "c. Collision",
                "d. Saturation",
                "c. Collision"));

        questionList.add(new Question("A beam of electrons moving with a velocity of 1.0 x 10^7m/s enters midway " +
                "between two horizontal parallel plates P and Q in a direction parallel to the " +
                "plates. P and Q are 5cm long and 2cm apart and have a potential difference V " +
                "applied between them. Calculate V, if the beam is deflected so that it just grazes " +
                "the edge of the low plate Q (assume e/m = 1.8 x 10^11 C/Kg).",
                "a. 17.8V",
                "b. 44.5V",
                "c. 89.0V",
                "d. 178.0V",
                "c. 89.0V"));

        questionList.add(new Question("What is the grazing angle of the beam of electrons moving with a velocity of 1.0 x " +
                "10^7m/s entering midway between two horizontal parallel plates P and Q in a " +
                "direction parallel to the plates. P and Q are 5cm long and 2cm apart and have a " +
                "potential difference V applied between them",
                "a. 5.71",
                "b. 11.0",
                "c. 2.89",
                "d. 16.3",
                "a. 5.71"));

        questionList.add(new Question("Proton with a charge – mass ratio of 1.0 x 10^8" +
                "c/kg is rotated in a circular orbit of " +
                "radius r when they enter a uniform magnetic field of 0.5T. Calculate the number " +
                "of revolutions.",
                "a. 2 x 10^6Hz",
                "b. 4 x 10^6Hz",
                "c. 6 x 10^6Hz",
                "d. 8 x10^6Hz",
                "d. 8 x10^6Hz"));

        questionList.add(new Question("The path of a beam of electrons in an electric field is",
                "a. Circle",
                "b. Spiral",
                "c. Parabola",
                "d. Ellipse",
                "c. Parabola"));

        questionList.add(new Question("The maximum wavelength of light that can produce photoelectrons from sodium is " +
                "650nm. What is the work function of sodium?",
                "a. 3.05 x 10^-17J",
                "b. 3.06 x 10^-26J",
                "c. 4.31 x 10^-10J",
                "d. 6.50 x 10^-9J",
                "b. 3.06 x 10^-26J"));

        questionList.add(new Question("The maximum wavelength of light that can produce photoelectrons from sodium is " +
                "650nm. If light of wavelength 436nm is used to illuminate a sodium surface in a " +
                "vacuum, what is the maximum K.E of the photoelectrons?",
                "a. 4.25 x 10^-25J",
                "b. 1.42 x 10^-40J",
                "c. 1.50 x 10^-19J",
                "d. 3.06 x 10-19J",
                "b. 1.42 x 10^-40J"));

        questionList.add(new Question("When a metal is illuminated by monochromatic radiation of wavelength 248nm, " +
                "the maximum kinetic energy of photoelectrons emitted is found to be 8.6 x 10-20J. " +
                "Calculate the work function of the metal",
                "a. -8.599 x 10^-20J",
                "b. 7.16 x 10^-19J",
                "c. -8.58 x 10^-20J",
                "d. 1.72 x 10^-19J",
                "a. -8.599 x 10^-20J"));

        questionList.add(new Question("Which of the following is the failure associated with the Rutherford’s model of the atom",
                "a. atoms are not electrically neutral",
                "b. electrons can only move round the proton in elliptical orbits",
                "c. the charges are evenly distributed",
                "d. electron will spiral into the proton which is at the center of the nucleus",
                "d. electron will spiral into the proton which is at the center of the nucleus"));

        questionList.add(new Question("If the energy levels En is related to the principal quantum number “n” by En = -13.6eV/n^2" +
                ", calculate the ionization energy of the hydrogen atom.",
                "a. -13.6eV",
                "b. 13.6eV",
                "c. 0eV",
                "d. -27.2eV",
                "b. 13.6eV"));

        questionList.add(new Question("X-rays of wavelength 1.5 x10-10m is incident on a crystal and it gives a third order " +
                "diffraction for a glancing angle of 600. What is the separation of the layers of " +
                "atoms in the crystal?",
                "a. 2.46 x 10^-10m",
                "b. 1.23 x 10^-10m",
                "c. 7.38 x 10^-10m",
                "d. 4.65 x 10^7m",
                "c. 7.38 x 10^-10m"));

        questionList.add(new Question("Which of the following does account for the failure of the Bohr model of atom",
                "a. it is not intellectually satisfactory",
                "b. it could not explain the observed fine structure of the atomic spectra",
                "c. it only explains the single electron atoms",
                "d. it could not explain the observed atomic spectra",
                "c. it only explains the single electron atoms"));

        questionList.add(new Question("The potential difference between the target and cathode of an X-ray tube is 20KV " +
                "and the current is 20mA. Only 5% of the total energy supplied is emitted as x-rays. " +
                "What is the minimum wavelength of the x-ray emitted?.",
                "a. 6.19 x 10^-11m",
                "b. 3.87 x 10^8m",
                "c. 3.22 x 10^-10m",
                "d. 27.22 x 10^1m",
                "a. 6.19 x 10^-11m"));

        questionList.add(new Question("Calculate the velocity of electrons accelerated from rest to a target in hot cathode " +
                "vacuum tube by a potential difference of 25V",
                "a. 2.98 x 10^8m/s",
                "b. 3.98 x 10^8m/s",
                "c. 2.98 x 10^3m/s",
                "d. 4.44 x 10^5m/s",
                "c. 2.98 x 10^3m/s"));

        questionList.add(new Question("Electrons moving with a constant velocity enter a uniform magnetic field B in a " +
                "direction perpendicular to B. The path of the electrons in the field is",
                "a. a helix",
                "b. a straight line parallel to B",
                "c. a straight line perpendicular to B",
                "d. a circle",
                "d. a circle"));

        questionList.add(new Question("Calculate the De Broglie wavelength of a 0.01kg material having a velocity 0f 10m/s",
                "a. 6.63 x 10^-23m",
                "b. 6.63 x 10^-33m",
                "c. 6.63 x 10^-26m",
                "d. 6.63 x 10^-10m",
                "a. 6.63 x 10^-23m"));

        questionList.add(new Question("The wavelength of the spectral line in the hydrogen spectrum are given empirically " +
                "by 1/λm = R( 1/n^2 – 1/m^2" +
                ") where R = 1.097 x 10^7/m and n and m are integers. " +
                "Calculate the wavelength of Hβ in the Balmer’s series.",
                "a. 4.00 x 10^-7m",
                "b. 4.86 x 10^-7m",
                "c. 1.37 x 10^-6m",
                "d. 7.30 x 10^-7m",
                "b. 4.86 x 10^-7m"));

        questionList.add(new Question("Which of the following is a correct statement of Milikan’s famous experimental results?",
                "a. electron is a common constituent of all matter",
                "b. all charges are integral multiple of a discrete electronic charge",
                "c. electron can be deflected by both the electric and magnetic field",
                "d. electronic – mass ratio is constant",
                "d. electronic – mass ratio is constant"));

        questionList.add(new Question("A radioactive source contains 1.0 x 10^-6g of platinum239. It is estimated that this " +
                "source emits 2300 alpha particles per second. Calculate the T1/2 of platinum.",
                "a. 7.59 x 10^11s",
                "b. 7.50 x 10^11s",
                "c. 8.00 x 10^11s",
                "d. 8.59 x 10^11s",
                "a. 7.59 x 10^11s"));

        questionList.add(new Question("Deuterium is represented by the symbol 2H1. What nucleons constitute its nucleus?",
                "a. one proton, one neutron",
                "b. two protons, two neutrons",
                "c. two protons, one neutron",
                "d. one proton, two neutrons",
                "a. one proton, one neutron"));

        questionList.add(new Question("Calculate the nuclear binding energy of deuterium 2H1 given that mass of one atom " +
                "of deuterium is 2.01410mu, mass of one hydrogen atom is 1.00788mu and rest " +
                "mass of a neutron is 1.00867mu. (1mu = 1.66 x 10^-27kg).",
                "a. 1.749MeV",
                "b. 2.747MeV",
                "c. 3.247Mev",
                "d. 4.000Mev",
                "b. 2.747MeV"));

        questionList.add(new Question("In an x-ray tube, electrons each of charge q are accelerated through a potential " +
                "difference V and then strike a metal target. If h is Planck’s constant and c is the " +
                "speed of light, what is the minimum wavelength of the x-ray produced?",
                "a. h/c",
                "b. hf/c",
                "c. hc/qV",
                "d. hf/V",
                "c. hc/qV"));

        questionList.add(new Question("Calculate the minimum wavelength of the x-ray produced of electrons on the " +
                "screen of a television set where the accelerating potential is 20KV.",
                "a. 1.551 x 10^-19m",
                "b. 1.260 x 10^-26m",
                "c. 0.995 x 10^-29m",
                "d. 0.095 x 10^-19m",
                "d. 0.095 x 10^-19m"));

        questionList.add(new Question("Calculate the energy and momentum of a photon of light of wavelength 500nm.",
                "(a) 6.63 x 10^-17J, 1.330 x 10^-25kgm/s",
                "(b) 2.98 x 10^-19J, 0.133 x 10^-25kgm/s",
                "(c) 3.98 x 10^-19J, 0.013 x 10^-25kgm/s",
                "(d) 4.00 x 10^-19J, 1.330 x 10^-25kgm/s",
                "(c) 3.98 x 10^-19J, 0.013 x 10^-25kgm/s"));

        questionList.add(new Question("If the fission of an atom of 235U yields energy of 200MeV, how much energy " +
                "would be released by the fission of 1g of 235U?",
                "(a) 8.20 x 10^10J",
                "(b) 8.20 x 10^6J",
                "(c) 8.2 x 10^10J",
                "(d) 8.2 x 10^-6J",
                "(a) 8.20 x 10^10J"));

        questionList.add(new Question("The most abundant isotope of helium has a 4He2 nucleus whose mass is " +
                "6.6447 x 10^-27 kg. For this nucleus, find the mass defect ∆m",
                "(a) 0.0620 x 10^-27kg",
                "(b) 0.0503 x 10^-27 kg",
                "(c) 0.0412 x 10^-27 kg",
                "(d) 0.0205 x 10^-27 kg",
                "(a) 0.0620 x 10^-27kg"));

        questionList.add(new Question("The most abundant isotope of helium has a 4He2 nucleus whose mass is" +
                " 6.6447 x 10 -27 kg. For this nucleus , find the binding energy of the nucleus",
                "(a) 28.3 MeV",
                "(b) 26.4 MeV",
                "(c) 27.2 MeV",
                "(d) 30.0 MeV",
                "(a) 28.3 MeV"));

        questionList.add(new Question("Determine the energy released when 238 U 92 decays into 234 Th 90\n" +
                "238U92 = 238.0508u\n" +
                "234Th90 = 234.0436u\n"+
                "4He2 = 4.0026u\n" +
                " 1u = 931.5 MeV",
                "(a) 7.8 MeV",
                "(b) 2.8 MeV",
                "(c) 5.6 MeV",
                "(d) 4.3 MeV.",
                "(d) 4.3 MeV."));

        questionList.add(new Question("When Uranium 238U92 is decays to Thorium 234Th90 a gamma ray of 0.0496 MeV is " +
                "also emitted. What is the wavelength of the emitted gamma ray\n",
                "(a) 4.3 x 10^-11m",
                "(b) 3.66 x 10^-11m",
                "(c) 2.51 x 10^-11m",
                "(d) 1.21 x 10^-11m",
                "(c) 2.51 x 10^-11m"));

        questionList.add(new Question("Radon 222Rn86 was produced when radium 226Ra88 undergoes α – decay. Suppose " +
                "3.0 x 10^7 radon atoms are trapped and the half-life of radon is 3.83 days. How " +
                "many radon atoms remain after 31 days",
                "(a) 4.2 x 10^5",
                "(b) 1.1 x 10^5",
                "(c) 2.3 x 10^5",
                "(d) 3.0 x 10^5",
                "(b) 1.1 x 10^5"));

        questionList.add(new Question("Radon 222Rn86 was produced when radium 226Ra88 undergoes α – decay. Suppose " +
                " 3.0 x 10^7 radon atoms are trapped. The half-life of radon is 3.83 days. Find the activity for element",
                "(a) 63Bq",
                "(b) 50Bq",
                "(c) 45Bq",
                "(d) 70Bq",
                "(a) 63Bq"));

        questionList.add(new Question("A 14C6 activity of about 0.18 Bq per gram of carbon was measured on a scroll. " +
                "Determine the age of the scroll, If activity A0 = 0.23Bq and the half life is " +
                "730years.",
                "(a) 4.0 x 10^3yr",
                "(b) 3.0 x 10^3yr",
                "(c) 2.0 x 10^3yr",
                "(d) 1.0 x 10^3yr",
                "(c) 2.0 x 10^3yr"));

        questionList.add(new Question("A device that can be used to detect α , β and γ rays is",
                "(a) Geiger counter",
                "(b) Newton counter",
                "(c) Thompson counter",
                "(d) Compton counter",
                "(a) Geiger counter"));

        questionList.add(new Question("What is the wavelength of the 0.186 MeV γ– ray that is emitted by radium 226Ra88",
                "(a) 5.72 x 10^-12m",
                "(b) 4.68 x 10^-12m",
                "(c) 6.68 x 10^-12m",
                "(d) 7.11 x 10^-12m",
                "(c) 6.68 x 10^-12m"));

        questionList.add(new Question("Determine the symbol A" +
                "XZ for the parent nucleus whose α – decay produces the" +
                " same daughter as the β- decay of thallium 208Tl81.",
                "(a) 200Po76",
                "(b) 210Po81",
                "(c) 214Po85",
                "(d) 212Po84",
                "(b) 210Po81"));

        questionList.add(new Question("What is the binding energy (in MeV) for oxygen 16 O 8,atomic mass = 15.994915u",
                "(a) 127.6 MeV",
                "(b) 125 MeV",
                "(c) 123.6 MeV",
                "(d) 120.6 MeV",
                "(a) 127.6 MeV"));

        questionList.add(new Question("Nuclei that contain the same number of protons but a different number of Neutrons are known as",
                "(a) Isotopes",
                "(b) Allotropes",
                "(c) Nucleons",
                "(d) Positive Particles",
                "(a) Isotopes"));

        questionList.add(new Question("The total number of protons and neutron is referred to as",
                "a. Atomic Volume",
                "b. Atomic Counting",
                "c. Atomic Summation",
                "d. Atomic mass number",
                "d. Atomic mass number"));

        questionList.add(new Question("The spontaneous disintegration of unstable nucleus of an element is called",
                "(a) Instability",
                "(b) Breaking effect",
                "(c) Radioactivity",
                "(d) Solidification",
                "(d) Solidification"));

        questionList.add(new Question("The following particles and/or high energy photons are released when an " +
                " unstable nucleus disintegrate\n" +
                " (i) Alpha rays (ii) Beta rays (iii) Neutron ray (iv) Gamma ray",
                "(a) i , ii and iv",
                "(b) i, ii and iii",
                "(c) i and iv only",
                "(d) iv only",
                "(a) i , ii and iv"));

        questionList.add(new Question("A stable nucleus requires certain energy to separate its proton and neutron. This energy is called",
                "(a) Binding energy",
                "(b) Fission energy",
                "(c) Potential energy",
                "(d) Threshold energy",
                "(a) Binding energy"));

        questionList.add(new Question("The process of α-decay for which Uranium 238U92" +
                " parents is converted into " +
                "the 234Th90 daughter is known as",
                "(a) Transformation",
                "(b) Translation",
                "(c) Transmutation",
                "(d) Tranfiguration",
                "(c) Transmutation"));

        questionList.add(new Question("Nuclides having the same number of neutron N but a different atomic number Z " +
                "and therefore a different mass number A are called",
                "(a) Isotones",
                "(b) Isotopes",
                "(c) Isobars",
                "(d) Entropid",
                "(c) Isobars"));

        questionList.add(new Question("Nuclides which have the same total number of nucleons but which differ in " +
                "atomic number Z and also in neutron number N called",
                "(a) Isotones",
                "(b) Isotopes",
                "(c) Isobars",
                "(d) Allotropes",
                "(c) Isobars"));

        questionList.add(new Question("Calculate the nuclear radius of a nucleus with mass number 4. Given that " +
                "Ro = 1.4 x 10^-15m",
                "(a) 2.22 x 10^-15m",
                "(b) 1.62 x 10^-15m",
                "(c) 2.78 x 10^-15m",
                "(d) 1.04 x 10^-15m",
                "(a) 2.22 x 10^-15m"));

        questionList.add(new Question("The atomic mass unit (amu) used in expressing the masses of nuclei is",
                "(a) One tenth of the mass of the 12C atom",
                "(b) One fifth of the mass of the 12C atom",
                "(c) One twelfth of the mass of the 12C atom",
                "(d) One third of the mass of the 12C atom",
                "(c) One twelfth of the mass of the 12C atom"));

        questionList.add(new Question("If one atomic mass unit (1amu) is 1.66 x 10^-27kg. Calculate the energy " +
                "equivalence of this mass",
                "(a) 933.7MeV",
                "(b) 683.2 MeV",
                "(c) 999.1 MeV",
                "(d) 709.3 MeV",
                "(a) 933.7MeV"));

        questionList.add(new Question("The binding energy of 35 Cl 11" +
                "is 280 MeV. Find its mass in atomic mass unit " +
                "(amu). Given that 1 amu = 931 MeV",
                "(a) 0.15 amu",
                "(b) 0.30 amu",
                "(c) 0.52 amu",
                "(d) 0.46 amu",
                "(b) 0.30 amu"));
    }

    private void getQuestionPhase3(List<Question> list) {

        questionList.add(new Question("If the half – life of a sample of radioactive material is 60 days, what fraction of " +
                "the original radioactive nuclei will remain after 120 days.",
                "a. 1/4",
                "b. 1/2",
                "c. 1/3",
                "d. 2/5",
                "a. 1/4"));

        questionList.add(new Question("A radioactive material has a half-life of 14 hours. How much of 100 of the " +
                "isotope will be left after 42 hours and what time has elapsed when 6.25g of the " +
                "100g are left",
                "(a) 25g, 60 h",
                "(b) 50g, 70h",
                "(c) 12.5g, 56h",
                "(d) 6.25g, 49h",
                "(c) 12.5g, 56h"));

        questionList.add(new Question("A counter positioned close to an α – particle emitter reads 200 per second and this " +
                "reduced to 50 per second in 720 seconds. Determine the half life of the source",
                "(a) 9 minutes",
                "(b) 6 minutes",
                "(c) 10 minutes",
                "(d) 12 minutes",
                "(b) 6 minutes"));

        questionList.add(new Question("In a Millikan experiment, a charged droplet of mass 1.8 x 10^-15kg just remains " +
                "stationary when the potential difference between the plates, which are 12mm a " +
                "apart is 150V. If the droplet suddenly gains an extra electron, calculate the initial " +
                "acceleration of the droplet",
                "(a) 2.31 m5^-2",
                "(b) 3.00 m5^-2",
                "(c) 2.60 m5^-2",
                "(d) 1.11 m5^-2",
                "(d) 1.11 m5^-2"));

        questionList.add(new Question("In a Milikan experiment, a charged droplet of mass 1.8 x 10^-15kg just remains " +
                "stationary when the potential difference between the plates, which are 12mm " +
                "apart, is 150V. If the droplet suddenly gains an extra electron, find the" +
                "voltage needed to bring the droplet to rest again",
                "(a) 120 V",
                "(b) 135 V",
                "(c) 110 V",
                "(d) 250 V",
                "(b) 135 V"));

        questionList.add(new Question("The primary uses of the Cathode ray oscilloscope (CRO) are to measure the " +
                "following except",
                "(a) Voltage",
                "(b) Frequency",
                "(c) Phase",
                "(d) Mass",
                "(d) Mass"));

        questionList.add(new Question("Which of the following is not a feature of the Cathode ray Oscilloscope?",
                "(a) Cooling compartment",
                "(b) Heated cathode to produce a beam of electron",
                "(c) Accelerating anode",
                "(d) Fluorescent Screen",
                "(a) Cooling compartment"));

        questionList.add(new Question("Which of the following is incorrect",
                "(a) α – particle is slightly deflected by magnetic and Electric field and positively charged",
                "(b) α – particle is slightly deflected and β – particle is greatly deflected but both are negatively charged",
                "(c) β – particle is greatly deflected by magnetic and Electric field and negatively charged",
                "(d) γ-ray is unaffected by magnetic and Electric field and uncharged",
                "(b) α – particle is slightly deflected and β – particle is greatly deflected but both are negatively charged"));

        questionList.add(new Question("Natural Radioactive decay rate depends on",
                "(a) Number of nuclei available to disintegrate",
                "(b) Temperature of the nuclei",
                "(c) Time of the day",
                "(d) Location of the nuclei on the planet",
                "(a) Number of nuclei available to disintegrate"));

        questionList.add(new Question("The following are the advantages of Fusion over Fission except",
                "(a) Easily achieved with light test elements",
                "(b) By – product are non-radioactive",
                "(c) Raw materials are cheaply available",
                "(d) Very high temperature is required",
                "(d) Very high temperature is required"));

        questionList.add(new Question("Radiation from radio Isotopes is useful in",
                "(a) Radiotherapy",
                "(b) Earth digging",
                "(c) Archaeological dating",
                "(d) Thickness gauging",
                "(b) Earth digging"));

        questionList.add(new Question("Calculate the count rate produced by 0.1μg of caesium 137, if the half life of Cs137 is 8.83 x 10^8s.",
                "(a) 3.45 x 10^5βq",
                "(b) 2.20 x 10^5βq",
                "(c) 1.66 x 10^5β",
                "(d) 4.12 x 10^5βq",
                "(a) 3.45 x 10^5βq"));

        questionList.add(new Question("In an archaeological site a piece of bone is found to give a count rate of 15 counts " +
                "per minute. A similar sample of fresh bone gives a count rate of 19 counts " +
                "per minute. Calculate the age of the specimen.",
                "(a) 1789 yrs",
                "(b) 1566 yrs",
                "(c) 1897 yrs",
                "(d) 2011 yrs",
                "(c) 1897 yrs"));

        questionList.add(new Question("The three types of radiation from radioactive decay process are \n" +
                "(i) α, β and λ radiation (ii) α, β and γ radiation (iii) α, β and ν radiation",
                "(a) i only",
                "(b) i or iii",
                "(c) ii only",
                "(d) None of the above",
                "(c) ii only"));

        questionList.add(new Question("Determine the velocity of a de Broglie’s particle whose mass and wavelength are " +
                "0.15 kg and 1.49x10^-26 nm: {Planck’s constant = 6.7 x 10^-34 Js}",
                "(a) 43 m/s",
                "(b) 15 m/s",
                "(c) 35 m/s",
                "(d) 30 m/s.",
                "(d) 30 m/s."));

        questionList.add(new Question("One of following is an advantage of semiconductor diode over diode value? It is:",
                "(a) Smaller in size",
                "(b) Easier to produce",
                "(c) Cheaper to purchase",
                "(d) All are its advantages",
                "(d) All are its advantages"));

        questionList.add(new Question("A photon with frequency 1.76 x 10^23Hz released 9.1 x 10^-31 kg mass electron at " +
                "4.4 x 105 Hz threshold frequency. Calculate its speed.\n" +
                "(Planck’s constant h = 6.63 x 10^-34Js)",
                "(a) 3.34 x 10^23 m/s",
                "(b) 1.6 x 10^10 m/s",
                "(c) 10.4 x 10^23 m/s",
                "(d) 1.76 x 10^23 m/s",
                "(b) 1.6 x 10^10 m/s"));

        questionList.add(new Question("During photoelectric emission, if work function is the same with an incoming " +
                "photon in quantity, one of the following is correct: ",
                "(a) Photoelectrons may not be obtained",
                "(b) Photoelectrons may move slower",
                "(c) Photoelectrons may possess little kinetic energy",
                "(d) All above are wrong",
                "(d) All above are wrong"));

        questionList.add(new Question("One of the following is the effect of the reverse bias connection of a diode value at a very low voltage:",
                "(a) Damage the valve",
                "(b) Evacuate its tube",
                "(c) The diode stops conducting",
                "(d) Stratifies the tube",
                "(c) The diode stops conducting"));

        questionList.add(new Question("The process of ejecting electrons from the surface of acold metal by an " +
                "electromagnetic radiation is an evidence of:",
                "(a) X-radiation",
                "(b) particle nature of wave",
                "(c) Thermionic emission",
                "(d) Compton effect",
                "(b) particle nature of wave"));

        questionList.add(new Question("The household tube television works on the principle of:",
                "(a) Thermionic emission",
                "(b) Dispersion of white light",
                "(c) Photoemission",
                "(d) Polarization",
                "(a) Thermionic emission"));

        questionList.add(new Question("A beam of x-rays with the atomic spacing 10.72nm is incident on a crystal and " +
                "gives a first order maximum when the glancing angle is 8 degrees; find the wavelength of the beam",
                "(a) 0.2 nm",
                "(b) 10.72nm",
                "(c) 0.55nm",
                "(d) 0.09nm",
                "(a) 0.2 nm"));

        questionList.add(new Question("An electron of mass 9.1x10^-31 kg and charge 1.6x10^-19 c is accelerated to a target " +
                "by applying a potential difference of 25 kV, calculate its velocity at an instance.\n",
                "(a) 3.30x10^7m/s",
                "(b) 5.86x10^7 m/s",
                "(c) 9.38x10^7 m/s",
                "(d) 4.79x10^7 m/s",
                "(c) 9.38x10^7 m/s"));

        questionList.add(new Question("A beam of x-rays with the atomic spacing 10.72nm is incident on a crystal and " +
                "gives a first order maximum when the glancing angle is 8 degrees; find the wavelength of the beam",
                "(a) 0.2 nm",
                "(b) 10.72nm",
                "(c) 0.55nm",
                "(d) 0.09nm",
                "(a) 0.2 nm"));

        questionList.add(new Question("One of these laws is aimed at seeing how Particles could behave like waves",
                "(a) Bohr’s law",
                "(b) Rutherford’s law",
                "(c) de Broglie’s law",
                "(d) Ohm’s law",
                "(c) de Broglie’s law"));

        questionList.add(new Question("All of these are wrong about photoelectric emission except:",
                "(a) No emission if work function and photon energy are equal",
                "(b) Decrease in work function makes the photoelectrons to move faster",
                "(c) Excess energy of photon serves as kinetic energy for the photoelectrons to move",
                "(d) (a) and (b)",
                "(c) Excess energy of photon serves as kinetic energy for the photoelectrons to move"));

        questionList.add(new Question("X-rays can be suitable to study internal structure of a charged object because it is",
                "(a) Fast",
                "(b) Neutral",
                "(c) Positively Charged",
                "(d) Negatively Charged",
                "(b) Neutral"));

        questionList.add(new Question("X-rays have charge characteristics comparable with that of",
                "(a) Gamma rays",
                "(b) Beta rays",
                "(c) Proton",
                "(d) Alpha particles",
                "(a) Gamma rays"));

        questionList.add(new Question("The presence of space charge in a working diode valve without an accelerating potential causes:",
                "(a) Increase in current",
                "(b) Current to die down",
                "(c) Temperature reduction in the valve",
                "(d) Diode values to have short life span",
                "(b) Current to die down"));

        questionList.add(new Question("Which of these electronic components is most suitable for AC-DC rectification?",
                "(a) Transistor",
                "(b) Integrated circuit",
                "(c) Capacitor",
                "(d) Thermionic diode",
                "(d) Thermionic diode"));

        questionList.add(new Question("All of these are characteristics of ‘HARDER’ x-rays except",
                "(a) High speed",
                "(b) Short wavelength",
                "(c) High Kinetic energy",
                "(d) ability to penetrate lead materials",
                "(c) High Kinetic energy"));

        questionList.add(new Question("Only 5% of photon energy, with frequency 20 Hz, falling on a metal served as " +
                "work function to release an electron. Calculate the velocity of the photoelectron " +
                "released. (Electron mass = 9.1x10^-31 kg. Planck constant h = 6.63x10^-34 Js)",
                "(a) 0.17 m/s",
                "(b) 2.55 m/s",
                "(c) 8.07 m/s",
                "(d) 3.7x10^10 m/s",
                "(a) 0.17 m/s"));

        questionList.add(new Question("Thermionic emission is a principle for the production of",
                "(a) Gamma rays",
                "(b) Beta rays",
                "(c) X-rays",
                "(d) Cathode rays",
                "(c) X-rays"));

        questionList.add(new Question("‘Soft’ X-rays are most suitable in:",
                "(a) Analysing internal faults in auto maintenance workshops",
                "(b) Identifying alteration made on paper artistic works",
                "(c) Mapping the internal organ such as bone marrow in human body",
                "(d) Analysing the internal organ of metal machines",
                "(b) Identifying alteration made on paper artistic works"));

        questionList.add(new Question("Ability to measure accurately, the position and velocity of a particle at a certain " +
                "time, according to uncertainty principle, implies that it is:",
                "(a) possible to predict its state at any given future time",
                "(b) possible to predict its momentum and size",
                "(c) possible to predict its size only at a time",
                "(d) impossible to make a decision about it at any time",
                "(a) possible to predict its state at any given future time"));

        questionList.add(new Question("The Uranium nucleus 238U92 undergoes successive decays, emitting respectively an " +
                "α-particle, a β-particle and a γ-ray. What is the atomic number and the mass " +
                "number of the resulting nucleus?",
                "(a) 91 , 234",
                "(b) 90 , 236",
                "(c) 88 , 236",
                "(d) 92 , 234",
                "(a) 91 , 234"));

        questionList.add(new Question("The isotope which decays by β-emission to produce 111In49 is",
                "(a) 112Ag49",
                "(b) 11150Cd",
                "(c) 110Ag50",
                "(d) 113Sn50",
                "(a) 112Ag49"));

        questionList.add(new Question("A stationary thorium nucleus (A = 220, Z = 90) emits an α- particle of kinetic energy E. What is the kinetic energy of the daughter nucleus",
                "(a) E ",
                "(b) E/12 ",
                "(c) E/36 ",
                "(d) E/54 ",
                "(d) E/54 "));

        questionList.add(new Question("An approximate relationship between the radius R of a nucleus and its nucleon " +
                "number N is R/m = 1.2 x 10^-15 N^1/3 . Estimate the number of nucleons per unit " +
                "volume of the nucleus",
                "(a) 0.12 x 10^44 m^-5",
                "(b) 1,4 x 10^44 m^-3",
                "(c) 5.78 x 10^44 m^-3",
                "(d) 1.2 x 10^44 m^-5",
                "(c) 5.78 x 10^44 m^-3"));

        questionList.add(new Question("Which of the following gives the relationship between the decay constant λ and the " +
                "half life T of a radioactive isotope.",
                "(a) N = Noe^-λT",
                "(b) T = In 2λ",
                "(c) T = In 2/λ",
                "(d) T = λN",
                "(c) T = In 2/λ"));

        questionList.add(new Question("The isotope 23490Th has a half-life of 24 days and decays to 23491Pa . How long does " +
                "it take for 90% of a sample of 234Th90 to decay to 234Pa91",
                "(a) 50 days",
                "(b) 60 days",
                "(c) 70 days",
                "(d) 80 days",
                "(d) 80 days"));

        questionList.add(new Question("The decay of a radioactive nuclide is represented by the equation dN /dt = -λN " +
                "where λ = 2.4 x 10^-8 s^-1. What is the half life of the nuclide",
                "(a) 2.9 x 10^7 s",
                "(b) 8.33 x 10^7 s",
                "(c) 1.25 x 10^7 s",
                "(d) 1.25 x 10^-7 s",
                "(a) 2.9 x 10^7 s"));

        questionList.add(new Question("If the fission of a atom of 235U yields an energy of 200 MeV. How much energy " +
                "would be released by the fission of 1g of 235U",
                "(a) 8.20 x 10^10J ",
                "(b) 8.20 J ",
                "(c) 8.20 x 10^-10J",
                "(d) 8.20 x 10^-6J",
                "(a) 8.20 x 10^10J "));

        questionList.add(new Question("The mass of a 20 Ne 10 nuclide is 19.99244amu. If the rest mass of a proton and a " +
                "neutron are 1.007825amu and 1.008665amu respectively, calculate the nuclear " +
                "binding energy and hence the nuclear binding energy per nucleon of 20" +
                "Ne10",
                "(a) 2.9 x 10^-9 J, 1.29 x 10^-12J",
                "(b) 2.58 x 10^-11J, 1.29 x 10^-12J",
                "(c) -2.97 x 10^-9 J, 1.29 x 10^-12J",
                "(d) None the above",
                "(b) 2.58 x 10^-11J, 1.29 x 10^-12J"));

        questionList.add(new Question("The results of the Geiger and Muller experiment proves that",
                "(a) Electrons are present in the atoms",
                "(b) Electrons move randomly in atoms",
                "(c) There is a central nucleus",
                "(d) There are protons and electrons in an atom",
                "(c) There is a central nucleus"));

        questionList.add(new Question("The length of an α-particle track in a cloud chamber is 37mm. If the average " +
                "energy required to produce an ion pair is 5.2 x 10^-18J and on the average an α-particle produces 5.0 x 10^3 ion pairs per mm of its track, calculate the initial \n" +
                "energy of the α-particle",
                "(a) 6.01 eV",
                "(b) 0.611MeV",
                "(c) 6.01 MeV",
                "(d) 0.006 MeV",
                "(c) 6.01 MeV"));

        questionList.add(new Question("The splitting of a large nucleus into smaller nuclei is referred to as",
                "(a) Fusion",
                "(b) Radioactivity",
                "(c) Fission",
                "(d) Decay",
                "(c) Fission"));

        questionList.add(new Question("In a radioactive decay reaction , the number of radioactive atoms",
                "(a) Decreases sinusoidally with times",
                "(b) Increases exponentially with time",
                "(c) Decreases hypothetically with times",
                "(d) Decreases exponentially with time",
                "(d) Decreases exponentially with time"));

        questionList.add(new Question("Which of the following gives the relationship between the nuclear binding energy " +
                "ΔE and mass defect Δm of a nucleus",
                "(a) ΔE = hv",
                "(b) ΔE = hc",
                "(c) ΔE = Δmc",
                "(d) ΔE = Δmc^2",
                "(d) ΔE = Δmc^2"));

        questionList.add(new Question("A uranium nucleus 238U92 , emits two alpha particles and two beta particles and " +
                "finally forms thorium (Th) nucleus. What is the symbol of this nucleus",
                "(a) 230Th92",
                "(b) 230Th90",
                "(c) 234Th90",
                "(d) 233Th89",
                "(b) 230Th90"));

        questionList.add(new Question("The half-life of radium is 10 days . After how many days will only one-sixteenth " +
                "of radium sample remain.",
                "(a) 30",
                "(b) 45",
                "(c) 40",
                "(d) 50",
                "(c) 40"));
    }

    private void getQuestionPhase4(List<Question> list) {

        questionList.add(new Question("How much 235U92 must undergo fission per day in a nuclear reactor that provides " +
                "energy to a 100MW electric power plant .Assume perfect efficiency. Given that 1 " +
                "kg of 235U92 can generate 9 x 10^13 J of energy",
                "(a) 9.6 x 10^-2 kg/day",
                "(b) 7.6 x 10^-2 kg/day",
                "(c) 3.4 x 10^-2 kg/day ",
                "(d) 6.8 x 10^-2 kg/day",
                "(a) 9.6 x 10^-2 kg/day"));

        questionList.add(new Question("Calculate the binding energy of 57Fe26 whose mass is 56.935398 a.m. u given that " +
                "the mass of protons = 1.007825 a. m.u and the mass of neutron = 1.008665 a.m.u . " +
                "1 a.m.u = 931 MeV",
                "(a) 250eV",
                "(b) 300 eV",
                "(c) 400 eV",
                "(d) 500eV",
                "(d) 500eV"));

        questionList.add(new Question("A sample of a radioactive isotope is left to decay . After 1 minute, only 1/8 of the " +
                "isotope remains in the sample. Calculate the decay constant.",
                "(a) 0.0235",
                "(b) 0.0421",
                "(c) 0.0213",
                "(d) 0.0347",
                "(d) 0.0347"));

        questionList.add(new Question("Which of the following is a common characteristic among α-particle and γ-rays.",
                "(a) They are e-m radiation of short wavelengths",
                "(b) They are deflected by electric fields",
                "(c) They cause some substance to fluoresce ",
                "(d) They have strong penetrating power",
                "(c) They cause some substance to fluoresce "));

        questionList.add(new Question("Electrons are emitted with negligible speed from a plane cathode in an evacuated " +
                "tube. The electrons are accelerated toward a plane anode which is parallel to the " +
                "cathode and 2.0cm from it by a p.d of 100V. Find the time taken for an electron to " +
                "move from the cathode to the anode (e/m = 1.8 x 10^11C/kg).",
                "(a) 4.5 x 10^-5 s",
                "(b) 1.8 x 10^-8 s",
                "(c) 2.11 x 10^-9 s",
                "(d) 1.90 x 10^-7 s",
                "(c) 2.11 x 10^-9 s"));

        questionList.add(new Question("Electrons are emitted with negligible speed from a plane cathode in an evacuated " +
                "tube. The electrons are accelerated toward a plane anode which is parallel to the " +
                "cathode and 2.0cm from it by a p.d of 100V. Find the time taken for an electron to " +
                "move from the cathode to the anode (e/m = 1.8 x 10^11C/kg).",
                "(a) 4.5 x 10^-5 s",
                "(b) 1.8 x 10^-8 s",
                "(c) 2.11 x 10^-9 s",
                "(d) 1.90 x 10^-7 s",
                "(c) 2.11 x 10^-9 s"));

        questionList.add(new Question("Find the energy difference and the wavelength of the photon which is emitted " +
                "when a hydrogen atom undergoes a transition from n=5 to n=2.",
                "(a) 2.856eV, 6.96 x 10^-26 m",
                "(b) 0.2856eV, 6.343 x10^-7 m",
                "(c) 2.856eV, 4.343 x10^-7 m",
                "(d) 0.2856eV, 4.343 x10^-10 m",
                "(a) 2.856eV, 6.96 x 10^-26 m"));

        questionList.add(new Question("Proton with a charge-mass ratio of 1.0 x 10^8 C/kg are rotated in a circular orbit of " +
                "radius r when they enter a uniform magnetic field of 0.5nT. Calculate the number " +
                "of revolution",
                "a. 2 x 10^6 Hz",
                "b. 4 x 10^6 Hz",
                "c. 6 x 10^6 Hz",
                "d. 8 x10^6 Hz",
                "b. 4 x 10^6 Hz"));

        questionList.add(new Question("What is the maximum kinetic energy of electrons emitted by light of wavelength " +
                "0.8nm from a surface which has a threshold wavelength of 0.96nm?",
                "(a) -3.168 x 10^-26J",
                "(b) 3.168 x 10^-26J",
                "(c) 1.98 x 10^-25J",
                "(d) 2.178 x 10^-26J",
                "(d) 2.178 x 10^-26J"));

        questionList.add(new Question("X-rays of wavelength 1.5 x10^-10m is incident on a crystal and it gives a third order " +
                "diffraction for a glancing angle of 600. What is the separation of the layers of " +
                "atoms in the crystal?",
                "(a) 2.46 x 10^-10m",
                "(b) 3.87 x 10^8m",
                "(c) 7.38 x 10^-10m",
                "(d) 4.5 x 10^-10m",
                "(c) 7.38 x 10^-10m"));

        questionList.add(new Question("The potential difference between the target and cathode of an x-ray tube is 20KV " +
                "and the current is 20mA. Only 0.5% of the total energy supplied is emitted as X-rays. What is the minimum wavelength of the x-ray emitted?",
                "(a) 6.19 x 10^-11 m",
                "(b) 3.87 x 10^8 m",
                "(c) 6.19 x 10^-8 m",
                "(d) 4.00 x 10^7 m",
                "(a) 6.19 x 10^-11 m"));

        questionList.add(new Question("Calculate the velocity of electrons accelerated from rest to a target in hot cathode " +
                "vacuum tube by a p.d of 25V",
                "(a) 2.96 x 10^6m/s",
                "(b) 3.98 x 10^8m/s",
                "(c) 2.98 x 10^3m/s",
                "(d) 3.98 x 10^3m/s",
                "(a) 2.96 x 10^6m/s"));

        questionList.add(new Question("Calculate the energy and momentum of a photon of light of wavelength 500nm",
                "(a) 3.96 x10^-19J, 1.32 x 10^-27kgm/s",
                "(b) 3.3 x 10^-31J, 1.32 x 10^-27kgm/s",
                "(c) 3.3 x 10^-40J, 1.32 x 10^-27kgm/s",
                "(d) a, b or c",
                "(a) 3.96 x10^-19J, 1.32 x 10^-27kgm/s"));

        questionList.add(new Question("An alpha particle of energy 5.30 MeV moves directly toward a lead nucleus 206Pb82" +
                "which is stationary. Calculate the nearest distance of approach of the alpha " +
                "particle from the least nucleus",
                "(a) 3.128 x 10^-7m/s",
                "(b) 3.75 x 10^-26m/s",
                "(c) 3.98 x 10^-26m/s",
                "(d) 4.425 x 10^-14m/s",
                "(d) 4.425 x 10^-14m/s"));

        questionList.add(new Question("In the production of x-rays most modern x-ray tubes use tungsten for the target because",
                "(a) they are good targets",
                "(b) they are not costly",
                "(c) they have the highest efficiency for x-ray production",
                "(d) they serve as the best cooling agent",
                "(c) they have the highest efficiency for x-ray production"));

        questionList.add(new Question("What is the shortest wavelength in the Lyman series of hydrogen?",
                "(a) 1.097 x 10^7m",
                "(b) 9.1 x 10^-8m",
                "(c) 2.7 x 10^6m",
                "(d) 3.65 x 10^-7m",
                "(d) 3.65 x 10^-7m"));

        questionList.add(new Question("The length of an alpha particle track in a cloud chamber is 37mm. if the average " +
                "energy required to produce an ion pair is 5.2 x 10^18J and on an average an alpha " +
                "particle produce 5 x 10^3 ion pairs per mm of its track, calculate the initial energy " +
                "of the particle",
                "(a) 6.01ev",
                "(b) 0.611Mev",
                "(c) 6.01Mev",
                "(d) 0.006Mev",
                "(c) 6.01Mev"));

        questionList.add(new Question("If the wavelength of the incident light in a photoelectric experiment is increased " +
                "from 30007nm to 30010, calculate the corresponding change in the stopping " +
                "potential",
                "(a) 1.38 x 10^-2V",
                "(b) 1.38 x 10^-8V",
                "(c) 1.38 x 10^-4V",
                "(d) 1.38 x 10^-3V",
                "(b) 1.38 x 10^-8V"));

        questionList.add(new Question("Calculate the velocity of electrons accelerated from rest to a target in hot cathode " +
                "vacuum tube by a potential difference of 25V",
                "(a) 2.98 x 10^8m/s",
                "(b) 3.75 x 10^8m/s",
                "(c) 2.98 x 10^3m/s",
                "(d) 3.98 x10^3m/s",
                "(c) 2.98 x 10^3m/s"));

        questionList.add(new Question("What is the wavelength of the Balmer series for n = 4?",
                "(a) 4.86 x 10^-7m",
                "(b) 9.7 x 10^-8m",
                "(c) 1.88 x 10^-6m",
                "(d) 2.0 x 10^-7 m",
                "(a) 4.86 x 10^-7m"));

        questionList.add(new Question("Electrons are knocked off the cathode at low pressures and low p.d in a discharge tube through",
                "(a) Electrical process",
                "(b) Thermionic emission",
                "(c) Photoelectric effect",
                "(d) Explosion process",
                "(c) Photoelectric effect"));

        questionList.add(new Question("The point in a discharge tube at which the growth of electrons become " +
                "uncontrollable at high voltage is known as",
                "(a) Uncontrollable point",
                "(b) Avalanche point",
                "(c) Gas breakdown point",
                "(d) Cathode dark point",
                "(c) Gas breakdown point"));

        questionList.add(new Question("One disadvantage of the method of discharge tube for the production of cathode rays is",
                "(a) cathode ray produced is usually small",
                "(b) production of x-rays as by-product",
                "(c) very low p.d is required",
                "(d) no gas is required",
                "(b) production of x-rays as by-product"));

        questionList.add(new Question("In the modern method of cathode ray production, cathode rays are produced by",
                "(a) chemical method",
                "(b) thermionic method",
                "(c) photoelectric effect",
                "(d) small voltage",
                "(c) photoelectric effect"));

        questionList.add(new Question("Which of the following is common to both the discharge tube method and the modern method of producing cathode rays",
                "(a) they both require gas",
                "(b) they both require metals",
                "(c) small electrons produced must be accelerated",
                "(d) they produce x-rays as by-product",
                "(c) small electrons produced must be accelerated"));

        questionList.add(new Question("An application of cathode ray is in",
                "(a) x-ray production",
                "(b) gamma ray production",
                "(c) cathode ray oscilloscope",
                "(d) production of alpha particle",
                "(c) cathode ray oscilloscope"));

        questionList.add(new Question("In Milikan experiment, an atomizer is used to",
                "(a) produce tiny charged droplet of oil",
                "(b) atomize the environment of the oil",
                "(c) produce a high p.d in the oil",
                "(d) keep the oil motionless",
                "(a) produce tiny charged droplet of oil"));

        questionList.add(new Question("According to Milikan, the charge on an oil drop is given by",
                "(a) q = E/d",
                "(b) q = I/t",
                "(c) q = mgd/V",
                "(d) q = V/d",
                "(c) q = mgd/V"));

        questionList.add(new Question("The ionization energy for hydrogen atom is ",
                "(a) -13.6eV",
                "(b) +13.6eVe",
                "(c) -10.2eV",
                "(d) +10.2eV",
                "(b) +13.6eVe"));

        questionList.add(new Question("What is the maximum kinetic energy of electrons emitted by light of wavelength " +
                "0.8nm from a surface which has a threshold wavelength of 0.96nm?",
                "(a) -3.168 x 10^-26J",
                "(b) 3.168 x 10^-26J",
                "(c) 1.98 x 10^-25J",
                "(d) 2.178 x 10^-26J",
                "(d) 2.178 x 10^-26J"));

        questionList.add(new Question("If ΔE, Δx and Δp are the smallest uncertainty measurements within the smallest " +
                "uncertainty time Δt, in energy, position and momentum the uncertainty principle " +
                "can be stated as",
                "(a) ΔE. Δx = h/2π",
                "(b) Δp. ΔE = h/2π",
                "(c) Δp. Δx = h/2π",
                "(d) Δt. Δx = h/2π",
                "(c) Δp. Δx = h/2π"));

        questionList.add(new Question("All of these are instances when an entity behaves like a particle:",
                "(a) Compton effect and diffraction",
                "(b) Photoelectric effect and refraction",
                "(c) X-Ray production and interference",
                "(d) Photoemission and Compton effect",
                "(d) Photoemission and Compton effect"));

        questionList.add(new Question("All of these are instances when an entity behaves like a particle:",
                "(a) Compton effect and diffraction",
                "(b) Photoelectric effect and refraction",
                "(c) X-Ray production and interference",
                "(d) Photoemission and Compton effect",
                "(d) Photoemission and Compton effect"));

        questionList.add(new Question("A Photon of 1.6 x 10^-19 J did a work of 0.3 x 10^-19 J to free an electron whose " +
                "mass is 9.1 x 10^-31 Kg. Calculate the velocity of the electron after been released",
                "(a) 5.3 x10^5 m/s",
                "(b) 60.1 x 10^5 m/s",
                "(c) 9.2 x 10^5 m/s",
                "(d) 1.12.x 10^5 m/s",
                "(a) 5.3 x10^5 m/s"));

        questionList.add(new Question("One of the following is not a proper use of X-rays:",
                "(a) Crystallographic study",
                "(b) Identification of alteration made on artistic works",
                "(c) Mapping the internal organ such as bone marrow in human body",
                "(d) To analyze the internal organ of metal machines",
                "(c) Mapping the internal organ such as bone marrow in human body"));

        questionList.add(new Question("Current dies down in a working diode value, without an accelerating potential, because",
                "(a) Anode is shielded",
                "(b) Of the presence of space charge",
                "(c) Temperature of the cathode reduces",
                "(d) Diode values usually have short life span",
                "(b) Of the presence of space charge"));

        questionList.add(new Question("Which of these statements is not true of x-rays? They:",
                "(a) belong to electromagnetic spectrum",
                "(b) appear neutral",
                "(c) can be made faster or slower at will",
                "(d) originate from energy changes in the nuclei of atoms",
                "(d) originate from energy changes in the nuclei of atoms"));

        questionList.add(new Question("If x-rays are brought near the top cap of a positively charged gold leaf " +
                "electroscope, the divergence of the leaves will:",
                "(a) decrease to zero slowly",
                "(b) steadily increase",
                "(c) remain constant",
                "(d) decrease to zero and then increase to maximum",
                "(b) steadily increase"));

        questionList.add(new Question("Bohr confirmed that the motion of electron towards nucleus of Rutherford atomic model is",
                "(a) Helical",
                "(b) Zigzag",
                "(c) Spiral",
                "(d) circular",
                "(c) Spiral"));

        questionList.add(new Question("De Broglie’s law is aimed at seeing how:",
                "(a) Particles could behave like waves",
                "(b) X-rays can be made ‘HARDER’",
                "(c) Waves can exhibit particle nature",
                "(d) X-rays can be made ‘SOFTER’",
                "(a) Particles could behave like waves"));

        questionList.add(new Question("Production of x-rays is sourced from the principle of",
                "(a) Photoelectric effect",
                "(b) Thermionic emission",
                "(c) Photovotaic emission",
                "(d) Comption effect",
                "(b) Thermionic emission"));

        questionList.add(new Question("Which of these is wrong about ‘HARD’ x-rays? They have:",
                "(a) High speed",
                "(b) Short wavelength",
                "(c) High Kinetic energy",
                "(d) Ability to penetrate plane paper only",
                "(d) Ability to penetrate plane paper only"));

        questionList.add(new Question("A beam of x-rays of wavelength 0.2 nm is incident on a crystal and gives a first " +
                "order maximum when the glancing angle is 80; find the atomic spacing in the crystal.",
                "(a) 0.90nm",
                "(b) 10.72nm",
                "(c) 0.55nm",
                "(d) 0.09nm",
                "(b) 10.72nm"));

        questionList.add(new Question("Diode value works on the principle of:",
                "(a) Photo emission",
                "(b) Compton effect",
                "(c) Thermionic emission",
                "(d) X-Ray emission",
                "(c) Thermionic emission"));

        questionList.add(new Question("The reverse bias connection of a diode value at a very high voltage can",
                "(a) Damage the value",
                "(b) Lead to the production of a stabilizing device",
                "(c) Evacuate its tube",
                "(d) Stratify the tube",
                "(d) Stratify the tube"));

        questionList.add(new Question("A 9.1 x 10^-3kg mass electron was released by a radiation to move with a speed of " +
                "1.6 x 10^10 m/s. If the threshold frequency is 4.4 x 10^5 Hz calculate the " +
                "frequency of the source radiation. (Planck’s constant h = 6.63 x 10^-34Js)",
                "(a) 3.34 x 10^23Hz",
                "(b) 5.11 x 10^23Hz",
                "(c) 10.4 x 10^23Hz",
                "(d) 1.76 x 10^23Hz",
                "(d) 1.76 x 10^23Hz"));

        questionList.add(new Question("Which of the following is an advantage of diode value over semiconductor diode? It is",
                "(a) made of glasses",
                "(b) usually smaller",
                "(c) easier to make",
                "(d) cheaper",
                "(c) easier to make"));

        questionList.add(new Question("Which of the following is an advantage of diode value over semiconductor diode? It is",
                "(a) made of glasses",
                "(b) usually smaller",
                "(c) easier to make",
                "(d) cheaper",
                "(c) easier to make"));

        questionList.add(new Question("During photoelectric emission, if work function of a metal is extremely high then",
                "(a) Photoelectrons may not be obtained",
                "(b) Photoelectrons may move slower",
                "(c) Photoelectrons may possess little kinetic energy",
                "(d) All above are possible outcome.",
                "(d) All above are possible outcome."));

        questionList.add(new Question("LASER principle is based on stimulated emission. Which of the following is correct about light pulse from LASER?",
                "(a) They are coherent",
                "(b) They are incoherent",
                "(c) They are non-monochromatic",
                "(d) all of the above",
                "(a) They are coherent"));

        questionList.add(new Question("Which of the following is a Bohr postulate?",
                "(a) electrons are spread about in the with the proton as a paste",
                "(b) electrons resolve round the nucleus in a random manner",
                "(c) electrons occupy allowed energy levels",
                "(d) electrons are located in the central core of the atom",
                "(c) electrons occupy allowed energy levels"));

        questionList.add(new Question("The total energy of the Bohr atom is given by",
                "(a) – k2e^2/r",
                " (b) – kze^2/r^2",
                "(c) - kze^2/2r",
                " (d) + kze^2/2r",
                "(c) - kze^2/2r"));

        questionList.add(new Question("Bohr proved electrons as a particle while Schroedinger proved it as a wave. The " +
                "two ideas can be combined and represented as: ",
                "(a) Angular momentum =h/mv",
                "(b) Angular momentum =nh/2π",
                "(c) Angular momentum = nλ/2π",
                "(d) Angular momentum = 2πr/n",
                "(b) Angular momentum =nh/2π"));

        questionList.add(new Question("Calculate the wavelength of the electron-wave for electrons fixed round an orbit " +
                "whose diameter is 1.2 nm if 24 complete waves are formed round the orbit:",
                "(a) 1.6 x 10^-10 m",
                "(b) 3.4 x 10^-12 m",
                "(c) 4.3 x 10^-10 m",
                "(d) 9.6 x 10^-12 m",
                "(a) 1.6 x 10^-10 m"));
    }

    private void getQuestionPhase5(List<Question> list) {

        questionList.add(new Question("One of the following is correct, when x-rays fall on a plane of atoms in a crystal",
                "(a) The beam of the x-rays is reflected",
                "(b) The beam of the x-rays is refracted",
                "(c) The beam of the x-rays is diffracted",
                "(d) The beam is absorbed",
                "(c) The beam of the x-rays is diffracted"));

        questionList.add(new Question("One of the following is correct, when x-rays fall on a plane of atoms in a crystal",
                "(a) The beam of the x-rays is reflected",
                "(b) The beam of the x-rays is refracted",
                "(c) The beam of the x-rays is diffracted",
                "(d) The beam is absorbed",
                "(c) The beam of the x-rays is diffracted"));

        questionList.add(new Question("Reflected beams from surfaces of atomic planes have maximum intensity if the " +
                "path difference between the incidents x-rays is",
                "(a) A half number of wavelengths of the rays",
                "(b) One third number of wavelengths of the rays",
                "(c) A whole number of wavelength of the rays",
                "(d) One fifth number of wavelengths of the rays",
                "(c) A whole number of wavelength of the rays"));

        questionList.add(new Question("In x-rays production, a characteristics line spectra is obtained whose frequency is related to",
                "(a) The number of electrons of the target metal used",
                "(b) The number of protons of the target metal used",
                "(c) The number of neutrons of the target metal used",
                "(d) The summation of the total number of protons and neutrons of the target metal used",
                "(c) The number of neutrons of the target metal used"));

        questionList.add(new Question("What is the ratio of the reminant intensity of x-rays after penetration to initial " +
                "intensity when it penetrates to a depth of 2cm into a patient body",
                "(a) 14:15",
                "(b) 34:35",
                "(c) 20:21",
                "(d) 24:25",
                "(d) 24:25"));

        questionList.add(new Question("One of the following is correct about the working of the diode valve",
                "(a) Electrons are released when the cathode is heated",
                "(b) Electrons are released when the anode is heated",
                "(c) The accelerating voltage is always applied to the cathode",
                "(d) Electrons always drifted towards the cathode",
                "(a) Electrons are released when the cathode is heated"));

        questionList.add(new Question("To increase the intensity of x-rays, one of the following is TRUE",
                "(a) Cathode temperature is reduced",
                "(b) Anode temperature is increased",
                "(c) Cathode temperature is increased",
                "(d) Anode temperature is reduced",
                "(c) Cathode temperature is increased"));

        questionList.add(new Question("By increasing the penetrating power of x-rays, one of the following is NOT correct",
                "(a) The wavelength of the x-rays is shor",
                "(b) The voltage applied must be low",
                "(c) The speed of the x-rays is high",
                "(d) X-rays kinetic energy increases",
                "(b) The voltage applied must be low"));

        questionList.add(new Question("If 3.6x10^-19J energy of the photon from a radiation is required to emit electrons " +
                "from a metal surface, what is the wavelength of the source radiation?",
                "(a) 440nm",
                "(b) 584nm",
                "(c) 380nm",
                "(d) 553nm",
                "(d) 553nm"));

        questionList.add(new Question("If the wavelength of an incident photon radiation is 220nm. What is the energy " +
                "required by it to release an electron from a metal surface? Take h =6.63x10^-34Js, " +
                "C=3.0x10^8 ms-1",
                "(a) 9.0x10^-19 J",
                "(b) 8.3x10^-19 J",
                "(c) 9.8x10^-18 J",
                "(d) 6.73x10-19 J",
                "(a) 9.0x10^-19 J"));

        questionList.add(new Question("When 6.73x10^-19J of photon incident on a metal surface, the electron acquired a " +
                "lane the energy of 2.6x10^-19J after their emission. What is the there hold" +
                "wavelength of the incident radiation ? Take h=6.63x10^-34 J, C=3.0x10^8 m/s. ",
                "(a) 550nm",
                "(b) 510nm",
                "(c) 423nm",
                "(d) 380nm",
                "(c) 423nm"));

        questionList.add(new Question("5.4x10^-19 J energy of photon in an incident radiation falls on a metal to emit " +
                "electrons. If the threshold wavelength of the photon is 720 nm, what is the velocity " +
                "of the photoelectron electrons after that emission?",
                "(a) 6.63x10^4 m/s",
                "(b) 7.61x10^5m/s",
                "(c) 5.42x10^3m/s",
                "(d) 4.62x10^4m/s ",
                "(b) 7.61x10^5m/s"));

        questionList.add(new Question("When 4.34x10^-19 J of energy of photon in an incident radiation falls on a metal " +
                "surface, it emits electrons that move with the kinetic energy of 2.02x10^-19 J, what is " +
                "the frequency of the radiation below which emission of electrons will not occur " +
                "Take h=6.63x10^-34Js ",
                "(a) 6.2 x 10^15 Hz",
                "(b) 3.26 x 10^12 Hz",
                "(c) 2.6 x 10^14 Hz",
                "(d) 3.50 x 10^14 Hz",
                "(d) 3.50 x 10^14 Hz"));

        questionList.add(new Question("Energy and momentum are properties of a particle when it behaves as",
                "(a) Light",
                "(b) Particle",
                "(c) Wave",
                "(d) Momentum",
                "(b) Particle"));

        questionList.add(new Question("Wavelength and frequency are properties of a particle when it behaves as: ",
                "(a) An energy",
                "(b) A light",
                "(c) A wave",
                "(d) A momentum",
                "(c) A wave"));

        questionList.add(new Question("A particle is said to have wave nature when it possesses",
                "(a) Wavelength",
                "(b) Frequency",
                "(c) Momentum",
                "(d) (a) and (b) are correct",
                "(d) (a) and (b) are correct"));

        questionList.add(new Question("One of the following is NOT correct during the production of x-rays:",
                "(a) Fast moving electrons are brought to rest by a metal target",
                "(b) The electrons’ energy is in a direct relation with the frequency of the x-rays Produced",
                "(c) The electrons’ energy is inversely related to the frequency of the x-rays Produced",
                "(d) On hitting the metal target, electrons lose some of their kinetic energy",
                "(c) The electrons’ energy is inversely related to the frequency of the x-rays Produced"));

        questionList.add(new Question("What is the wave length of the x-rays produced when 25 kV potential is applied " +
                "between the cathode and anode of its tube 0.05nm where produced?{Planck’s " +
                "constant h = 6.6 x 10^-34 Js , X-ray velocity = 3 x 10^8 m/s , electron charge = 1.6 x 10^-19 C.}",
                "(a) 16 nm",
                "(b) 0.05nm",
                "(c) 2.5 nm",
                "(d) 9.9 nm",
                "(b) 0.05nm"));

        questionList.add(new Question("In which of all these situations does an entity behaves like a particle?",
                "(a) Compton effect and diffraction",
                "(b) Photoelectric effect and refraction",
                "(c) X-Ray production and interference",
                "(d) Not in all cases above",
                "(d) Not in all cases above"));

        questionList.add(new Question("Which of the following is an inadequate use of ‘SOFT’ X-rays?",
                "(a) To analyze the internal organ of thick metal machines",
                "(b) Identification of alteration made on artistic paper works",
                "(c) Mapping the external structures of human bone in his body",
                "(d) Crystallographic study",
                "(a) To analyze the internal organ of thick metal machines"));

        questionList.add(new Question("Determine the uncertain time Δt within which application of a small force Δf " +
                "0.0012 N moved a small distance Δs of 5.5x10^-19 m. (h=6.63x10^-34 Js)",
                "(a) 2.3x10^-13s",
                "(b) 7.3 x10^-13s",
                "(c) 1.6x10^-13s",
                "(d) 4.7x10^-13s",
                "(c) 1.6x10^-13s"));

        questionList.add(new Question("The mass defect ∆m of a helium nuclide 4" +
                "He2 is 0.0503 x 10^-27 Kg. Find the mass of the nucleus",
                "(a) 4.7121 x 10^-27 Kg",
                "(b) 5.7256 x 10^-27 Kg",
                "(c) 6.6447 x 10^-27 Kg",
                "(d) 3.7123 x 10^-27 Kg",
                "(c) 6.6447 x 10^-27 Kg"));

        questionList.add(new Question("Suppose initially 3.0 x 10^7 " +
                "radon atoms were trapped before radioactive decay and 1.1 x 10^5" +
                "radon atoms remain after 31 days . Calculate the half life of the nuclide",
                "(a) 4.73 days",
                "(b) 3.83 days",
                "(c) 2.54 days",
                "(d) 7.20 days",
                "(b) 3.83 days"));

        questionList.add(new Question("The wavelength of the gamma-ray emitted by radium 226Ra88 is 6.68 x 10^-12m. " +
                "Obtain the energy released in the process",
                "(a) 0.186MeV",
                "(b) 0.204MeV",
                "(c) 0.300 MeV",
                "(d) 0.410 MeV",
                "(a) 0.186MeV"));

        questionList.add(new Question("Uranium 238" +
                "U92 is converted to thorium 234Th90 by α – decay . If the wavelength of " +
                " The gamma ray emitted in the process is 2.51 x 10^-11m. Calculate the energy of the Gamma ray",
                "(a) 0.0126 MeV",
                "(b) 0.0276 MeV",
                "(c) 0.03624 MeV",
                "(d) 0.0496 MeV",
                "(d) 0.0496 MeV"));

        questionList.add(new Question("Suppose initially 3.0 x 10^7" +
                "radon atoms were trapped before radioactive decay and " +
                " 1.1 x 10^5 radon atoms remain after 31 days . Calculate the half life of the nuclide.",
                "(a) 4.73 days",
                "(b) 3.83 days",
                "(c) 2.54 days",
                "(d) 7.20 days",
                "(b) 3.83 days"));

        questionList.add(new Question("The wavelength of the gamma-ray emitted by radium 226Ra88 is 6.68 x 10^-12m." +
                " Obtain the energy released in the process.",
                "(a) 0.186MeV",
                "(b) 0.204MeV",
                "(c) 0.300 MeV",
                "(d) 0.410 MeV",
                "(a) 0.186MeV"));

        questionList.add(new Question("Isotopes are nuclei that contain the same number of ",
                "a. Neutrons but different number of protons",
                "b. Protons but different number of neutrons",
                "c. Nucleons",
                "d. Nucleons but different atomic number",
                "b. Protons but different number of neutrons"));

        questionList.add(new Question("Atomic mass number is",
                "a. The sum of the number of protons and neutrons",
                "b. The square root of the number of proton",
                "c. Twice the number of nucleons",
                "d. Half the number of nucleons",
                "a. The sum of the number of protons and neutrons"));

        questionList.add(new Question("Radioactivity is the",
                "a. Splitting of the stable nucleus",
                "b. Integration of unstable nucleus",
                "c. Disintegration of unstable nucleus",
                "d. Amalgamation of unstable nucleus",
                "c. Disintegration of unstable nucleus"));

        questionList.add(new Question("Binding energy of a nucleus is the energy required to",
                "a. Fuse the electron and proton together",
                "b. Break the protons in a nucleus",
                "c. Unite the proton and neutron",
                "d. Separate the proton and neutron",
                "d. Separate the proton and neutron"));

        questionList.add(new Question("Isotones are nuclei having the",
                "a. Same number of electrons and different number of neutron",
                "b. Same number of neutron but different atomic number",
                "c. Same atomic number but different number of neutrons",
                "d. Number of nucleons as twice that of electron",
                "b. Same number of neutron but different atomic number"));

        questionList.add(new Question("Isobars are nuclei having",
                "a. Same total number of nucleons but different atomic number",
                "b. Same atomic number but different number of nucleons",
                "c. Different number of neutron but same number of electrons",
                "d. Same number of protons and electrons",
                "a. Same total number of nucleons but different atomic number"));

        questionList.add(new Question("Given that the nuclear radius of a nucleus is 2.22 x 10^-15m. Calculate the mass Fermi constant is Ro =1.4 x 10^-15m",
                "a. 2.0",
                "b. 5.0",
                "c. 3.0",
                "d. 4.0",
                "d. 4.0"));

        questionList.add(new Question("Using Einstein’s formula , compute the mass equivalence in kilogramme of the Energy 933.7 MeV",
                "(a) 1.66 x 10^-27Kg",
                "(b) 2.01 x 10^-27Kg",
                "(c) 0.265 x 10^-27Kg",
                "(d) 3.201 x 10^-27Kg",
                "(a) 1.66 x 10^-27Kg"));

        questionList.add(new Question("A radioactive nuclei have a half life of 60 days. After how many days will the " +
                "Material reduce to a quarter of the original",
                "(a) 100 days",
                "(b) 120 days",
                "(c) 136 days",
                "(d) 105 days",
                "(b) 120 days"));

        questionList.add(new Question("The graphical representation of a radioactive material decay is",
                "(a) Parabolic",
                "(b) Linear",
                "(c) Exponential",
                "(d) Circular",
                "(c) Exponential"));

        questionList.add(new Question("The following electromagnetic waves are arranged in the order of increasing wavelength",
                "(a) γ-rays, Radio waves, micro-wave, x-rays",
                "(b) γ-rays, x-rays , micro-wave, Radio waves",
                "(c) Radio waves, Micro-wave, γ-rays, x-rays",
                "(d) Micro-wave, Radio wave, x-rays, γ-rays",
                "(b) γ-rays, x-rays , micro-wave, Radio waves"));

        questionList.add(new Question("One of the following is not true of electromagnetic waves",
                "(a) It exhibits interference and diffraction",
                "(b) it travels in straight line in vacuum",
                "(c) It is unaffected by electric and magnetic field",
                "(d) it is a longitudinal wave",
                "(c) It is unaffected by electric and magnetic field"));

        questionList.add(new Question("One of these is useful in communication and produced by electrical oscillation",
                "(a) Visible radiation",
                "(b) X-rays",
                "(c) Radio waves",
                "(d) Micro-wave",
                "(c) Radio waves"));

        questionList.add(new Question("One of this is true of gamma-rays",
                "(a) It is positively charged",
                "(b) it is uncharged",
                "(c) It is negatively charged",
                "(d) It has changing charge",
                "(b) it is uncharged"));

        questionList.add(new Question("A system in which the fissionable and non fissionable materials are arranged so " +
                "that the fission chain reaction can proceed in a controlled manner is called",
                "(a) Nuclear bomb",
                "(b) Magnetron",
                "(c) Geiger tube",
                "(d) Nuclear reactor",
                "(d) Nuclear reactor"));

        questionList.add(new Question("A radioactive Isotope 111" +
                "X50 will decay by β-emission to give one of the following",
                "(a) 111Y51",
                "(b) 110Y51",
                "(c) 112Y50",
                "(d) 110Y50",
                "(a) 111Y51"));

        questionList.add(new Question("Two properties of fission that make it a very important process for practical application are",
                "(a) Electron and energy are released",
                "(b) Neutron and Energy are released",
                "(c) Alpha particle only is released",
                "(d) Gamma rays and neutrino are released",
                "(b) Neutron and Energy are released"));

        questionList.add(new Question("A reaction where very high temperature about 10^8" +
                " to 10^9 Kelvin is needed to overcome Initial electrostatic repulsion force to occur is called",
                "(a) Fission",
                "(b) Adiabatic",
                "(c) Fusion",
                "(d) Catalytic",
                "(c) Fusion"));

        questionList.add(new Question("The mass of a 20Ne10 nucleus is 19.992amu. Calculate the nuclear binding energy " +
                "and hence the nuclear binding energy per nucleon of 20Ne10",
                "(a) 2.63 x 10^-19Mev",
                "(b) 1.56 x 10^-16Mev",
                "(c) 1.61 x 10^6Mev",
                "(d) 37406.15z Mev",
                "(c) 1.61 x 10^6Mev"));

        questionList.add(new Question("The mass of a randon 222 nucleus is 221.97023amu. It decays by α-emission to " +
                "form 218Po84. The mass of a 218Po84 nucleus is 217.96273amu. Estimate the " +
                "kinetic energy of the α-particle if its mass is 4.00260amu",
                "(a) 3782.988 Mev",
                "(b) 4.564 Mev",
                "(c) 3728.4 Mev",
                "(d) 456.4 Mev",
                "(d) 456.4 Mev"));
    }
}
