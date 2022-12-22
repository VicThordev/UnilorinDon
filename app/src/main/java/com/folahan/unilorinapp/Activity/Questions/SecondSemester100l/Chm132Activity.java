package com.folahan.unilorinapp.Activity.Questions.SecondSemester100l;

import androidx.annotation.Nullable;
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

public class Chm132Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1, clicked = 0;
    Button btnNext, btnPrev, btnEnd;
    private AlertDialog.Builder dialog;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chm132);

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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
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
        if (questionAnswered == 30) {
            showButton();
        }

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("1. The ligands that donate more than one electron pairs is called",
                "(a) Lewis acid",
                "(b) Monodentate",
                "(c) Multidentate",
                "(d) Lewis base",
                "(c) Multidentate"));

        questionList.add(new Question("2. The number of bonds attached to the central metal atom is called…",
                "(a) oxidation number",
                "(b) electrovalent number",
                "(c) atomic number",
                "(d) Coordination number",
                "(d) Coordination number"));

        questionList.add(new Question("3. The following are examples of ligands except",
                "(a) H2O",
                "(b) Br",
                "(c) Be",
                "(d) Cl",
                "(c) Be"));

        questionList.add(new Question("4. Transition metals are paramagnetic because of",
                "(a) Possession of paired electron",
                "(b) Possession of unpaired electron",
                "(c) They are coloured",
                "(d) They are amphoteric",
                "(b) Possession of unpaired electron"));

        questionList.add(new Question("5. Which of the following groups of physical properties increase from left to right " +
                "of the periodic table? \n (1) Atomic energy (2) electronegativity (3) electron affinity (4) Atomic " +
                "radius.",
                "(a) 1 & 2",
                "(b) 2 & 3 ",
                "(c) 1, 2 & 3",
                "(d) All of the above",
                "(b) 2 & 3 "));

        questionList.add(new Question("6. State which elements in the series Na, Mg, Al, Si, S and Cl has the highest " +
                "melting point?",
                "(a) Na",
                "(b) Mg",
                "(c) S" ,
                "(d) Cl", "(b) Mg"));

        questionList.add(new Question("7. Water molecules can be ligands especially when they are bonded to",
                "(a) Transition metal", "(b) alkali metals",
                "(c) alkaline metals", "(d) Group V elements.",
                "(a) Transition metal"));

        questionList.add(new Question("8. The bond formed between lithium and fluorine is",
                "(a) covalent", "(b) hydrogen bond",
                "(c) ionic bond",
                "(d) dative bond.",
                "(c) ionic bond"));

        questionList.add(new Question("9. Group 1A metals are not formed in nature because they",
                "(a) fluorite and ZnS",
                "(b) Zinc blende & wurtzite",
                "(c) ZnS & corundum",
                "(d) all of the above",
                "(b) Zinc blende & wurtzite"));

        questionList.add(new Question("10. Aluminium is trivalent element because",
                "(a) it is a metal",
                "(b) it is a non-metal",
                "(c) its has a valency of three",
                "(d) it is electropositive", "(c) its has a valency of three"));

        questionList.add(new Question("11. Silica exists in six different crystalline forms as",
                "(a) quartz",
                "(b) Rutile" ,
                "(c) fluorite",
                "(d) none of the above",
                "(a) quartz"));

        questionList.add(new Question( "12. Cadmium chloride occur in which of this arrangement?",
                "(a) ABABAB",
                "(b) ACBACB",
                "(c) ABCABC",
                "(d) AABBCC",
                "(c) ABCABC"));

        questionList.add(new Question( "13. It is possible to measure the interatomic distance between two different ion " +
                "very accurately by.",
                "(a) Mass spectroscopy",
                "(b) Infrared spectroscopy",
                "(c) X-ray crystallrgraphy",
                "(d) all of the above.",
                "(c) X-ray crystallrgraphy"));

        questionList.add(new Question( "14. Calculate the dipole moment of a charge on an electron of 4.8 x 10-10 esu ",
                "(a) 4.8 x 10-18 esu.cm",
                "(b) 1.44 x 10-17 esu.cm",
                "(c) 1.44 x 10-19 esu.cm ",
                "(d) " +
                        "4.8 x 10-19 esu.cm",
                "(c) 1.44 x 10-19 esu.cm "));

        questionList.add(new Question( "15. Antimony does not obey the octet because its valence electron is",
                "(a) 5",
                "(b) 6",
                "(c) 8",
                "(d) 7",
                "(a) 5"));

        questionList.add(new Question( "16. When the electronegative difference is large, the bonding is….",
                "(a) ionic",
                "(b) covalent",
                "(c) dative",
                "(d) Metallic",
                "(a) ionic"));

        questionList.add(new Question( "17. Which of the following molecule does not exhibit resonance?",
                "(a) N2O",
                "(b) SO2",
                "(c) HNO3",
                "(d) NH3",
                "(d) NH3"));

        questionList.add(new Question( "18. Which of the following can not be used to determine bonding?",
                "(a) electron affinity",
                "(b) resonance",
                "(c) electronegativity",
                "(d) Ionization energy",
                "(b) resonance"));

        questionList.add(new Question( "19. Tetrahedral compounds differ from square planar in ",
                "(a) bond angle",
                "(b) coordination number",
                "(c) quantum number",
                "(d) oxidation number",
                "(a) bond angle"));

        questionList.add(new Question( "20. What is the coordination number of a square pyramid shape?",
                "(a) 3",
                "(b) 5",
                "(c) 6",
                "(d) 4",
                "(b) 5"));

        questionList.add(new Question( "21. The hybridization of a trigonal bipyramidal shape is",
                "(a) SP2d",
                "(b) SP3d",
                "(c) SP2d2",
                "(d) SP3d2",
                "(c) SP2d2"));

        questionList.add(new Question( "22. Both square planar and octahedral compounds will exhibit.",
                "(a) Ionization",
                "(b) oxidation",
                "(c) Isomerism",
                "(d) reduction",
                "(c) Isomerism"));

        questionList.add(new Question( "23. Valence bond theory fails to explain which of the following properties?",
                "(a) magnetic",
                "(b) electrical",
                "(c) physical",
                "(d) all of the above",
                "(a) magnetic"));

        questionList.add(new Question( "24. The similarity between tetrahedral and square planar compound is that both " +
                "have",
                "(a) a coordination Number of 2",
                "(b) a coordination number of 4",
                "(c) a coordination Number of 6",
                "(d) a coordination number of 3",
                "(b) a coordination number of 4"));

        questionList.add(new Question( "25. The following are van der waals forces except",
                "(a) Dipole-Dipole",
                "(b) Dipole-induced dipole",
                "(c) induced dipole – induced dipole",
                "(d) ion - dipole",
                "(d) ion - dipole"));

        questionList.add(new Question( "26. In acetic acid, the bond connecting the atoms together is …..",
                "(a) hydrogen bond",
                "(b) dative bond",
                "(c) metallic bond",
                "(d) ionic bond",
                "(a) hydrogen bond"));

        questionList.add(new Question( "27. The bond in a complex can be",
                "(a) ionic",
                "(b) covalent",
                "(c) dative",
                "(d) metallic",
                "(c) dative"));

        questionList.add(new Question( "28. The hybridization of carbon in ethylene is",
                "(a) Sp3",
                "(b) SP2",
                "(c) SP",
                "(d) SP2d",
                "(b) SP2"));

        questionList.add(new Question( "29. What are the permitted values for the azimuthal quantum number (L) for an " +
                "electron with a principal quantum number n 5?",
                "(a) 0,1",
                "(b) 2,3",
                "(c) 4",
                "(d) all of the above",
                "(d) all of the above"));

        questionList.add(new Question( "30. Which of the following represents a reasonable set of quantum number for " +
                "a 3d electrons?",
                "(a) 3, 2, 1, ½",
                "(b) 3, 0, 2, -½",
                "(c) a & b",
                "(d) Neither of these",
                "(c) a & b"));

        questionList.add(new Question( "31. Determine the formal charge on H₃0+",
                "(a) 1",
                "(b) 0",
                "(c) -1",
                "(d) 2",
                "(a) 1"));

        questionList.add(new Question( "32. The total valence electron in SbCl₅ is",
                "(a) 20",
                "(b) 30",
                "(c) 40",
                "(d) 12",
                "(c) 40"));

        questionList.add(new Question( "33. The formal charge on NH₄+ is ",
                "(a) -1",
                "(b) +1",
                "(c) 0",
                "(d) 2",
                "(b) +1"));

        questionList.add(new Question( "34. The ability of a compound to exhibit two or more resonance structures is known as ………………",
                "(a) resonance",
                "(b) coordination",
                "(c) inert pair effect",
                "(d) valency",
                "(a) resonance"));

        questionList.add(new Question( "35. calculate the bond length of H₂ molecule if the bond length of an atom of hydrogen is 0.37A",
                "(a) 0.37a",
                "(b) 0",
                "(c) 0.74A",
                "(d) 0.185A",
                "(c) 0.74A"));

        questionList.add(new Question( "36. ……………….. is defined as the distance between the nuclei of two bonded nuclei",
                "(a) bond strength",
                "(b) bond length",
                "(c) atomic radius",
                "(d) bond property",
                "(b) bond length"));

        questionList.add(new Question( "37. Bond strength is also referred to as ……………..",
                "(a) bond length",
                "(b) bond energy",
                "(c) bond power",
                "(d) bond property",
                "(b) bond energy"));

        questionList.add(new Question( "37. ______ is the distance between the nuclei of two bonded atoms.",
                "(a) bond distance",
                "(b) bond energy",
                "(c) bond space",
                "(d) bond angle",
                "(a) bond distance"));

        questionList.add(new Question( "38. The crystal lattice is made up of repeated unit called ",
                "(a) unit cell",
                "(b) particles",
                "(c) motif",
                "(d) crystal arrangement",
                "(a) unit cell"));

        questionList.add(new Question( "39. The bond angle of carbon atoms in graphite is _____",
                "(a) 90°",
                "(b) 120°",
                "(c) 118°",
                "(d) 20°",
                "(b) 120°"));

        questionList.add(new Question( "40. This three(3)-dimensional arrangement of the particles are called _____",
                "(a) Crystal Lattice",
                "(b) Motif",
                "(c) Unit cell",
                "(d) Simple cubic structure",
                "(a) Crystal Lattice"));
    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question("1. Group 1A elements are also called alkaline metals because _______",
                "(a) their solution in water is strongly alkaline",
                "(b) have two valence electrons in their outermost shell",
                "(c) they are p-block elements",
                "(d) they are s-block elements",
                "(a) their solution in water is strongly alkaline"));

        questionList.add(new Question("2. Group 1A forms ……………….. ions wen ionized",
                "(a) univalent",
                "(b) bivalent",
                "(c) trivalent",
                "(d) tetravalent",
                "(a) univalent"));

        questionList.add(new Question("3. I. both alkali and alkali earth metals forms ionic compound which are soluble in water\n" +
                "II. both are characterized by ns\n" +
                "1\n" +
                "and ns\n" +
                "2\n" +
                "valence electronic configuration respectively\n" +
                "III. both are from p-orbitals\n" +
                "IV. both react with oxygen to form oxides\n" +
                "Which of these is correct about alkali and alkali earth metals?",
                "(a) I,II,III and IV",
                "(b) I,II and III",
                "(c) I,II and IV",
                "(d) II and IV",
                "(c) I,II and IV"));

        questionList.add(new Question("4. A compound M with an atomic number of 19 reacts with an halogen member X to form" +
                " halides.The compound formed can be represented by",
                "(a) M₂X",
                "(b) MX₂",
                "(c) MX",
                "(d) M₂X₂",
                "(c) MX"));

        questionList.add(new Question("5. Group III elements are characterized by the valence electronic configuration of",
                "(a) ns¹np¹",
                "(b) ns²p¹",
                "(c) ns¹np¹",
                "(d) ns²np²",
                "(b) ns²p¹"));

        questionList.add(new Question("6. One unique characteristics about group I elements Is that they are",
                "(a) good reducing agents",
                "(b) good oxidizing agents",
                "(c) good bleaching agents",
                "(d) insoluble in water",
                "(a) good reducing agents"));

        questionList.add(new Question("7. ………………….. have the largest atomic size in the periodic table",
                "(a) noble gases",
                "(b) transition elements",
                "(c) halogens",
                "(d) alkali metals",
                "(d) alkali metals"));

        questionList.add(new Question("8. Elements which have the tendency to lose one or more electrons during chemical bonding are called ………………….. ",
                "(a) electronegative elements",
                "(b) electropositive elements",
                "(c) noble gases",
                "(d) covalent elements",
                "(b) electropositive elements"));

        questionList.add(new Question("9. The total valence electrons in CO2 is ……………….",
                "(a) 12",
                "(b) 20",
                "(c) 16",
                "(d) 10",
                "(c) 16"));

        questionList.add(new Question("10. The total pairs of valence electrons in C₂H₂ is ……………",
                "(a) 7",
                "(b) 5",
                "(c) 6",
                "(d) 4",
                "(b) 5"));

        questionList.add(new Question("11. The total number of valence electrons in N₂ is ………………",
                "(a) 0",
                "(b) 5",
                "(c) 10",
                "(d) 6",
                "(c) 10"));

        questionList.add(new Question("12. The process by which internal structure of solids is studied is known as……………",
                "(a) X-ray crystallography",
                "(b) X-ray chromatography",
                "(c) X-ray centrifugal base",
                "(d) Crystallization",
                "(a) X-ray crystallography"));

        questionList.add(new Question("13. Inspite of its appearance,glass is not a true solid,but a …………… ",
                "(a) crystal solid",
                "(b) regular solid",
                "(c) viscous liquid",
                "(d) viscous solid",
                "(c) viscous liquid"));

        questionList.add(new Question("14. ………………. is the fundamental building block of a crystal",
                "(a) amorphous solid",
                "(b) crystal lattice",
                "(c) unit cell",
                "(d) motif",
                "(c) unit cell"));

        questionList.add(new Question("15. ……………….. is the part of pattern that lies within a single parallelogram",
                "(a) motif",
                "(b) lattice points ",
                "(c) unit cell",
                "(d) crystal lattice",
                "(a) motif"));

        questionList.add(new Question("16. Bravias showed that all lattices can be divided into …………….",
                "(a) 8",
                "(b) 10",
                "(c) 14",
                "(d) 24",
                "(c) 14"));

        questionList.add(new Question("17. The most regular Bravias crystal system is the ……………",
                "(a) tetragonal",
                "(b) monoclinic",
                "(c) triclinic",
                "(d) cubic",
                "(d) cubic"));

        questionList.add(new Question("18. The ………………..system is the least regular lattice,with no axes or angles equal",
                "(a) monoclinic",
                "(b) triclinic",
                "(c) cubic",
                "(d) orthorhombic",
                "(b) triclinic"));

        questionList.add(new Question("19. The ………………..lattice has a ninth lattice point at the centre of the cube",
                "(a) body centred",
                "(b) face centred cubic",
                "(c) body centred cubic",
                "(d) face centred",
                "(c) body centred cubic"));

        questionList.add(new Question("20. The ……………. lattice has 14 lattice points in the unit cell",
                "(a) body centred cubic",
                "(b) face centred cubic",
                "(c) end centred",
                "(d) hexagonal",
                "(b) face centred cubic"));

        questionList.add(new Question("21. Primitive and end centred systems are types of ……………….system",
                "(a) monoclinic",
                "(b) rhombic",
                "(c) cubic",
                "(d) hexagonal",
                "(a) monoclinic"));

        questionList.add(new Question("22. ………………… system has all the four lattice points in its structure",
                "(a) cubic",
                "(b) monoclinic",
                "(c) orthorhombic",
                "(d) tetragonal",
                "(c) orthorhombic"));

        questionList.add(new Question("23. How a substance behaves under different conditions of temperature and pressure can " +
                "be represented on a graph called ………..",
                "(a) phase graph",
                "(b) phase lattice",
                "(c) phase diagram",
                "(d) phase picture",
                "(c) phase diagram"));

        questionList.add(new Question("24. …………………..solids seem to be solids but are not made of crytals",
                "(a) crystalline",
                "(b) amorphous",
                "(c) complex",
                "(d) cubic",
                "(b) amorphous"));

        questionList.add(new Question("25. The crystal lattice structure with a coordination number of 4 is a/an ……………………",
                "(a) tetrahedral",
                "(b) octahedral",
                "(c) cubic",
                "(d) complex",
                "(a) tetrahedral"));

        questionList.add(new Question("26. The crystal lattice structure with a coordination number of 8 is a/an ______",
                "(a) cubic",
                "(b) octahedral",
                "(c) tetrahedral",
                "(d) complex",
                "(a) cubic"));

        questionList.add(new Question("27. An example of crystal solids made of giant molecules is ………………",
                "(a) coal",
                "(b) graphite",
                "(c) diamond",
                "(d) chalk",
                "(c) diamond"));

        questionList.add(new Question("28. Molecules are held together in crystals by …………………",
                "(a) van der waals",
                "(b) covalent",
                "(c) ionic",
                "(d) crystal bonds",
                "(a) van der waals"));

        questionList.add(new Question("29. In the pressure-temperature graph,a point where the solid,liquid and gas are in equilibrium is called",
                "(a) phase point",
                "(b) equilibrium point",
                "(c) triple point",
                "(d) equilibrium phase",
                "(c) triple point"));

        questionList.add(new Question("30. Withdrawal of heat from the liquid system until a point is reached when solids begin to" +
                " form at a constant pressure.The point reached is known as …………………",
                "(a) freezing temperature",
                "(b) solidifying temperature",
                "(c) cooling temperature",
                "(d) absolute temperature",
                "(d) absolute temperature"));

        questionList.add(new Question("31. The P I F form of lattice symbol is a designation of ……………….system",
                "(a) orthorhombic",
                "(b) hexagonal",
                "(c) triclinic",
                "(d) cubic",
                "(d) cubic"));

        questionList.add(new Question("32. ………………. consist of a space within eight lattice points which occupy the corners of a" +
                " cube or other parallelogram",
                "(a) motif",
                "(b) unit cell",
                "(c) lattice",
                "(d) primitive spacing",
                "(b) unit cell"));

        questionList.add(new Question("33. Rhombohedral,hexagonal and triclinic contains …………….. lattice symbols",
                "(a) P",
                "(b) I",
                "(c) F",
                "(d) P&I",
                "(d) P&I"));

        questionList.add(new Question("34. The measure of the power of an atom in its gaseous state in a molecule to attract an " +
                "electron to itself and become negatively charged is known as ………………..",
                "(a) electropositivity",
                "(b) ionization potential",
                "(c) electronegativity",
                "(d) electron affinity",
                "(c) electronegativity"));

        questionList.add(new Question("35. Down the group of a periodic table,electronegativity ………………",
                "(a) increases",
                "(b) decreases",
                "(c) remains constant",
                "(d) none of the above",
                "(b) decreases"));

        questionList.add(new Question("36. The amount of energy released when an atom,in its gaseous state,attract an electron to " +
                "itself and becomes negatively charged is called …………….",
                "(a) ionization energy",
                "(b) electronegativity",
                "(c) electropositivity",
                "(d) electron affinity",
                "(d) electron affinity"));

        questionList.add(new Question("37. Electron affinity ……………………. across the period and …………….. down the group.",
                "(a) increase,decrease",
                "(b) decrease,increase",
                "(c) decrease,decrease",
                "(d) increase,increase",
                "(a) increase,decrease"));

        questionList.add(new Question("38. From beryllium to chlorine,electronegativity ……………….",
                "(a) increase",
                "(b) decrease",
                "(c) all of the above",
                "(d) none of the above",
                "(a) increase"));

        questionList.add(new Question("39. From boron to carbon,electron affinity ……………………",
                "(a) increase",
                "(b) decrease",
                "(c) all of the above",
                "(d) none of the above",
                "(a) increase"));

        questionList.add(new Question("40. The amount of energy required to remove the most loosely bonded electron from an" +
                " atom in its gaseous state to become positively charged is known as is called ………………",
                "(a) electron affinity",
                "(b) ionization energy",
                "(c) electropositivity",
                "(d) electronegativity",
                "(b) ionization energy"));
    }

    private void getQuestionPhase3(List<Question> list) {

        questionList.add(new Question("1. The bond angle between two N-H bonds is about ____",
                "(a) 112°",
                "(b) 180°",
                "(c) 90°",
                "(d) 117°",
                "(d) 117°"));

        questionList.add(new Question("2. The bond angle between the lobes of sp3 orbitals is ",
                "(a) 120°",
                "(b) 180°",
                "(c) 109°28¹",
                "(d) 12°",
                "(c) 109°28¹"));

        questionList.add(new Question("2. As the bond length increases,the bond energy ………………",
                "(a) increases",
                "(b) decreases",
                "(c) all of the above",
                "(d) none of the above",
                "(b) decreases"));

        questionList.add(new Question("3. I. triple bond is shorter than double bond\n" +
                "II.triple bond is the shortest bond\n" +
                "III. double is longer than single bond\n" +
                "IV. single bond is the shortest\n" +
                "which of these is correct?",
                "(a) I & II",
                "(b) III & IV",
                "(c) I,II & IV",
                "(d) II,III & IV",
                "(a) I & II"));

        questionList.add(new Question("4. S0₄²- has …………….. resonance structures",
                "(a) 2",
                "(b) 4",
                "(c) 6",
                "(d) 5",
                "(c) 6"));

        questionList.add(new Question("5. The common table salt,NaCl,I s form by the reaction of elements frm",
                "(a) period III & period III ",
                "(b) group II and group VII ",
                "(c) group I & period I",
                "(d) group I & period VI",
                "(a) period III & period III "));

        questionList.add(new Question("6. The electron affinity ______ across the period",
                "(a) decreases",
                "(b) increases",
                "(c) both",
                "(d) stays constant",
                "(a) decreases"));

        questionList.add(new Question("7. Group 0 elements are also known as ____",
                "(a) atomic gases",
                "(b) noble gases",
                "(c) energy element",
                "(d) none of the above",
                "(b) noble gases"));

        questionList.add(new Question("8. The average amount of energy required to make or break one mole of a particular bond in " +
                "its gaseous state is _____",
                "(a) Bond energy",
                "(b) Ionic radii",
                "(c) Activation energy",
                "(d) Electronegative energy",
                "(a) Bond energy"));

        questionList.add(new Question("9. A monoatomic element,M,reacts with hydrogen,H2, to fomhydride.The formula for the compound is ………………",
                "(a) M₂H",
                "(b) MH₂",
                "(c) M₂H₂",
                "(d) MH",
                "(c) M₂H₂"));

        questionList.add(new Question("10. Group III elements have a valence electronic configuration of …………………. in their ground state ",
                "(a) ns²np¹",
                "(b) np¹ns²",
                "(c) ns¹np¹",
                "(d) ns²np²",
                "(a) ns²np¹"));

        questionList.add(new Question("11. ____ is the fundamental building block of a crystal",
                "(a) Solid structure",
                "(b) lattice",
                "(c) unit cell",
                "(d) cell arrangement",
                "(c) unit cell"));

        questionList.add(new Question("12. Calculate the formal charge ion in NH₄+",
                "(a) +2",
                "(b) +1",
                "(c) -1",
                "(d) -2",
                "(b) +1"));

        questionList.add(new Question("13. Which of the following compund is T-shaped?",
                "(a) NaCl",
                "(b) PbCl₅",
                "(c) HCL",
                "(d) ClF₃",
                "(d) ClF₃"));

        questionList.add(new Question("14. Group ……………….elements have the highest oxidizing ability",
                "(a) I",
                "(b) II",
                "(c) VII",
                "(d) VI",
                "(c) VII"));

        questionList.add(new Question("15. How many electrons can be accommodated in the n = 5 energy level?",
                "(a) 50",
                "(b) 40",
                "(c) 36",
                "(d) 10",
                "(a) 50"));

        questionList.add(new Question("16. Which of the following compounds is see saw shaped?",
                "(a) SF₄",
                "(b) H₃0+",
                "(c) ClF₃",
                "(d) HCl",
                "(a) SF₄"));

        questionList.add(new Question("17. Group ……………..elements have the highest reducing ability",
                "(a) I",
                "(b) II",
                "(c) VII",
                "(d) VI",
                "(a) I"));

        questionList.add(new Question("18. How many unpaired electrons can be found Fe3+?",
                "(a) 4",
                "(b) 3",
                "(c) 5",
                "(d) 6",
                "(c) 5"));

        questionList.add(new Question("19. A group I element,2M, reacts with halogen,X₂,to form………………",
                "(a) 2MX",
                "(b) 2M₂X₂",
                "(c) MX₂",
                "(d) MX",
                "(a) 2MX"));

        questionList.add(new Question("20. Which atom of the periodic table has 59 electrons?",
                "(a) Polonium",
                "(b) Chromium",
                "(c) Praseodymium",
                "(d) Fluorine",
                "(c) Praseodymium"));

        questionList.add(new Question("21. The oxidation number of sulphur in SO₄²- is ……………….",
                "(a) 4",
                "(b) 2",
                "(c) 6",
                "(d) -2",
                "(c) 6"));

        questionList.add(new Question("22. The compound known as laughing gas has a chemical formula of …………",
                "(a) NO₂",
                "(b) NO",
                "(c) N₂O₂",
                "(d) N₂O",
                "(d) N₂O"));

        questionList.add(new Question("23. The following contain coordinate covalent bonds except",
                "(a) BH₄-",
                "(b) BeCl₄²",
                "(c) PH₄",
                "(d) CCl₄",
                "(c) PH₄"));

        questionList.add(new Question("24. What is the ionic valence of Se?",
                "(a) +1",
                "(b) -3",
                "(c) -2",
                "(d) +2",
                "(c) -2"));

        questionList.add(new Question("25. What angle exist between the hybrid orbital in a SP2?",
                "(a) 180°",
                "(b) 120°",
                "(c) 90°",
                "(d) 60°",
                "(b) 120°"));

        questionList.add(new Question("26. The shape of an ammonia molecule is ____",
                "(a) Dumb bell",
                "(b) Trigonal",
                "(c) Pyramidal",
                "(d) Round",
                "(c) Pyramidal"));

        questionList.add(new Question("27. When an alkali metal reacts with water,……………… is liberated",
                "(a) water",
                "(b) oxygen",
                "(c) hydrogen",
                "(d) neon",
                "(c) hydrogen"));

        questionList.add(new Question("28. The kind of hybridization of carbon in CO₂ is ",
                "(a) sp²",
                "(b) sp",
                "(c) spd",
                "(d) sp²d",
                "(b) sp"));

        questionList.add(new Question("29. The shape of sp hybridization is ",
                "(a) linear",
                "(b) tetra-hydral",
                "(c) trigonal",
                "(d) pyramidal",
                "(a) linear"));

        questionList.add(new Question("30. PbO is more stable than PbO₂ as a result of …………………..",
                "(a) electronegativities",
                "(b) inert pair effect",
                "(c) reaction gradient",
                "(d) availability of oxygen",
                "(b) inert pair effect"));

        questionList.add(new Question("31. Which of the following can be lewis base?",
                "(a) H₂O",
                "(b) Ni²+",
                "(c) CO",
                "(d) Mn",
                "(a) H₂O"));

        questionList.add(new Question("32. The sp² hybridization has ___ sigma bond",
                "(a) 2",
                "(b) 1",
                "(c) 3",
                "(d) 0",
                "(b) 1"));

        questionList.add(new Question("33. Which of the following does not have a dipole moment?",
                "(a) H₂O",
                "(b) HCCl₃",
                "(c) I₂",
                "(d) AgI",
                "(c) I₂"));

        questionList.add(new Question("34. NO₃- has a resonance structure of ……………….",
                "(a) 2",
                "(b) 3",
                "(c) 5",
                "(d) 7",
                "(b) 3"));

        questionList.add(new Question("35. Which of the hybridization has 1 s- orbital and 1 p-orbital.",
                "(a) sp²",
                "(b) sp",
                "(c) sp²d",
                "(d) spd",
                "(b) sp"));

        questionList.add(new Question("36. Nitrogen and the members of its group has a valence electronic configuration of ………………….",
                "(a) ns²np³",
                "(b) ns³np²",
                "(c) ns¹np¹",
                "(d) ns²np²",
                "(a) ns²np³"));

        questionList.add(new Question("37. Which of the following contain pure covalent bond?",
                "(a) H₂S",
                "(b) NCl₅",
                "(c) SeO₃",
                "(d) TeBr₄",
                "(a) H₂S"));

        questionList.add(new Question("38. BF₃ has a ______ shape",
                "(a) Angular",
                "(b) linear",
                "(c) Trigonal Planar",
                "(d) Pyramidal",
                "(c) Trigonal Planar"));

        questionList.add(new Question("39. A water molecule has a bond angle of _____",
                "(a) 120°",
                "(b) 104°",
                "(c) 180°",
                "(d) 210°",
                "(b) 104°"));

        questionList.add(new Question("40. The least electronegative element on the periodic table is ____",
                "(a) Florine",
                "(b) Hydrogen",
                "(c) Copper",
                "(d) Caesium",
                "(d) Caesium"));
    }
}



