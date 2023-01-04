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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.Activity.Questions.SecondSemester100l.SecondSemesterActivity;
import com.folahan.unilorinapp.MainActivity;
import com.folahan.unilorinapp.Model.Question;
import com.folahan.unilorinapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Chm101Activity extends AppCompatActivity {

    private List<Question> questionList;
    private RadioGroup mGroup;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1, clicked = 0;
    private Button btnNext, btnPrev, btnEnd;
    private AlertDialog.Builder dialog;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chm101);

        questionList = new ArrayList<>();
        questionText = findViewById(R.id.questionText);
        mGroup = findViewById(R.id.rbGroup);
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

        CountDownTimer timer = new CountDownTimer(mTimeLeft, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeft = (int) l;
                int minutes = mTimeLeft / 1000 / 60;
                int secs = (mTimeLeft / 1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
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
        }

        btnEnd.setOnClickListener(view -> dialogAlert());

        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.button_previous);

        setListeners();
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
        mGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.radioA:
                    if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                            .equals(rbOption1.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                        pos2++;
                    }
                    clicked++;
                    break;
                    //
                case R.id.radioB:
                    if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                            .equals(rbOption2.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                        pos2++;
                    }
                    clicked++;
                    break;
                    //
                case R.id.radioC:
                    if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                            .equals(rbOption3.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                        pos2++;
                    }
                    clicked++;
                    break;
                    //
                case R.id.radioD:
                    if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                            .equals(rbOption4.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                        pos2++;
                    }
                    clicked++;
                    break;
                    //
                default:
                    return;
            }
        });

        /*rbOption1.setOnClickListener(view -> {
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
        });*/
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
        answerText.setText(questionList.get(position).getAnswer());



        questionNo.setText("Question "+questionAnswered+" of 30");

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("1. A reaction is said to be of zero order if _____",
                "the activation path is invisible",
                "the rate of reaction does not depend on the reaction concentration",
                "the catalyst involved is not effective",
                "the activation energy is negative",
                "the rate of reaction does not depend on the reaction concentration"));

        questionList.add(new Question("2. Sodium and Potassium belong to the same group of the periodic table. This is because",
                "both are metals",
                "both are soft and lighter than water", "both form cations by loosing electrons",
                "both have identical electronic configuration",
                "both have identical electronic configuration"));

        questionList.add(new Question("3. Metals are electropositive because", "they form positively charged ions",
                "they are electron acceptors", "they form negatively charged ions",
                "they form positively charged ions by losing electrons",
                "they form positively charged ions by losing electrons"));

        questionList.add(new Question("4. Given that 2R (g) &rarr; 2P (g) + Q(g) ?H = -x kJ Which of the <br /> following conditions\n" +
                "has no effect on the equilibrium position?", "Reducing the pressure",
                "Increasing the temperature", "adding more of Q", "Decreasing the temperature",
                "Decreasing the temperature "));

        questionList.add(new Question("5. The vapour pressure of ethanol is 115 torr at 34.9°C. If the heat of vapourization is " +
                "40.5KJ/mol. Calculate the temperature when the vapour pressure is 760 torr. (R = 8.314 KJ/mol)",
                "350.4K",
                "200.3K",
                "400K", "310.6K",
                "350.4K"));

        questionList.add(new Question("6. In writing equilibrium constant expression, which of the following quantities can be used to \n" +
                "represent the amount of reactants and products?",
                "(a) Concentration", "(b) Partial pressures",  "(c) Mole fractions" ,
                "(d)  (a) and (b) only", " (a) and (b) only"));

        questionList.add(new Question("7. The equilibrium expression, k = [Ag+] [Cl-] describe the reaction",
                " (a) AgCl Ag+ + Cl-", "(b) Ag+ + Cl- AgCl", " (c) Ag+ + Cl- Ag + Cl",
                " (d) Ag + Cl Ag+ + Cl\u0002",
                " (d) Ag + Cl Ag+ + Cl\u0002"));

        questionList.add(new Question("8. Cu(OH)2(s) + 2H+(aq) &harr; Cu2+(aq) + 2H2O (l)What is the effect of addition <br /> of \n" +
                "sodium hydroxide solution on the above equilibrium?",
                "Equilibrium shifts to the right.", "Equilibrium shifts to the right.",
                "More Cu (OH)2 reacts.",
                "Equilibrium shifts to the left.", "Equilibrium shifts to the right."));

        questionList.add(new Question("9. Identify the reducing agent in the following reaction <br /> 2 Al (s) + 3 Cu2+ aq &rarr; \n" +
                "2Al2+ aq + 3Cu aq",
                " Cu2+ ion", "Cu atom" ,
                " Al atom",
                "Al3+",
                "Cu atom"));

        questionList.add(new Question("10. Reducing agent X react with oxidizing agent Y, The true statement is",
                "X is reduced",
                "<br> X is reduced <br/> Y is oxidized",
                "X gain electron",
                "Y gain electron",
                "Y gain electron"));

        questionList.add(new Question("11. Arrange the compound in the increasing order of oxidation state of manganese <br /> I \n" +
                "KMnO4 <br /> II MnCl2 <br /> III MnO2",
                "II > I > III",
                " I < III < II",
                "III > II> I",
                "III = II = I",
                " I < III < II"));

        questionList.add(new Question("12. Given that 2R (g) &harr; 2P (g) + Q(g) ?H = -x kJ Which of the <br /> following conditions \n" +
                "has no effect on the equilibrium position?",
                " Reducing the pressure\n",
                " Reducing the pressure <br /> Increasing the temperature",
                " adding more of Q",
                " Adding a catalyst",
                " adding more of Q"));

        questionList.add(new Question("13. If reducing potential of Na+ + e is - 2.71, the oxidizing potential will be",
                "A. 10 - (-.271)",
                "B. 1-(- 2.71)",
                "C. +2.71",
                "D. -0.51",
                "D. -0.51"));

        questionList.add(new Question("15. For the reaction <br /> 6H2O + 4 NO2(g) &harr; 4 NH3 g + 7O2 g",
                "Kp = Kc/RT",
                "Kp = KcRT",
                "Kp = Kc(RT)2",
                "Kc = KpRT2",
                "Kp = Kc(RT)2"));

        questionList.add(new Question("16. Which of the following is the equilibrium constant for the reaction? <br /> x P + y Q &harr; \n" +
                "t R + f M\n",
                " [R] [M]/[P] [Q]",
                " [P]x [Q]y/[R]t [M]f",
                " [P] [Q]/[R] [Q]",
                " [R]t [M]f/[P]x [Q]y",
                " [P]x [Q]y/[R]t [M]f"));

        questionList.add(new Question("17. 2N2O (g) + N2H4 (g) &harr; 3N2 (g) + 2H2O (g) Kp/Kc is",
                " (RT)2",
                " Pa-",
                " 1/RT",
                " Atm-1",
                " (RT)2"));

        questionList.add(new Question("18. If X (g) + B (g) &harr; 2 C (g) took place in 0.5 litre container, the no of moles of each \n" +
                "material present at equilibrium are X - 1.0 mole B- 2.0 moles and C - 3.0 moles the value of Kc is",
                "4.5",
                "9",
                "6",
                "18",
                "9"));

        questionList.add(new Question("19. G at equilibrium is",
                "Positive",
                "negative",
                "0",
                "none of the options",
                "none of the options"));

        questionList.add(new Question("20. Elements P , Q, R, S have 6,11,15 and 17 electrons respectively, therefore",
                "P will form an electrovalent bond with R",
                "Q will form a covalent bond with S",
                "R will form an electrovalent bond with S",
                "Q will form an electrovalent bond with S",
                "R will form an electrovalent bond with S"));

        questionList.add(new Question("21. An element X forms the following compound with chlorine;XCl4, XCl3, XCl2. This illustrates the",
                "law of multiple proportion",
                "law of chemical propotion",
                "law of simple proportion",
                "law of conservation of mass",
                "law of conservation of mass"));

        questionList.add(new Question("22. Which of the following statements is an exception in the assumptions of the kinetic theory of gases ?",
                "Gases are composed of many elastic particles",
                "The particle are of negligible size",
                "The particle are in constant random motion",
                "The particle are of negligible mass",
                "The particle are of negligible size"));

        questionList.add(new Question("23. Given the mean atomic mass of chlorine prepared in the laboratory to be 35.5 and assuming " +
                "that chlorine contains the isotope of mass number 35 and 37. What is the percentage composition of " +
                "the isotope of mass number 35 ?",
                "20",
                "25",
                "50",
                "75",
                "25"));

        questionList.add(new Question("24. Element X has a total of 19 electrons and can react with O2 to form an oxide. Which of the " +
                "following statements is NOT true of the oxide of element X?",
                "When exposed to air, it slowly changes to liquid",
                "Its solution in water conduct electricity.",
                "Its solution in water on evaporation gives a white solid",
                "Its oxide, when slowly heated regenerates the element X",
                "Its solution in water on evaporation gives a white solid"));

        questionList.add(new Question("25. Sodium and potassium belong to the same group of the Periodic Table.This is because",
                "both are metals",
                "both forms cations by losing electrons.",
                "both have identical electronic configuration",
                "non of the option",
                "both are metals"));

        questionList.add(new Question("26.An element X has two isotopes 20 10 X and 22 10 X present in the ratio 1: 3.The relative " +
                "<br /> atomic mass of X would be",
                "20.5",
                "21.0",
                "21.5",
                "22.5",
                "20.5"));

        questionList.add(new Question("27. Cathode rays cause an object placed behind a perforated anode to cast a shadow on the end \n" +
                "of the tube.The observation shows that the rays",
                "are positive charged",
                "are negatively charged",
                "have masses",
                "travel in straight line",
                "have masses"));

        questionList.add(new Question("28. The basic assumptions in the kinetic theory of gases that the collisions of the gaseous <br /> " +
                "molecules are perfectly elastic implied that the",
                "the force of attraction and repulsion are in equilibrium.",
                "gaseous molecules can occupy any available space",
                "gaseous molecules will continue their motion indefinitely",
                "gases can be compressed",
                "gaseous molecules will continue their motion indefinitely"));

        questionList.add(new Question("29. Which quantum number divides shells in to orbital?",
                "Principal",
                "Azimuthal",
                "Magnetic",
                "Spin",
                "Principal"));

        questionList.add(new Question("30. The experiment that showed that atoms have tiny positively charged nucleus was first carried out by",
                "Moseley",
                "Rutherford",
                "Milikan",
                "Dalton",
                "Moseley"));
    }

    private void getQuestionPhase2(List<Question> list) {
        questionList.add(new Question("1. Which of the following substances is not a homogeneous mixture?",
                "filtered sea water",
                "writing ink",
                "flood water",
                "soft drink",
                "soft drink"));

        questionList.add(new Question("2. An increase in temperature causes an increase in the pressure of a gas at a fixed volume due \n" +
                "to an increase in the",
                "number of molecules of the gas",
                "density of the gas molecules",
                "number of collisions between the gas molecules and the wall of the container",
                "number of collisions between the gas molecules",
                "number of collisions between the gas molecules and the wall of the container"));

        questionList.add(new Question("3. An element X forms the following compounds with chlorine; XCl<sub>4</sub> , " +
                "XCl<sub>3</sub> , XCl<sub>2</sub> . This illustrate",
                "law of multiple proportions",
                "law of chemical proportions",
                "law of simple proportions",
                "law of conservation of mass",
                "law of multiple proportions"));

        questionList.add(new Question("4. If 1.0dm3 of gas x diffuses through porous plug in 60 seconds, while the same " +
                "volume of hydrogen diffuses in 15 seconds under the same conditions, calculate the relative molecular " +
                "mass of X (H = 1.0)",
                "8.0",
                "16.0",
                "4.0",
                "32.0",
                "16.0"));

        questionList.add(new Question("5. A sample of the gaseous compound, formed during bacterial fermentation of grain is found to " +
                "consist of 27.29% carbon and 7271% oxygen. What is the empirical formula of the compounds? (C = " +
                "12.0, H = 1.0)",
                "C<sub>2</sub> O<sub>4</sub>",
                "CO",
                "CO<sub>2</sub>",
                "None of the above",
                "CO<sub>2</sub>"));

        questionList.add(new Question("6. If atom X with six electrons in its outermost shell combines with atom Y with only one " +
                "electron in its outermost shell to form an ionc compound, then",
                "six atoms of X will combine with one of Y",
                "two atoms of X will combine with six of Y",
                "one atom of X will combine with two of Y",
                "two atoms of X will combine with one of Y",
                "two atoms of X will combine with one of Y"));

        questionList.add(new Question("7. Two electrons in the 1s orbital must have different spin quantum numbers to satisfy",
                "Hund’s rule",
                "the magnetic rule",
                "the Pauli Exclusion Principle",
                "the Aufbau principle",
                "the Pauli Exclusion Principle"));

        questionList.add(new Question("8. What is the empirical formula of a cupric oxalate, if the compound contains 41.93% Cu, " +
                "15.85% C and 42.22% O by mass? (Cu = 63.55, C = 12.0, O =16.0)",
                "CuC<sub>3</sub> O<sub>4</sub>",
                "Cu (C<sub>2</sub> O<sub>4</sub> )<sub>2</sub>",
                "CuC<sub>2</sub> O<sub>4 </sub>",
                "Cu<sub>2</sub> (C<sub>2</sub> O<sub>4</sub> )<sub>3</sub>",
                "CuC<sub>2</sub> O<sub>4 </sub>"));

        questionList.add(new Question("9. The main energy levels of an atom are indicated by the",
                "orbital quantum numbers",
                "magnetic quantum numbers",
                "spin quantum numbers",
                "principal quantum numbers",
                "principal quantum numbers"));

        questionList.add(new Question("10. From thermodynamic principles, the free energy of a reaction system is made up of two " +
                "energy components",
                "entropy and temperature",
                "enthalpy and entropy",
                "enthalpy and temperature",
                "entropy and Hemholtz free energy",
                "enthalpy and entropy"));

        questionList.add(new Question("11. If &Delta;E = h? is true for electromagnetic wave, then only one of the following statements " +
                "can hold true.",
                "the most energetic wave has the lowest wavenumber",
                "the most energetic wave has the shortest wavelength",
                "the most energetic wave has the longest frequency",
                "none of the above",
                "none of the above"));

        questionList.add(new Question("12. The surface of a well-formed crystallinesolid reveals upon examination, flat planes that " +
                "intersect at angles characteristics of the particular substance under investigation is prominent in",
                "crystalline solids",
                "amorphous solids",
                "interfacial angles",
                "solids",
                "crystalline solids"));

        questionList.add(new Question("13. Calculate &Delta;H at 298.15K for the reaction <br /> 2Na(s) + 2H<sub>2</sub> O(i) " +
                "2NaOH(s) + H<sub>2</sub> (g). <br /> If H<sub>2</sub> O(1) = -285.8kJmol-1 and NaOH(s) = -" +
                "426.8kJmol-1.",
                "–291.9KJ",
                "–281.9KJ",
                "–271.9KJ",
                "+281.9KJ",
                "–281.9KJ"));

        questionList.add(new Question("14. J.J. Thomson is known for his <br /> <br /> I \"plum pudding\" model of the atom <br /> " +
                "<br /> II Thin gold experiment <br /> <br /> III Cathode ray experiment <br /> <br /> IV Discovery of " +
                "nucleus",
                "I and II are correct",
                "II and III are correct",
                "I and IV are correct",
                "I and III are correct",
                "I and III are correct"));

        questionList.add(new Question("15. Stoichiometry involves the following except",
                "Basis of qualitative analysis",
                "Calculation of yields",
                "efficiency of chemical reaction",
                " Stoichiometric equation",
                "Basis of qualitative analysis"));

        questionList.add(new Question("16. All the properties are variable across the period and down the group except",
                "latent heat fusion",
                "boiling point",
                "charges in the nucleus",
                "number of shells",
                "charges in the nucleus"));

        questionList.add(new Question("17. The amount of work needed to expand the surface of a liquid depends on the strength of the " +
                "inward forces and is called",
                "liquid’s surface tension",
                "covalent crystal",
                "mean free path",
                "surface diffusion",
                "liquid’s surface tension"));

        questionList.add(new Question("18. For the reaction 2HI(g) H<sub>2</sub> (g) + I<sub>2</sub> (g), calculate the rate " +
                "at which HI disappears if the rate of formation of I<sub>2</sub> at a particular time is 3.5 x 10<sup>-" +
                "5</sup> (mol dm<sup>-3</sup> s<sup>-1</sup> )",
                "7.0 x 10<sup>-5</sup> mol dm<sup>-3</sup> s<sup>-1</sup>",
                "3.5 x 10<sup>-5</sup> mol dm<sup>-3</sup> s<sup>-1</sup>",
                "14.0 x 10<sup>-5</sup> mol dm<sup>-3</sup> s<sup>-1</sup>",
                "1.75 x 10<sup>-5 </sup> mol dm<sup>-3</sup> s<sup>-1</sup>",
                "7.0 x 10<sup>-5</sup> mol dm<sup>-3</sup> s<sup>-1</sup>"));

        questionList.add(new Question("19. Determine the number of moles of ammonia in 4.26g of ammonia (N = 14.0, H = 1.0)",
                "0.20mol",
                "0.25mol",
                "1.20mol",
                "1.25mol",
                "0.25mol"));

        questionList.add(new Question("20. The total amount of energy in the universe is constant and is referred to as the",
                "First law of thermodynamic",
                "Second law of thermodynamic",
                "Third law of thermodynamic",
                " A and C are correct.",
                "First law of thermodynamic"));

        questionList.add(new Question("21. The atomic numbers of two elements X and Y are 12 and 9 respectively. The bond in the " +
                "compound formed between the atoms of these two elements is",
                "ionic",
                "covalent",
                "neutral",
                "coordinate",
                "ionic"));

        questionList.add(new Question("22. The component of atom that contributes least to its mass is the",
                "nucleus",
                "neutron",
                "electron",
                "proton",
                "electron"));

        questionList.add(new Question("23. 50cm3 of hydrogen are sparked with 20cm3 of oxygen at 1000C and 1 atmosphere. The total " +
                "volume of the residual gases is",
                "50 cm3",
                "70cm3",
                "30cm3",
                "40cm3",
                "50 cm3"));

        questionList.add(new Question("24. Which of these describes the spin on the last electron in Nitrogen with atomic number seven?",
                "+1/2",
                "-1/2",
                "1/2",
                "1",
                "+1/2"));

        questionList.add(new Question("25. Any spontaneous change that occurs in the universe must be <br /> accompanied by an " +
                "increase in the entropy of the universe is termed <br /> <br /> <br /> <br /> First law of thermodynamic " +
                "<br /> ? <br /> Second law of thermodynamic <br /> ? <br /> Third law of thermodynamic <br /> ? <br /> " +
                "Law of conservation of entropy.",
                "First law of thermodynamic",
                "Second law of thermodynamic",
                "Third law of thermodynamic",
                "Law of conservation of entropy.",
                "Second law of thermodynamic"));

        questionList.add(new Question("26. The French scientist Lous de Broglie believed",
                "electrons could have a dual wave-particle nature",
                "light waves did not have a dual wave-particle nature",
                "the natures of light and quantized electron orbits were not similar",
                "Bohr’s model of the hydrogen atom was completely correct",
                "electrons could have a dual wave-particle nature"));

        questionList.add(new Question("27. If a crystal is cleaved or even crushed into a powder, each resulting particle will possess",
                "faces",
                "interfacial faces",
                "angles",
                "interfacial angles",
                "interfacial angles"));

        questionList.add(new Question("28. Which of the following atoms has the smallest radius?",
                "O",
                "F",
                "Ne",
                "N",
                "Ne"));

        questionList.add(new Question("29. Which of the following is a measure of the average kinetic energy of substance?",
                "volume",
                "mass",
                "pressure",
                "temperature",
                "temperature"));

        questionList.add(new Question("30. Which one of the following elements possesses complete filling of electrons in the d-orbital?",
                "Cu",
                "Ti",
                "Zn",
                "Co",
                "Zn"));

    }

    private void getQuestionPhase3(List<Question> list) {

        questionList.add(new Question("1. Which of these pairs of metal has variable valencies?",
                "Iron and copper",
                "Zinc and caesium",
                "Iron and strontium",
                "Barium and scandium",
                "Iron and copper"));

        questionList.add(new Question("2. J.J. Thomson’s model of the atom fell into disrepute and was replaced by one of the " +
                "followings ",
                "Dalton’s theory",
                "Rutherford Nuclear model",
                "Bohr model",
                "Balmer series model",
                "Rutherford Nuclear model"));

        questionList.add(new Question("3. Phosphorous burns in oxygen according to the equation" +
                "P4 + 5O2 P4O . How many litres of oxygen will be required at s.t.p. for complete oxidation " +
                "of 12.4g of phosphorous? (P = 31, 0 = 16 and molar volume of a gas at s.t.p. = 22.4 litres) ",
                "5.20 litre",
                "11.20 litre",
                "2.24 litre",
                "20.20 litre ",
                "11.20 litre"));

        questionList.add(new Question("4. Which of these statements is not true when the cubes are added into the hot tea in a " +
                "glass cup?",
                "11.20 litre",
                "2.24 litre",
                "20.20 litre ",
                "11.20 litre"));

        questionList.add(new Question("5. The atom of an element X is represented as YZX .The basic chemical properties of X depend " +
                "on the value of",
                "Y",
                "Z",
                "Y-Z",
                "Z-Y",
                "Y"));

        questionList.add(new Question("6. How many electrons are in the L - shell of 3115P",
                "2",
                "5",
                "8",
                "18",
                "5"));

        questionList.add(new Question("7. Which experiment led to the measurement of the charge on an electron?",
                "Scattering ? –particles",
                "Discharging- tube experiment",
                "Oil- experiment",
                "Mass spectrometric experiment",
                "Scattering ? –particles"));

        questionList.add(new Question("8. An element X forms the following compound with chlorine;XCl<sub>4</sub> , " +
                "XCl<sub>3</sub> , XCl<sub>2</sub> . This illustrates the",
                "law of multiple proportion",
                "law of conservation of mass",
                "law of simple proportion",
                "law of chemical propotion",
                "law of multiple proportion"));

        questionList.add(new Question("9. How many moles of sulphur molecules are contained in 80.3g of sulphur if the molecular " +
                "formula is S₈ ? (S = 32.06)",
                "0.08 mole",
                "0.16 mole",
                "0.313 mole",
                "0.260 mole",
                "0.313 mole"));

        questionList.add(new Question("10. Which of the following can change the value of the equilibrium constant for a reaction?",
                "Changing the concentration of the reactants",
                "Adding a catalyst",
                "Adding a catalyst",
                "Removing the products as they are formed",
                "Adding a catalyst"));

        questionList.add(new Question("11. In writing equilibrium constant expression, which of the following quantities can be used to " +
                "represent the amount of reactants and products?",
                "(a) Concentration",
                "(b) Partial pressures",
                "(c) Mole fractions",
                "(d) (a) and (b) only",
                "(d) (a) and (b) only"));

        questionList.add(new Question("12. In cell convection, the slant lanes/ solid vertical lines represent _____",
                "(a) products",
                "(b) boundaries between the phases",
                "(c) activation path",
                "(d) cell design",
                "(b) boundaries between the phases"));

        questionList.add(new Question("13. The equilibrium expression, k = [Ag+] [Cl-] describe the reaction",
                "(a) AgCl Ag+ + Cl-",
                "(b) Ag+ + Cl- AgC",
                "(c) Ag+ + Cl- Ag + Cl",
                "(d) Ag + Cl Ag+ + Cl-",
                "(d) Ag + Cl Ag+ + Cl-"));

        questionList.add(new Question("14. Give the IUPAC name of the compound Fe₃P₂",
                "(a) Ironphosphate",
                "(b) Iron(ii)phosphate",
                "(c) Iron(iii)phospate",
                "(d) Phosphateianide",
                "(b) Iron(ii)phosphate"));

        questionList.add(new Question("15. Give the IUPAC name of the compound H₂CrO₄",
                "(a) tetraoxodihydrogenchromate(vi)",
                "(b) hydrogen tetraoxochromate(iv)",
                "(c) hydrogen tetraoxochromate(vi)",
                "(d) tetraoxochromate(v)",
                "(c) hydrogen tetraoxochromate(vi)"));

        questionList.add(new Question("16. Give the IUPAC name of the compound Ca₃N₂",
                "(a) Calcium(ii)nitride",
                "(b) Calcium nitride",
                "(c) Calcium(iii)nitride",
                "(d) None of the above",
                "(b) Calcium nitride"));

        questionList.add(new Question("17. Calculate the average bond energy of the C - H bond in methane molecule. ",
                "(a) 1656 KJ/mol",
                "(b) 414 KJ/mol",
                "(c) 4140 KJ/mol",
                "(d) 165 KJ/mol",
                "(b) 414 KJ/mol"));

        questionList.add(new Question("18. Cu(OH)₂(s) + 2H+(aq) &harr; Cu₂+(aq) + 2H₂O (l)What is the effect of addition of " +
                "sodium hydroxide solution on the above equilibrium",
                "(a) Equilibrium shifts to the right",
                "(b) Equilibrium is not affected",
                "(c) More Cu (OH)2 reacts",
                "(d) Equilibrium shifts to the left",
                "(a) Equilibrium shifts to the right"));

        questionList.add(new Question("19. Identify the reducing agent in the following reaction <br /> 2 Al (s) + 3 Cu₂+ aq &rarr; " +
                "2Al2+ aq + 3Cu aq",
                "(a) Cu₂+ ion",
                "(b) Cu atom",
                "(c) Al atom",
                "(d) Al₃+",
                "(b) Cu atom"));

        questionList.add(new Question("20. 0.82g of a hydrate Y₂CO₃.5H₂O was heated in an oven to remove all the " +
                " water of crystallization. If the weight of the residue left is 0.37g. Identify Y",
                "(a) 14",
                "(b) 0.9",
                "(c) 6.3",
                "(d) 7",
                "(d) 7"));

        questionList.add(new Question("21. Reducing agent X react with oxidizing agent Y, The true statement is",
                "(a) X is reduced",
                "(b) Y is oxidized",
                "(c) X gain electron",
                "(d) Y gain electron",
                "(d) Y gain electron"));

        questionList.add(new Question("22. An oxide of an element x with the formula XO₂ occupies 0.14dm³ of volume " +
                "at STP. Identify the Molar Mass of X",
                "(a) 64 g/mol",
                "(b) 32 g/mol",
                "(c) 16 g/mol",
                "(d) 8 g/mol",
                "(b) 32 g/mol"));

        questionList.add(new Question("23. If reducing potential of Na+ + e is - 2.71, the oxidizing potential will be",
                "(a) 10 - (-.271)",
                "(b) 1-(- 2.71)",
                "(c) +2.71",
                "(d) -0.51",
                "(d) -0.51"));

        questionList.add(new Question("24. 2.5 grams of N₂O was placed in the container at STP. Calculate the volume occupied " +
                "by the gas.",
                "(a) 1.27cm³",
                "(b) 22.4cm³",
                "(c) 0cm³",
                "(d) 11.2cm³",
                "(a) 1.27cm³"));

        questionList.add(new Question("25. For the reaction 6H₂O + 4 NO₂(g) &harr; 4 NH3 g + 7O₂ g",
                "(a) Kp = Kc/RT",
                "(b) Kp = KcRT",
                "(c) Kp = Kc(RT)₂",
                "(d) Kc = KpRT₂",
                "(c) Kp = Kc(RT)₂"));

        questionList.add(new Question("26. Which of these pairs of metal has variable valencies? ",
                "(a) Iron and copper",
                "(b) Zinc and caesium",
                "(c) Iron and strontium",
                "(d) Barium and scandium",
                "(a) Iron and copper"));

        questionList.add(new Question("27. J.J. Thomson’s model of the atom fell into disrepute and was replaced by one of the followings ",
                "(a) Dalton’s theory",
                "(b) Rutherford Nuclear model ",
                "(c) Bohr model",
                "(d) Balmer series model",
                "(b) Rutherford Nuclear model "));

        questionList.add(new Question("28. Phosphorous burns in oxygen according to the equation " +
                "P₄ + 5O₂ P₄O . How many litres of oxygen will be required at s.t.p. for complete oxidation " +
                "of 12.4g of phosphorous? (P = 31, 0 = 16 and molar volume of a gas at s.t.p. = 22.4 litres) ",
                "(a) Dalton’s theory",
                "(b) Rutherford Nuclear model ",
                "(c) Bohr model",
                "(d) Balmer series model",
                "(b) Rutherford Nuclear model "));

        questionList.add(new Question("28. Which of these statements is not true when the cubes are added into the hot tea in a " +
                " glass cup?",
                "(a) Tea is surrounding the ice cubes",
                "(b) The system is the ice cubes.",
                "(c) Heat is transferred from the tea to the ice cubes and the ice melts.",
                "(d) Heat is only transferred from the ice cubes to the tea and the ice melts",
                "(d) Heat is only transferred from the ice cubes to the tea and the ice melts"));

        questionList.add(new Question("29. The following thermodynamic properties: temperature, enthalpy, entropy and energy " +
                "changes are called",
                "(a) non-state functions",
                "(b) state functions",
                "(c) adiabatic functions",
                "(d) repulsive forces",
                "(b) state functions"));

        questionList.add(new Question("30. Ionization energy",
                "(a) increases across the period but decreases down the group of the periodic table ",
                "(b) decreases across the period but increases down the group of the periodic table",
                "(c) increases both across the period and down the group of the periodic table ",
                "(d) decreases both across the period and down the group of the periodic table ",
                "(a) increases across the period but decreases down the group of the periodic table "));
    }

    private void getQuestionPhase4(List<Question> list) {

        questionList.add(new Question("1. Amphoteric elements are the elements which",
                "have similar physical properties with metals but similar chemical properties with non-metals",
                "possess acidic properties only", "possess neither acidic nor basic properties",
                "possess basic properties",
                "have similar physical properties with metals but similar chemical properties with non-metals"));

        questionList.add(new Question("2. Sodium and Potassium belong to the same group of the periodic table. This is because",
                "both are metals",
                "both are soft and lighter than water",
                "both form cations by loosing electrons",
                "both have identical electronic configuration",
                "both have identical electronic configuration"));

        questionList.add(new Question("5. Which of the following can change the value of the equilibrium constant for a reaction?",
                "Changing the concentration of the reactants",
                "Adding a catalyst",
                "Changing the solvent", "Removing the products as they are formed",
                "Changing the solvent"));
    }


}