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

public class Gns114Activity extends AppCompatActivity {

    private List<Question> questionList;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1, clicked = 0;
    Button btnNext, btnPrev, btnEnd, btnAnswer;
    private AlertDialog.Builder dialog;
    private boolean mTimerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chm132);

        questionList = new ArrayList<>();
        questionText = findViewById(R.id.questionText);
        answerText = findViewById(R.id.txtAnswer);
        btnAnswer = findViewById(R.id.btnAnswer);
        btnEnd = findViewById(R.id.buttonGoto);
        rbOption1 = findViewById(R.id.radioA);
        rbOption2 = findViewById(R.id.radioB);
        rbOption3 = findViewById(R.id.radioC);
        rbOption4 = findViewById(R.id.radioD);
        questionNo = findViewById(R.id.question1);
        countDown = findViewById(R.id.timeText);

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

        if (SecondSemesterActivity.questionRequestCode == 1)
        {
            getQuestionPhase(questionList);

            setDataView(pos);
        }
        else if
        (SecondSemesterActivity.questionRequestCode == 2)
        {
            getQuestionPhase2(questionList);

            setDataView(pos);
        }
        else if
        (SecondSemesterActivity.questionRequestCode == 3)
        {
            getQuestionPhase3(questionList);

            setDataView(pos);
        }
        else if (SecondSemesterActivity.questionRequestCode == 4)
        {
            getQuestionPhase4(questionList);

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

    private boolean setDataView(int position) {
        questionText.setText(questionList.get(position).getQuestion());

        rbOption1.setText(questionList.get(position).getOption1());
        rbOption2.setText(questionList.get(position).getOption2());
        rbOption3.setText(questionList.get(position).getOption3());
        rbOption4.setText(questionList.get(position).getOption4());
        answerText.setText(questionList.get(position).getAnswer());

        questionNo.setText("Question "+questionAnswered+" of 30");
        return false;

    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }


    private void getQuestionPhase(List<Question> list) {

        questionList.add(new Question( "1. Two devices are in network if",
                "a) a process in one device is able to exchange information with a process in another device",
                "b) a process is running on both devices",
                "c) PIDs of the processes running of different devices are same",
                "d) none of the mentioned",
                "a) a process in one device is able to exchange information with a process in another device"));

        questionList.add(new Question( "2. What is a web browser?",
                "a) a program that can display a webpage",
                "b) a program used to view html documents",
                "c) it enables user to access the resources of internet",
                "d) all of the mentioned",
                "d) all of the mentioned"));

        questionList.add(new Question( "3. URL stands for",
                "a) unique reference label",
                "b) uniform reference label",
                "c) uniform resource locator",
                "d) unique resource locator",
                "c) uniform resource locator"));

        questionList.add(new Question("4. What is internet?",
                "a) a single network",
                "b) a vast collection of different network",
                "c) interconnection of local area network",
                "d) none of the mentioned",
                "b) a vast collection of different network"));

        questionList.add(new Question("5. Which of the following is a correct format of Email address?",
                "a) name@website@info",
                "b) name@website.info",
                "c) www.nameofebsite.com",
                "d) name.website.com",
                "b) name@website.info"));

        questionList.add(new Question("6. HTML is used to create",
                "a) machine language program",
                "b) high level program",
                "c) web page" ,
                "d) web server", "c) web page"));

        questionList.add(new Question("7. The computer jargon - WWW,stands for",
                "a) World Wide Web", "b) World Wide Wildlife",
                "c) World Wide Women's", "d) World Wide Women's",
                "a) World Wide Web"));

        questionList.add(new Question("8. The process of transferring files from a computer on the Internet to your" +
                "computer is called",
                "a) Uploading", "b) Forwarding",
                "c) FTP",
                "d) Downloading",
                "d) Downloading"));

        questionList.add(new Question("9. In internet terminology IP means",
                "a) Internet Provider",
                "b) Internet Protocol",
                "c) Internet Procedure",
                "d) Internet Processor",
                "b) Internet Protocol"));

        questionList.add(new Question("10. Which one of the following is not a search engine??",
                "a) Bing",
                "b) Google",
                "c) Yahoo",
                "d) Windows", "d) Windows"));

        questionList.add(new Question("11. Verification of a login name and password is known as" ,
                "a) configuration",
                "b) accessibility" ,
                "c) authentication",
                "d) logging in",
                "c) authentication"));

        questionList.add(new Question( "12. Internet explorer falls under:",
                "a) Operating System",
                "b) Compiler",
                "c) Browser",
                "d) IP address",
                "c) Browser"));

        questionList.add(new Question( "13. What is the full form of WWW in web address?",
                "a) World Wide Web",
                "b) World Wide Word",
                "c) World Wide Wood",
                "d) None of these",
                "a) World Wide Web"));

        questionList.add(new Question( "14. Full form of HTML is:",
                "a) Hyper Text Markup Language",
                "b) Hyper Text Manipulation Language",
                "c) Hyper Text Managing Links",
                "d) Hyper Text Manipulating Links",
                "a) Hyper Text Markup Language"));

        questionList.add(new Question( "15. Moving from one website to another is called:",
                "a) Downloading",
                "b) Browsing",
                "c) Uploading",
                "d) Attachment",
                "b) Browsing"));

        questionList.add(new Question( "16. A computer on internet are identified by:",
                "a) e-mail address",
                "b) street address",
                "c) IP address",
                "d) None of the above",
                "c) IP address"));

        questionList.add(new Question( "17. ___________programs automatically connect to websites and download " +
                "documents and save them to local drive.",
                "a) None of these",
                "b) Web Downloading Utilities",
                "c) Offline Browser",
                "d) Web Server",
                "c) Offline Browser"));

        questionList.add(new Question( "18. Which of the following website will give you details of Local Shops",
                "a) www.c4learn.com",
                "b) www.justdial.com",
                "c) www.google.com",
                "d) None of these",
                "c) www.google.com"));

        questionList.add(new Question( "19. An example of an Optical Storage device is:",
                "a) Magnetic Tapes",
                "b) USB Disk",
                "c) Floppy Disk",
                "d) DVD",
                "d) DVD"));

        questionList.add(new Question( "20. What unit of storage is used to represent 1,073,741,824 bytes?",
                "a) Kilobyte",
                "b) Terabyte",
                "c) Megabyte",
                "d) Gigabyte",
                "d) Gigabyte"));

        questionList.add(new Question( "21. What unit of storage is used to represent 1,024 bytes?",
                "a) Kilobyte",
                "b) Terabyte",
                "c) Megabyte",
                "d) Gigabyte",
                "a) Kilobyte"));

        questionList.add(new Question( "22. What unit of storage is used to represent 1,048,576 bytes",
                "a) Kilobyte",
                "b) Terabyte",
                "c) Megabyte",
                "d) Gigabyte",
                "b) Terabyte"));

        questionList.add(new Question( "23. Memory stores:",
                "a) the operating system",
                "b) application programs",
                "c) the data being processed by the application programs",
                "d) all of the above",
                "d) all of the above"));

        questionList.add(new Question( "24. Bit is short for:",
                "a) Binary system",
                "b) Digital byte",
                "c) Binary unit",
                "d) Binary digit",
                "d) Binary digit"));

        questionList.add(new Question( "25. ___________is used to store frequently used data and instruction for faster " +
                "access and processing.",
                "a) Cache memory",
                "b) Virtual memory",
                "c) Flash memory",
                "d) All of the above",
                "a) Cache memory"));

        questionList.add(new Question( "26. The number of bits that can be uniquely addressed by the computer\n",
                "a) byte",
                "b) cycle",
                "c) register",
                "d) word",
                "a) byte"));

        questionList.add(new Question( "27. Input may be in the following",
                "a) OCR, MICR and CPU",
                "b) OMR, VDU and OCR",
                "c) OCR, MICR and OMR",
                "d) VDU, SVGA and XVGA",
                "c) OCR, MICR and OMR"));

        questionList.add(new Question( "28. Which of the following statements is true?",
                "a) The digitizing tablet is an input device",
                "b) The light pen is an input device",
                "c) The mouse is an output device",
                "d) The monitor is not an output device",
                "b) The light pen is an input device"));

        questionList.add(new Question( "29. The nature of data that determines the hybrid type of computer is/are",
                "a) Analogue",
                "b) Digital",
                "c) Synchronous",
                "d) Analogue and digital",
                "d) Analogue and digital"));

    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question( "1. Computer that requests the resources or data from other computer is called as" +
                "________computer.",
                "a) Server",
                "b) Client",
                "c) Filter",
                "d) Pusher",
                "b) Client"));

        questionList.add(new Question( "2. Software which allows user to view the webpage is called as__________.",
                "a) Interpreter",
                "b) Internet Browser",
                "c) Website",
                "d) OperatingSystem",
                "b) Internet Browser"));

        questionList.add(new Question( "3. With regards to e-mail addresses",
                "a) they must always contain an @ symbol",
                "b) they can never contain spaces",
                "c) they are case-insensitive",
                "d) all of the above",
                "d) all of the above"));

        questionList.add(new Question( "4. Which of the following website is used to search other website by typing a keyword?",
                "a) Social Networks",
                "b) None of these",
                "c) Routers",
                "d) Search Engine",
                "d) Search Engine"));

        questionList.add(new Question( "5. Google (www.google.com) is a",
                "a) Number in Math",
                "b) Search Engine",
                "c) Chat service on the web",
                "d) Directory of images",
                "b) Search Engine"));

        questionList.add(new Question( "6. At which of the following sites would you most probably buy books?",
                "a) www.amazon.com",
                "b) www.hotmail.com",
                "c) www.sun.com",
                "d) www.msn.com",
                "a) www.amazon.com"));

        questionList.add(new Question( "7. ISP stands for",
                "a) Integrated Service Provider",
                "b) Internet Service Provider",
                "c) Internet Security Protocol",
                "d) Internet Survey Period",
                "b) Internet Service Provider"));

        questionList.add(new Question( "8. Applets are written in______________programming language.",
                "a) C++",
                "b) Java",
                "c) C",
                "d) C#",
                "b) Java"));

        questionList.add(new Question( "9. HTML is used to",
                "a) Author web page",
                "b) Plot Complicated Graph",
                "c) Solve equation",
                "d) Transfer one language to another",
                "a) Author web page"));

        questionList.add(new Question( "10. Which term describes hardware or software that protects your computer or" +
                "network from probing or malicious users?",
                "a) Router",
                "b) Firewall",
                "c) Protocol",
                "d) Spyware",
                "b) Firewall"));

        questionList.add(new Question( "11. Search engine are used to________\n",
                "a) Search Videos",
                "b) Search for information on the World Wide Web",
                "c) All of these",
                "d) Search Documents",
                "c) All of these"));


        questionList.add(new Question( "12. Which of the following is used to explore the Internet?",
                "a) Browser",
                "b) Spreadsheet",
                "c) Clipboard",
                "d) Draw",
                "a) Browser"));

        questionList.add(new Question( "13. What is Internet Explorer?",
                "a) An Icon",
                "b) A File Manager",
                "c) A Browser",
                "d) The Internet",
                "c) A Browser"));

        questionList.add(new Question( "14. What do I need to get on to the Internet?",
                "a) Computer",
                "b) Modem",
                "c) Browser",
                "d) All of the above",
                "d) All of the above"));

        questionList.add(new Question( "15. What is an ISP?",
                "a) Internet System Protocol",
                "b) Internal System Program",
                "c) Internet Service Provider",
                "d) None of the above",
                "c) Internet Service Provider"));

        questionList.add(new Question( "16. Which is not a domain name extensio",
                "a) .mil",
                "b) .org",
                "c) .int",
                "d) .com",
                "c) .int"));

        questionList.add(new Question( "17. What is a FTP program used for?",
                "a) Transfer files to and from an Internet Server",
                "b) Designing a website",
                "c) Connecting to the internet",
                "d) None of the above",
                "a) Transfer files to and from an Internet Server"));

        questionList.add(new Question( "18. TCP/IP is a:",
                "a). Network Hardware",
                "b) Network Software",
                "c) Protocol",
                "d) None of these",
                "c) Protocol"));

        questionList.add(new Question( "19. TCP/IP mainly used for:",
                "a) File Transfer",
                "b) Email",
                "c) Remote Login Service",
                "d) All of these",
                "d) All of these"));

        questionList.add(new Question( "20. Which protocol is used for browsing website?",
                "a) TCP",
                "b) HTTP",
                "c) FTP",
                "d) TFTP",
                "b) HTTP"));

        questionList.add(new Question( "21. Which is not the browser?",
                "a) Internet Explorer",
                "b) Opera",
                "c) Mozilla",
                "d) Google",
                "d) Google"));

        questionList.add(new Question( "22. Which of the following lists the different type of networks in ascending " +
                "geographical area?",
                "a) LAN, WAN, MAN",
                "b) LAN, MAN, WAN",
                "c) MAN, LAN, WAN",
                "d) WAN, LAN, MAN",
                "b) LAN, MAN, WAN"));

        questionList.add(new Question( "22. All of the following are examples of computer input units EXCEPT:",
                "a) Scanner",
                "b) Speaker",
                "c) Bar code reader",
                "d) Keyboard",
                "b) Speaker"));

        questionList.add(new Question( "23. Which of the following units is the biggest capacity:",
                "a) Byte",
                "b) Kilobyte",
                "c) Gigabyte",
                "d) Megabyte",
                "c) Gigabyte"));

        questionList.add(new Question( "24. Which of the following devices can be used to directly input printed text",
                "a) OCR",
                "b) OMR\n",
                "c) MICR\n",
                "d) None of the above",
                "a) OCR"));

        questionList.add(new Question( "25. Which of the following does not represent on I/O device",
                "a) speaker",
                "b) joystick",
                "c) plotter",
                "d) ALU",
                "d) ALU"));

        questionList.add(new Question( "26. One thousand and twenty four bytes represent a",
                "a) Megabyte",
                "b) Gigabyte",
                "c) Kilobyte",
                "d) Terabyte",
                "c) Kilobyte"));

        questionList.add(new Question( "27. Third generation computers",
                "a) Were the first to use built-in error detecting device\n",
                "b) Used integrated circuit instead of vacuum tubes",
                "c) Were the first to use neural network",
                "d) used micro processor",
                "b) Used integrated circuit instead of vacuum tubes"));

        questionList.add(new Question( "28. Computer follows a simple principle called GIGO which means:",
                "a) garbage input good output",
                "b) garbage in garbage out",
                "c) great instructions great output",
                "d) good input good output",
                "b) garbage in garbage out"));

        questionList.add(new Question( "29. Multiple choice examination answer sheets can be evaluated automatically by",
                "a) Optical Mark Reader",
                "b) Optical Character Reader",
                "c) Magnetic tape reader",
                "d) Magnetic ink character reader.",
                "a) Optical Mark Reader"));

        questionList.add(new Question( "30. Right clicking something in Excel:",
                "a) Deletes the object",
                "b) Nothing the right mouse button is there for left handed people",
                "c) Opens a shortcut menu listing everything you can do to the object",
                "d) Selects the object",
                "c) Opens a shortcut menu listing everything you can do to the object"));

        questionList.add(new Question( "31. Files created with Excel have an extension",
                "a) DOC",
                "b) XLS",
                "c) 123",
                "d) WK1",
                "b) XLS"));

        questionList.add(new Question( "32. To delete an embedded objects,first",
                "a) Double click the object",
                "b) Select the object by clicking it",
                "c) Press the Shift + Delete keys",
                "d) Select it and then press the delete key",
                "d) Select it and then press the delete key"));

        questionList.add(new Question( "33. Which of the following is not a worksheet design criterion?",
                "a) Efficiency",
                "b) Aditibility",
                "c) Description",
                "d) Clarity",
                "c) Description"));

        questionList.add(new Question( "34. To copy cell contents using drag and drop press the",
                "a) End key",
                "b) Shift key",
                "c) Ctrl key",
                "d) Esc key",
                "d) Esc key"));

        questionList.add(new Question( "35. Which menu option can be used to split windows into two",
                "a) Format > window",
                "b) View > window > split",
                "c) Window > split",
                "d) View > split",
                "c) Window > split"));

        questionList.add(new Question( "36. Each excel file is called a workbook because",
                "a) It can contain text and data",
                "b) It can be modified",
                "c) It can contain many sheets including worksheets and chart sheets",
                "d) You have to work hard to create it",
                "c) It can contain many sheets including worksheets and chart sheets"));

        questionList.add(new Question( "37. Excel probably considers the cell entry January 1, 2000 to be a",
                "a) Label",
                "b) Value",
                "c) Formula",
                "d) Text string",
                "b) Value"));

        questionList.add(new Question( "38. You can enter which types of data into worksheet cells?",
                "a) Labels, values, and formulas",
                "b) Labels and values but not formulas",
                "c) Values and formulas but not labels",
                "d) Formulas only",
                "a) Labels, values, and formulas"));

        questionList.add(new Question( "39. Which of the following is a correct order of precedence in formula calculation?",
                "a) Multiplication and division exponentiation positive and negative values",
                "b) Multiplication and division, positive and negative values, addition and subtraction",
                "c) Addition and subtraction, positive and negative values, exponentiation",
                "d) All of the above",
                "d) All of the above"));

        questionList.add(new Question( "40. Which of the following is not a valid data type in excel",
                "a) Number",
                "b) Character",
                "c) Label",
                "d) Date/time",
                "b) Character"));

        questionList.add(new Question( "41. Which of the following options is not located in the Page Setup dialog box?",
                "a) Page Break Preview.",
                "b) Page Orientation",
                "c) Margins",
                "d) Headers and Footers",
                "a) Page Break Preview."));

        questionList.add(new Question( "42. Which of these best describes the Computer:",
                "a) Works through an interaction of hardware and software",
                "b) Works through an interaction of hardware only",
                "c) Works through an interaction of software only",
                "d) All of the above",
                "a) Works through an interaction of hardware and software"));

        questionList.add(new Question( "43. Files in powerpoint are saved by default as:",
                "a) document",
                "b) presentation",
                "c) sheet or book",
                "d) All of the above",
                "b) presentation"));

        questionList.add(new Question( "44. The following are output device(s) except:",
                "a) Monitor",
                "b) Speaker",
                "c) Projector",
                "d) None of the above",
                "d) None of the above"));

        questionList.add(new Question( "45. Which of these can be used in animation design for simulation studies?",
                "a) graphics based software using three dimensional structures",
                "b) statistical based software using central tendences",
                "c) word document",
                "d) All of the above",
                "a) graphics based software using three dimensional structures"));

        questionList.add(new Question( "46. The following key(s) maybe described as components of the keyboard except:",
                "a) Backspace",
                "b) Tab key",
                "c) Caps lock",
                "d) None of the above",
                "d) None of the above"));

        questionList.add(new Question( "47. Which of these applies to the term ”EMR”?",
                "a) Electrical medical record",
                "b) Electronic medical record",
                "c) Electric medical record",
                "d) All of the above",
                "b) Electronic medical record"));

        questionList.add(new Question( "48. Files in word format are saved by default as?",
                "a) document",
                "b) presentation",
                "c) sheet or book",
                "d) All of the above",
                "a) document"));

        questionList.add(new Question( "49. Telemedicine may be defined as:",
                "a) Use of ICT for the delivery of clinical care",
                "b) Use of clinics for the delivery of clinical care",
                "c) Use of Hospitals for the delivery of clinical care",
                "d) All of the above",
                "a) Use of ICT for the delivery of clinical care"));

        questionList.add(new Question( "50. Which of these applies to asynchronous Telemedicine?",
                "a. Does not require the presence of both parties for a real-time interaction",
                "b. Requires the presence of two or more parties that allows a real-time interaction",
                "c. Requires the presence of both parties that allows a real-time interaction",
                "d. All of the above",
                "a. Does not require the presence of both parties for a real-time interaction"));

    }

    private void getQuestionPhase3(List<Question> list) {

        questionList.add(new Question( "1. Internet is .............................",
                "a) a network of networks",
                "b) an ocean of resources waiting to be mined",
                "c) a cooperative anarchy",
                "d) all of the above",
                "d) all of the above"));

        questionList.add(new Question( "2. ________ programs are automatically loaded and operates as a part of browser.\n",
                "a) Plug-ins",
                "b) Add-ons",
                "c) Utilities",
                "d) Widgets",
                "a) Plug-ins"));

        questionList.add(new Question( "3. Which of the following protocol is used for e-mail services?",
                "a) SMAP",
                "b) SMTP",
                "c) SMIP",
                "d) SMOP",
                "b) SMTP"));

        questionList.add(new Question( "3. .......................is a uniform naming scheme for locating resources on the web",
                "a) URL",
                "b) HTTP",
                "c) WEBNAME",
                "d) RESOURCENAME",
                "a) URL"));

        questionList.add(new Question("4. To join the internet,the computer has to be connected to a",
                "a) internet architecture board",
                "b) internet society",
                "c) internet service provider",
                "d) none of the mentioned",
                "c) internet service provider"));

        questionList.add(new Question("5. Internet access by transmitting digital data over the wires of a local telephone" +
                "network is provided by",
                "a) leased line",
                "b) digital subscriber line",
                "c) digital signal line",
                "d) none of the mentioned",
                "b) digital subscriber line"));

        questionList.add(new Question("6. ISP exchanges internet traffic between their networks by",
                "a) internet exchange point",
                "b) subscriber end point",
                "c) ISP endpoint",
                "d) none of the mentioned",
                "a) internet exchange point"));

        questionList.add(new Question( "7. Which is not the search engine:",
                "a) Altavista.com",
                "b) Google.com",
                "c) Facebook.com",
                "d) Yahoo.com",
                "c) Facebook.com"));

        questionList.add(new Question( "8. Email stands for:",
                "a) Easy mail",
                "b) Electronic mail",
                "c) Electric mail",
                "d) None of these",
                "b) Electronic mail"));

        questionList.add(new Question( "9. Which is the chatting application",
                "a) Yahoo messenger",
                "b) Google earth",
                "c) Youtube",
                "d) None of these",
                "a) Yahoo messenger"));

        questionList.add(new Question( "10. Which is not the application of internet:",
                "a) Communication",
                "b) Banking",
                "c) Shopping",
                "d) Surfing",
                "d) Surfing"));

        questionList.add(new Question( "11. Which is the advantage of e-business:",
                "a) Better Service",
                "b) Reduction of cost",
                "c) Reduction of paperwork",
                "d) All of these",
                "d) All of these"));

        questionList.add(new Question( "12. The following are peripheral media devices except?",
                "a) speakers",
                "b) projector",
                "c) web camera",
                "d) None of the above",
                "d) None of the above"));

        questionList.add(new Question( "13. An operating system",
                "a) is not required on large computers",
                "b) is always supplied with the computer",
                "c) is always supplied with the BASIC",
                "d) is a system software",
                "d) is a system software"));

        questionList.add(new Question( "14. Which of the following would cause quickest access",
                "a) direct access from a cache",
                "b) direct access from a hard disk",
                "c) direct access from a floppy disk",
                "d) direct access from a memory",
                "a) direct access from a cache"));

        questionList.add(new Question( "15. The process of retaining data for future use is called",
                "a) reading",
                "b) writing",
                "c) storing",
                "d) coding",
                "c) storing"));

        questionList.add(new Question( "16. _________is a interconnection of computers that facilitates the sharing of "+
                "information "+
                "between computing devices",
                "a) network",
                "b) peripheral",
                "c) expansion board",
                "d) digital device",
                "a) network"));

        questionList.add(new Question( "17. Which of the following statements is true?",
                "a) Mini computer works faster than Micro computer",
                "b) Micro computer works faster than Mini computer",
                "c) Speed of both the computers is the same",
                "d) The speeds of both these computers cannot be compared with the speed " +
                "of advanced",
                "b) Micro computer works faster than Mini computer"));

        questionList.add(new Question( "18. Where are data and programme stored when the processor uses them?",
                "a) Main memory",
                "b) Secondary memory",
                "c) Disk memory",
                "d) Programme memory",
                "a) Main memory"));

        questionList.add(new Question( "19. _______ represents raw facts, whereas________ is data made meaningful.",
                "a) Information,reporting",
                "b) Data,information",
                "c) Information,bits",
                "d) Records,bytes",
                "b) Data,information"));

        questionList.add(new Question( "21. Which device is required for the Internet connection?",
                "a) Joystick",
                "b) VDU",
                "c) CDDrive",
                "d) Distributor",
                "d) Distributor"));

        questionList.add(new Question( "22. What is a light pen?",
                "a) Mechanical Input device",
                "b) Optical input device",
                "c) Electronic input device",
                "d) Optical output device",
                "b) Optical input device"));

        questionList.add(new Question( "23. When you insert an Excel file into a Word document,the data are",
                "a) Hyper linked",
                "b) Placed in a word table",
                "c) Linked",
                "d) Embedded",
                "b) Placed in a word table"));

        questionList.add(new Question( "24. Each excel file is called a workbook because",
                "a) It can contain text and data",
                "b) It can be modified",
                "c) It can contain many sheets including worksheets and chart sheets",
                "d) You have to work hard to create it",
                "c) It can contain many sheets including worksheets and chart sheets"));

        questionList.add(new Question( "25. Which types of charts can excel produce?",
                "a) Line graphs and pie charts only",
                "b) Only line graphs",
                "c) Bar charts, line graphs and pie charts",
                "d) Bar charts and line graphs only",
                "c) Bar charts, line graphs and pie charts"));

        questionList.add(new Question( "26. How are data organized in a spreadsheet?",
                "a) Lines and spaces",
                "b) Layers and planes",
                "c) Rows and columns",
                "d) Height and width",
                "c) Rows and columns"));

        questionList.add(new Question( "27. You can print only an embedded chart by",
                "a) Moving the chart to a chart sheet before you print",
                "b) Formatting the chart before you print",
                "c) Selecting the chart before you print",
                "d) a and c",
                "d) a and c"));

        questionList.add(new Question( "29. How should you print a selected area of a worksheet, if you’ll want to print a "+
                "different area next time?",
                "a) On the file menu, point to print area, and then click set print area)",
                "b) On the file menu, click print, and then click selection under print what",
                "c) On the view menu, click custom views, then click add",
                "d) All of above",
                "b) On the file menu, click print, and then click selection under print what"));

        questionList.add(new Question( "30. Which of the following methods cannot be used to enter data in a cell?",
                "a) Pressing an arrow key",
                "b) Pressing the tab key",
                "c) Pressing the Esc key",
                "d) Clicking the enter button to the formula bar",
                "c) Pressing the Esc key"));

        questionList.add(new Question( "31. Which of the following methods cannot be used to edit the content of cell?",
                "a) Pressing the Alt key",
                "b) Clicking the formula bar",
                "c) Pressing F2",
                "d) Double clicking the cell",
                "a) Pressing the Alt key"));

        questionList.add(new Question( "32. This data type allows alphanumeric characters and special symbols.",
                "a) text",
                "b) memo",
                "c) auto number",
                "d) None of the above",
                "a) text"));

        questionList.add(new Question( "33. Query design window has two parts. The upper part shows",
                "a) Name of fields, field type and size",
                "b) Tables with fields and relationships between tables",
                "c) Criteria",
                "d) Sorting check boxes",
                "b) Tables with fields and relationships between tables"));

        questionList.add(new Question( "34. In a database table, the category of information is called ____________",
                "a) tuple",
                "b) field",
                "c) record",
                "d) All of the above",
                "b) field"));

        questionList.add(new Question( "35. To create a new table, in which method you don’t need to specify the field type and size?",
                "a) Create table in Design View",
                "b) Create Table using wizard",
                "c) Create Table by Entering data",
                "d) All of the above",
                "d) All of the above"));

        questionList.add(new Question( "36. Which of the following is not a database object?",
                "a) Tables",
                "b) Queries",
                "c) Relationships",
                "d) Reports",
                "c) Relationships"));

        questionList.add(new Question( "37. The command center of access file that appears when you " +
                "create or open the MS Access database file.",
                "a) Database Window",
                "b) Query Window",
                "c) Design View Window",
                "d) Switch board",
                "a) Database Window"));

        questionList.add(new Question( "38. When creating a new table which method can be used to choose fields from" +
                " standard databases and tables",
                "a) Create table in Design View",
                "b) Create Table using wizard",
                "c) Create Table by Entering data",
                "d) None of above",
                "c) Create Table by Entering data"));

        questionList.add(new Question( "39. Which field type will you select when creating a new table if you require to enter" +
                " long text in that field?",
                "a) Text",
                "b) Memo",
                "c) Currency",
                "d) Hyperlink",
                "b) Memo"));

        questionList.add(new Question( "40. In table design view what are the first column of buttons used for",
                "a) Indicate Primary Key",
                "b) Indicate Current Row",
                "c) Both of above",
                "d) None of above",
                "a) Indicate Primary Key"));

        questionList.add(new Question( "41. Which of the following is not a type of relationship that can be applied in Access database",
                "a) One to One",
                "b) One to Many",
                "c) Many to Many",
                "d) All of above can be applied",
                "d) All of above can be applied"));

        questionList.add(new Question( "42. Selecting text means, selecting the following except …………..?",
                "a) a word",
                "b) an entire sentence",
                "c) whole document",
                "d) audio",
                "d) audio"));

        questionList.add(new Question( "43. MS-Word automatically moves the text to the next line when it reaches the right" +
                " edge of the screen and is called?",
                "a) Carriage Return",
                "b) Enter",
                "c) Word Wrap",
                "d) Carriage Release",
                "c) Word Wrap"));

        questionList.add(new Question( "44. Using Find command in Word, we can search for the following except……..",
                "a) characters",
                "b) formats",
                "c) symbols",
                "d) music",
                "d) music"));

        questionList.add(new Question( "45. In MS-Word, ruler help for the following except……",
                "a) to set tabs",
                "b) to set indents",
                "c) to change page margins",
                "d) to review",
                "d) to review"));

        questionList.add(new Question( "46. By default, on which page the header or the footer is printed?",
                "a) on first page",
                "b) on alternate page",
                "c) one very page",
                "d) on even pages",
                "c) one very page"));

        questionList.add(new Question( "47. Which menu in MS-Word can be used to change character size and typeface?",
                "a) View",
                "b) Tools",
                "c) Format",
                "d) Data",
                "c) Format"));

        questionList.add(new Question( "48. Which key should be pressed to start a new paragraph in MS-Word?",
                "a) Down Cursor Key",
                "b) Enter Key",
                "c) Shift + Enter",
                "d) Ctrl + Enter",
                "b) Enter Key"));

        questionList.add(new Question( "49. Which of these toolbars allows changing of Fonts and their sizes?",
                "a) Standard",
                "b) Formatting",
                "c) Print Preview",
                "d) None of these",
                "b) Formatting"));

        questionList.add(new Question( "50. Which bar is usually located below that Title Bar that provides categorized options?",
                "a) Menu bar",
                "b) Status Bar",
                "c) Tool bar",
                "d) Scroll bar",
                "a) Menu bar"));

    }

    private void getQuestionPhase4(List<Question> list) {

        questionList.add(new Question( "1. The Software which contains rows and columns is called______",
                "a) Database",
                "b) Drawing",
                "c) Spreadsheet",
                "d) Word processing",
                "c) Spreadsheet"));

        questionList.add(new Question( "2. Which of the following methods cannot be used to enter data in a cell?",
                "a) Pressing a narrow key",
                "b) Pressing the Tab key",
                "c) Pressing the Esc key",
                "d) Clicking on the formula bar",
                "c) Pressing the Esc key"));

        questionList.add(new Question( "3. Which of the following will not cut information?",
                "a) Pressing Ctrl+C",
                "b) Selecting Edit>Cut from the menu",
                "c) Clicking the Cut button on the standard",
                "d) Pressing Ctrl+X",
                "a) Pressing Ctrl+C"));

        questionList.add(new Question( "4. Which of the following is not a way to complete a cell entry?",
                "a) Pressing enter",
                "b) Pressing any arrow key on the keyboard",
                "c) Clicking the Enter button on the Formula bar",
                "d) Pressing space bar",
                "d) Pressing space bar"));

        questionList.add(new Question( "5. You can activate a cell by",
                "a) Pressing the Tab key",
                "b) Clicking the cell",
                "c) Pressing an arrow key",
                "d) All of the above",
                "d) All of the above"));

        questionList.add(new Question( "6. How do you insert a row?",
                "a) Right-click the row heading where you want to insert the new row and select Insert from the shortcut menu",
                "b) Select the row heading where you want to insert the new row and select Edit " +
                "> Row from the menu",
                "c) Select the row heading where you want to insert the new row and click the Insert" +
                "Row button on the standard tool bar",
                "d) All of the above",
                "a) Right-click the row heading where you want to insert the new row and select Insert from the shortcut menu"));

        questionList.add(new Question( "7. Which of the following is not a basic step in creating a worksheet?",
                "a) Save workbook",
                "b) Modify the worksheet",
                "c) Enter text and data",
                "d) Copy the worksheet",
                "d) Copy the worksheet"));

        questionList.add(new Question( "8. How do you select an entire column",
                "a) Select Edit > Select > Column from the menu",
                "b) Click the column heading letter",
                "c) Hold down the shift key as you click anywhere in the column.",
                "d) Hold down the Ctrl key as you click anywhere in the column",
                "b) Click the column heading letter"));

        questionList.add(new Question( "9. To create a formula, you first:",
                "a) Select the cell you want to place the formula into",
                "b) Type the equals sign (=) to tell Excel that you’re about to enter a formula",
                "c) Enter the formula using any input values and the appropriate mathematical" +
                " operators that make up your formula",
                "d) Choose the new command from the file menu",
                "a) Select the cell you want to place the formula into"));

        questionList.add(new Question( "10. To center worksheet titles across a range of cells,you must",
                "a) Select the cells containing the title text plus the range over which the title text is to be centered",
                "b) Widen the columns",
                "c) Select the cells containing the title text plus the range over which the title text is to be enfettered",
                "d) Format the cells with the comma style",
                "a) Select the cells containing the title text plus the range over which the title text is to be centered"));

        questionList.add(new Question( "11. How do you delete a column?",
                "a) Select the column heading you want to delete and select the Delete Row button on the standard toolbar",
                "b) Select the column heading you want to delete and select Insert Delete from the menu",
                "c) Select the row heading you want to delete and select Edit>Delete from the menu",
                "d) Right click the column heading you want to delete and select delete from the shortcut menu",
                "d) Right click the column heading you want to delete and select delete from the shortcut menu"));

        questionList.add(new Question( "12. How can you find specific information in a list?",
                "a) Select Tools>Finder from the menu",
                "b) Click the Find button on the standard toolbar",
                "c) Select Insert>Find from the menu",
                "d) Select Data>Form from the menu to open the Data Form dialog box and click the Criteria button",
                "d) Select Data>Form from the menu to open the Data Form dialog box and click the Criteria button"));

        questionList.add(new Question( "13. When a label is too long to fit with in a worksheet cell, you typically must",
                "a) Shorten the label",
                "b) Increase the column width",
                "c) Decrease the column width",
                "d) Adjust the row height",
                "b) Increase the column width"));

        questionList.add(new Question( "14. You can use the horizontal and vertical scroll bars to",
                "a) Split a worksheet in to two panes",
                "b) View different rows and columns edit the contents of a cell ",
                "c) Edit the contents of a cell",
                "d) view different work sheets",
                "b) View different rows and columns edit the contents of a cell "));

        questionList.add(new Question( "15. What is the term used when you press and hold the left mouse key and more the mouse around the slide?",
                "a) Highlighting",
                "b) Dragging",
                "c) Selecting",
                "d) Moving",
                "b) Dragging"));

        questionList.add(new Question( "16. You can edit an embedded organization chart object by",
                "a) Clicking edit object",
                "b) Double clicking the organization chart object",
                "c) Right clicking the chart object, then clicking edit MS-Organizaiton Chart object ",
                "d) b and c both",
                "d) b and c both"));

        questionList.add(new Question( "17. Special effects used to introduce slides in a presentation are called",
                "a) effects",
                "b) custom animations",
                "c) transitions",
                "d) present animations",
                "c) transitions"));

        questionList.add(new Question( "18. You can create a new presentation by completing all of the following except",
                "a) Clicking the new button on the standard toolbar",
                "b) Clicking file, new",
                "c) Clicking file open",
                "d) Pressing ctrl+N",
                "c) Clicking file open"));

        questionList.add(new Question( "19. In order to edit a chart, you can ",
                "a) Triple click the chart object",
                "b) Click and drag the chart object",
                "c) Double click the chart object",
                "d) Click the chart object",
                "c) Double click the chart object"));

        questionList.add(new Question( "20. In which menu can you find features like Slide Design, Slide Layout etc)?",
                "a) Insert Menu",
                "b) Format Menu",
                "c) Tools Menu",
                "d) SlideShow Menu",
                "b) Format Menu"));

        questionList.add(new Question( "21. Which symbol must all formula begin with?",
                "a) =",
                "b) +",
                "c) (",
                "d) @",
                "a) ="));

        questionList.add(new Question( "22. Which of the following formulas is not entered correctly?",
                "a) =10+50",
                "b) =B7*B1",
                "c) =B7+14",
                "d) 10+50",
                "d) 10+50"));

        questionList.add(new Question( "23. Getting data from a cell located in a different sheet is called ......",
                "a) Accessing",
                "b) Referencing",
                "c) Updating",
                "d) Functioning",
                "b) Referencing"));

        questionList.add(new Question( "24. What is the intersection of a column and a row on a worksheet called?",
                "a) Column",
                "b) Value",
                "c) Address",
                "d) Cell",
                "d) Cell"));

        questionList.add(new Question( "25. To save a workbook, you:",
                "a) Click the save button on the standard toolbar from the menu",
                "b) Press Ctrl+F5",
                "c) Click Save on the Windows Start button",
                "d) Select Edit>Save",
                "a) Click the save button on the standard toolbar from the menu"));

        questionList.add(new Question( "26. To view a cell comment",
                "a) click the edit comment command on the insert menu",
                "b) click the display comment command on the window menu",
                "c) position the mouse pointer over the cell",
                "d) click the comment command on the view menu",
                "c) position the mouse pointer over the cell"));

        questionList.add(new Question( "27. You can select a single range of cells by",
                "a) Clicking the upper-left cell in a group of cells and then pressing the Shiftkey while clicking the lower right cell in a group of cells",
                "b) Pressing the Ctrl key while dragging over the desired cells",
                "c) Pressing the Shift key and an arrow key",
                "d) Dragging over the desired cells",
                "d) Dragging over the desired cells"));

        questionList.add(new Question( "28. You can use the drag and drop method to",
                "a) Copy cell contents",
                "b) Move cell contents",
                "c) Add cell contents",
                "d) a and b",
                "d) a and b"));

        questionList.add(new Question( "29. How can you delete a record?",
                "a) Delete the column from the worksheet",
                "b) Select Data>Form from the menu to open the Data Form dialog box, find the record and Click the Delete button",
                "c) Select Data>Delete Record from the menu",
                "d) Click the Delete button on the Standard toolbar",
                "b) Select Data>Form from the menu to open the Data Form dialog box, find the record and Click the Delete button"));

        questionList.add(new Question( "30. Right clicking something in Excel:",
                "a) Deletes the object",
                "b) Nothing the right mouse button is there for left handed people",
                "c) Opens a shortcut menu listing everything you can do to the object",
                "d) Selects the object",
                "c) Opens a shortcut menu listing everything you can do to the object"));

        questionList.add(new Question( "31. Which option in File pull-down menu is used to close a file in MS Word?",
                "a) New",
                "b) Quit",
                "c) Close",
                "d) Exit",
                "c) Close"));

        questionList.add(new Question( "32. What is the function of Ctrl+B in Ms-Word",
                "a) It converts selected text in to the next larger size of the same font",
                "b) It adds a line break to the document",
                "c) It makes the selected text bold",
                "d) It applies Italic formatting the selected text",
                "c) It makes the selected text bold"));

        questionList.add(new Question( "33. Graphics for word processor",
                "a) Peripheral",
                "b) Clip art",
                "c) Highlight",
                "d) Execute",
                "b) Clip art"));

        questionList.add(new Question( "34. What is the function of CTRL+P in MS-Word",
                "a) Open the Print dialog box",
                "b) Update the current Webpage",
                "c) Close the current window",
                "d) Close all windows",
                "a) Open the Print dialog box"));

        questionList.add(new Question( "35. What is the extension of files created in Ms-Word 97-2003",
                "a) dot",
                "b) doc",
                "c) dom",
                "d) txt",
                "b) doc"));

        questionList.add(new Question( "36. In Microsoft Word shortcut key CTRL+W is used for",
                "a) open the Print dialog box",
                "b) Update the current Webpage",
                "c) close the current window",
                "d) None of these",
                "c) close the current window"));

        questionList.add(new Question( "37. Portrait and landscape are ……… in MS-Office",
                "a) Page orientation",
                "b) Page setup",
                "c) Page margin",
                "d) Page layout",
                "a) Page orientation"));

        questionList.add(new Question( "38. Which of the following is not a font style in MS-Word",
                "a) Regular",
                "b) Bold",
                "c) Italics",
                "d) Superscript",
                "d) Superscript"));

        questionList.add(new Question( "39. Which of the following is not a paper margin in MS-Word",
                "a) Top",
                "b) Right",
                "c) Center",
                "d) Left",
                "c) Center"));

        questionList.add(new Question( "40. In MS Word, the command to move one word to the right is",
                "a) CTRL+RIGHT ARROW",
                "b) CTRL+LEFT ARROW",
                "c) CTRL+DOWN ARROW",
                "d) CTRL+UP ARROW",
                "a) CTRL+RIGHT ARROW"));

        questionList.add(new Question( "41. Which file format can be added to a PowerPoint show?",
                "a) .jpg",
                "b) .giv",
                "c) .wav",
                "d) All of the above",
                "d) All of the above"));

        questionList.add(new Question( "42. In Microsoft PowerPoint two kind of sound effects files that can be added to the" +
                " presentation are……….",
                "a) .wav files and .mid files",
                "b) .wav files and .gif files",
                "c) .wav files and .jpg files",
                "d) .jpg files and .gif files",
                "a) .wav files and .mid files"));

        questionList.add(new Question( "43. Material consisting of text and numbers is best presented as…………..",
                "a) A table slide",
                "b) A bullet slide",
                "c) A title slide",
                "d) All of the above",
                "a) A table slide"));

        questionList.add(new Question( "44. What is a slide-title master pair?",
                "a) The title area and text area of a specific slide",
                "b) a slide master and title master merged into a single slide",
                "c) A slide master and title master for a specific design template",
                "d) All of above",
                "c) A slide master and title master for a specific design template"));

        questionList.add(new Question( "45. Which of the following should you use if you want all the slide in the presentation " +
                "to have the same “look”?",
                "a) the slide layout option",
                "b) add a slide option",
                "c) outline view",
                "d) a presentation design template",
                "d) a presentation design template"));

        questionList.add(new Question( "46. In the context of animations, what is a trigger?",
                "a) An action button that advances to the next slide",
                "b) An item on the slide that performs an action when clicked",
                "c) The name of a motion path",
                "d) All of above",
                "b) An item on the slide that performs an action when clicked"));

        questionList.add(new Question( "47. If you have a PowerPoint show you created and want to send using email to " +
                "another teacher you can add the show to your email message as a(an)………………",
                "a) Inclusion",
                "b) Attachment",
                "c) Reply",
                "d) Forward",
                "b) Attachment"));

        questionList.add(new Question( "48. In order to edit a chart, you can…………….",
                "a) Triple click the chart object",
                "b) Click and drag the chart object",
                "c) Double click the chart object",
                "d) Click the chart object",
                "c) Double click the chart object"));

        questionList.add(new Question( "49. To exit the PowerPoint",
                "a) click the application minimize button",
                "b) click the document close button",
                "c) double click the applications control menu icon",
                "d) double click the document control menu icon",
                "c) double click the applications control menu icon"));

        questionList.add(new Question( "50. To preview a motion path effect using the custom animation task pane, you should",
                "a) click the play button",
                "b) click the show effect button",
                "c) double click the motion path",
                "d) all of above",
                "a) click the play button"));
    }

}

