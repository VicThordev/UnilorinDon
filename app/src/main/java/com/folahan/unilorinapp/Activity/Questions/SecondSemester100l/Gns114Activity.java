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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.MainActivity;
import com.folahan.unilorinapp.Model.Question;
import com.folahan.unilorinapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Gns114Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1;
    Button btnNext, btnPrev, btnEnd, btnAnswer;
    ArrayList<String> list = new ArrayList<>();
    private AlertDialog.Builder dialog;
    String [] arr;
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

        //getQuestionPhase(questionList);

        //chooseMethod();

        list.add(getQuestionPhase(questionList));
        list.add(getQuestionPhase2(questionList));
        list.add(getQuestionPhase3(questionList));

        setDataView(pos);
        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.button_previous);

        btnNext.setOnClickListener(view -> {
            if (questionAnswered == 60) {
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

    private String chooseMethod() {
        ArrayList<String> list = new ArrayList<>();
        list.add(getQuestionPhase(questionList));
        list.add(getQuestionPhase2(questionList));
        list.add(getQuestionPhase3(questionList));
        return list.get(random.nextInt(list.size()));
        //list = new String[]{getQuestionPhase3(questionList), getQuestionPhase(questionList), getQuestionPhase2(questionList)};
        //List<String> strings = Arrays.asList(arr);
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

        //btnAnswer.setOnClickListener(view -> showOtherButton());
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

    @Nullable
    private String getQuestionPhase(List<Question> list) {

        questionList.add(new Question( "Two devices are in network if",
                "a) a process in one device is able to exchange information with a process in another device",
                "b) a process is running on both devices",
                "c) PIDs of the processes running of different devices are same",
                "d) none of the mentioned",
                "a) a process in one device is able to exchange information with a process in another device"));

        questionList.add(new Question( "What is a web browser?",
                "a) a program that can display a webpage",
                "b) a program used to view html documents",
                "c) it enables user to access the resources of internet",
                "d) all of the mentioned",
                "d) all of the mentioned"));

        questionList.add(new Question( "URL stands for",
                "a) unique reference label",
                "b) uniform reference label",
                "c) uniform resource locator",
                "d) unique resource locator",
                "c) uniform resource locator"));

        questionList.add(new Question("What is internet?",
                "a) a single network",
                "b) a vast collection of different network",
                "c) interconnection of local area network",
                "d) none of the mentioned",
                "b) a vast collection of different network"));

        questionList.add(new Question("Which of the following is a correct format of Email address?",
                "a) name@website@info",
                "b) name@website.info",
                "c) www.nameofebsite.com",
                "d) name.website.com",
                "b) name@website.info"));

        questionList.add(new Question("HTML is used to create",
                "a) machine language program",
                "b) high level program",
                "c) web page" ,
                "d) web server", "c) web page"));

        questionList.add(new Question("The computer jargon - WWW,stands for",
                "a) World Wide Web", "b) World Wide Wildlife",
                "c) World Wide Women's", "d) World Wide Women's",
                "a) World Wide Web"));

        questionList.add(new Question("The process of transferring files from a computer on the Internet to your" +
                "computer is called",
                "a) Uploading", "b) Forwarding",
                "c) FTP",
                "d) Downloading",
                "d) Downloading"));

        questionList.add(new Question("In internet terminology IP means",
                "a) Internet Provider",
                "b) Internet Protocol",
                "c) Internet Procedure",
                "d) Internet Processor",
                "b) Internet Protocol"));

        questionList.add(new Question("Which one of the following is not a search engine??",
                "a) Bing",
                "b) Google",
                "c) Yahoo",
                "d) Windows", "d) Windows"));

        questionList.add(new Question("Verification of a login name and password is known as" ,
                "a) configuration",
                "b) accessibility" ,
                "c) authentication",
                "d) logging in",
                "c) authentication"));

        questionList.add(new Question( "Internet explorer falls under:",
                "a) Operating System",
                "b) Compiler",
                "c) Browser",
                "d) IP address",
                "c) Browser"));

        questionList.add(new Question( "What is the full form of WWW in web address?",
                "a) World Wide Web",
                "b) World Wide Word",
                "c) World Wide Wood",
                "d) None of these",
                "a) World Wide Web"));

        questionList.add(new Question( "Full form of HTML is:",
                "a) Hyper Text Markup Language",
                "b) Hyper Text Manipulation Language",
                "c) Hyper Text Managing Links",
                "d) Hyper Text Manipulating Links",
                "a) Hyper Text Markup Language"));

        questionList.add(new Question( "Moving from one website to another is called:",
                "a) Downloading",
                "b) Browsing",
                "c) Uploading",
                "d) Attachment",
                "b) Browsing"));

        questionList.add(new Question( "A computer on internet are identified by:",
                "a) e-mail address",
                "b) street address",
                "c) IP address",
                "d) None of the above",
                "c) IP address"));

        questionList.add(new Question( "___________programs automatically connect to websites and download " +
                "documents and save them to local drive.",
                "a) None of these",
                "b) Web Downloading Utilities",
                "c) Offline Browser",
                "d) Web Server",
                "c) Offline Browser"));

        questionList.add(new Question( "Which of the following website will give you details of Local Shops",
                "a) www.c4learn.com",
                "b) www.justdial.com",
                "c) www.google.com",
                "d) None of these",
                "c) www.google.com"));

        questionList.add(new Question( "An example of an Optical Storage device is:",
                "a) Magnetic Tapes",
                "b) USB Disk",
                "c) Floppy Disk",
                "d) DVD",
                "d) DVD"));

        questionList.add(new Question( "What unit of storage is used to represent 1,073,741,824 bytes?",
                "a) Kilobyte",
                "b) Terabyte",
                "c) Megabyte",
                "d) Gigabyte",
                "d) Gigabyte"));

        questionList.add(new Question( "What unit of storage is used to represent 1,024 bytes?",
                "a) Kilobyte",
                "b) Terabyte",
                "c) Megabyte",
                "d) Gigabyte",
                "a) Kilobyte"));

        questionList.add(new Question( "What unit of storage is used to represent 1,048,576 bytes",
                "a) Kilobyte",
                "b) Terabyte",
                "c) Megabyte",
                "d) Gigabyte",
                "b) Terabyte"));

        questionList.add(new Question( "Memory stores:",
                "a) the operating system",
                "b) application programs",
                "c) the data being processed by the application programs",
                "d) all of the above",
                "d) all of the above"));

        questionList.add(new Question( "Bit is short for:",
                "a) Binary system",
                "b) Digital byte",
                "c) Binary unit",
                "d) Binary digit",
                "d) Binary digit"));

        questionList.add(new Question( "___________is used to store frequently used data and instruction for faster " +
                "access and processing.",
                "a) Cache memory",
                "b) Virtual memory",
                "c) Flash memory",
                "d) All of the above",
                "a) Cache memory"));

        questionList.add(new Question( "The number of bits that can be uniquely addressed by the computer\n",
                "a) byte",
                "b) cycle",
                "c) register",
                "d) word",
                "a) byte"));

        questionList.add(new Question( "Input may be in the following",
                "a) OCR, MICR and CPU",
                "b) OMR, VDU and OCR",
                "c) OCR, MICR and OMR",
                "d) VDU, SVGA and XVGA",
                "c) OCR, MICR and OMR"));

        questionList.add(new Question( "Which of the following statements is true?",
                "a) The digitizing tablet is an input device",
                "b) The light pen is an input device",
                "c) The mouse is an output device",
                "d) The monitor is not an output device",
                "b) The light pen is an input device"));

        questionList.add(new Question( "The nature of data that determines the hybrid type of computer is/are",
                "a) Analogue",
                "b) Digital",
                "c) Synchronous",
                "d) Analogue and digital",
                "d) Analogue and digital"));

        return null;
    }

    @Nullable
    private String getQuestionPhase2(List<Question> list) {

        questionList.add(new Question( "Computer that requests the resources or data from other computer is called as" +
                "________computer.",
                "a) Server",
                "b) Client",
                "c) Filter",
                "d) Pusher",
                "b) Client"));

        questionList.add(new Question( "Software which allows user to view the webpage is called as__________.",
                "a) Interpreter",
                "b) Internet Browser",
                "c) Website",
                "d) OperatingSystem",
                "b) Internet Browser"));

        questionList.add(new Question( "With regards to e-mail addresses",
                "a) they must always contain an @ symbol",
                "b) they can never contain spaces",
                "c) they are case-insensitive",
                "d) all of the above",
                "d) all of the above"));

        questionList.add(new Question( "Which of the following website is used to search other website by typing a keyword?",
                "a) Social Networks",
                "b) None of these",
                "c) Routers",
                "d) Search Engine",
                "d) Search Engine"));

        questionList.add(new Question( "Google (www.google.com) is a",
                "a) Number in Math",
                "b) Search Engine",
                "c) Chat service on the web",
                "d) Directory of images",
                "b) Search Engine"));

        questionList.add(new Question( "At which of the following sites would you most probably buy books?",
                "a) www.amazon.com",
                "b) www.hotmail.com",
                "c) www.sun.com",
                "d) www.msn.com",
                "a) www.amazon.com"));

        questionList.add(new Question( "ISP stands for",
                "a) Integrated Service Provider",
                "b) Internet Service Provider",
                "c) Internet Security Protocol",
                "d) Internet Survey Period",
                "b) Internet Service Provider"));

        questionList.add(new Question( "Applets are written in______________programming language.",
                "a) C++",
                "b) Java",
                "c) C",
                "d) C#",
                "b) Java"));

        questionList.add(new Question( "HTML is used to",
                "a) Author web page",
                "b) Plot Complicated Graph",
                "c) Solve equation",
                "d) Transfer one language to another",
                "a) Author web page"));

        questionList.add(new Question( "Which term describes hardware or software that protects your computer or" +
                "network from probing or malicious users?",
                "a) Router",
                "b) Firewall",
                "c) Protocol",
                "d) Spyware",
                "b) Firewall"));

        questionList.add(new Question( "Search engine are used to________\n",
                "a) Search Videos",
                "b) Search for information on the World Wide Web",
                "c) All of these",
                "d) Search Documents",
                "c) All of these"));


        questionList.add(new Question( "Which of the following is used to explore the Internet?",
                "a) Browser",
                "b) Spreadsheet",
                "c) Clipboard",
                "d) Draw",
                "a) Browser"));

        questionList.add(new Question( "What is Internet Explorer?",
                "a) An Icon",
                "b) A File Manager",
                "c) A Browser",
                "d) The Internet",
                "c) A Browser"));

        questionList.add(new Question( "What do I need to get on to the Internet?",
                "a) Computer",
                "b) Modem",
                "c) Browser",
                "d) All of the above",
                "d) All of the above"));

        questionList.add(new Question( "What is an ISP?",
                "a) Internet System Protocol",
                "b) Internal System Program",
                "c) Internet Service Provider",
                "d) None of the above",
                "c) Internet Service Provider"));

        questionList.add(new Question( "Which is not a domain name extensio",
                "a) .mil",
                "b) .org",
                "c) .int",
                "d) .com",
                "c) .int"));

        questionList.add(new Question( "What is a FTP program used for?",
                "a) Transfer files to and from an Internet Server",
                "b) Designing a website",
                "c) Connecting to the internet",
                "d) None of the above",
                "a) Transfer files to and from an Internet Server"));

        questionList.add(new Question( "TCP/IP is a:",
                "a). Network Hardware",
                "b) Network Software",
                "c) Protocol",
                "d) None of these",
                "c) Protocol"));

        questionList.add(new Question( "TCP/IP mainly used for:",
                "a) File Transfer",
                "b) Email",
                "c) Remote Login Service",
                "d) All of these",
                "d) All of these"));

        questionList.add(new Question( "Which protocol is used for browsing website?",
                "a) TCP",
                "b) HTTP",
                "c) FTP",
                "d) TFTP",
                "b) HTTP"));

        questionList.add(new Question( "Which is not the browser?",
                "a) Internet Explorer",
                "b) Opera",
                "c) Mozilla",
                "d) Google",
                "d) Google"));

        questionList.add(new Question( "Which of the following lists the different type of networks in ascending " +
                "geographical area?",
                "a) LAN, WAN, MAN",
                "b) LAN, MAN, WAN",
                "c) MAN, LAN, WAN",
                "d) WAN, LAN, MAN",
                "b) LAN, MAN, WAN"));

        questionList.add(new Question( "All of the following are examples of computer input units EXCEPT:",
                "a) Scanner",
                "b) Speaker",
                "c) Bar code reader",
                "d) Keyboard",
                "b) Speaker"));

        questionList.add(new Question( "Which of the following units is the biggest capacity:",
                "a) Byte",
                "b) Kilobyte",
                "c) Gigabyte",
                "d) Megabyte",
                "c) Gigabyte"));

        questionList.add(new Question( "Which of the following devices can be used to directly input printed text",
                "a) OCR",
                "b) OMR\n",
                "c) MICR\n",
                "d) None of the above",
                "a) OCR"));

        questionList.add(new Question( "Which of the following does not represent on I/O device",
                "a) speaker",
                "b) joystick",
                "c) plotter",
                "d) ALU",
                "d) ALU"));

        questionList.add(new Question( "One thousand and twenty four bytes represent a",
                "a) Megabyte",
                "b) Gigabyte",
                "c) Kilobyte",
                "d) Terabyte",
                "c) Kilobyte"));

        questionList.add(new Question( "Third generation computers",
                "a) Were the first to use built-in error detecting device\n",
                "b) Used integrated circuit instead of vacuum tubes",
                "c) Were the first to use neural network",
                "d) used micro processor",
                "b) Used integrated circuit instead of vacuum tubes"));

        questionList.add(new Question( "Computer follows a simple principle called GIGO which means:",
                "a) garbage input good output",
                "b) garbage in garbage out",
                "c) great instructions great output",
                "d) good input good output",
                "b) garbage in garbage out"));

        questionList.add(new Question( "Multiple choice examination answer sheets can be evaluated automatically by",
                "a) Optical Mark Reader",
                "b) Optical Character Reader",
                "c) Magnetic tape reader",
                "d) Magnetic ink character reader.",
                "a) Optical Mark Reader"));

        return null;
    }

    @Nullable
    private String getQuestionPhase3(List<Question> list) {

        questionList.add(new Question( "Internet is .............................",
                "a) a network of networks",
                "b) an ocean of resources waiting to be mined",
                "c) a cooperative anarchy",
                "d) all of the above",
                "d) all of the above"));

        questionList.add(new Question( "________ programs are automatically loaded and operates as a part of browser.\n",
                "a) Plug-ins",
                "b) Add-ons",
                "c) Utilities",
                "d) Widgets",
                "a) Plug-ins"));

        questionList.add(new Question( "Which of the following protocol is used for e-mail services?",
                "a) SMAP",
                "b) SMTP",
                "c) SMIP",
                "d) SMOP",
                "b) SMTP"));

        questionList.add(new Question( ".......................is a uniform naming scheme for locating resources on the web",
                "a) URL",
                "b) HTTP",
                "c) WEBNAME",
                "d) RESOURCENAME",
                "a) URL"));

        questionList.add(new Question("To join the internet,the computer has to be connected to a",
                "a) internet architecture board",
                "b) internet society",
                "c) internet service provider",
                "d) none of the mentioned",
                "c) internet service provider"));

        questionList.add(new Question("Internet access by transmitting digital data over the wires of a local telephone" +
                "network is provided by",
                "a) leased line",
                "b) digital subscriber line",
                "c) digital signal line",
                "d) none of the mentioned",
                "b) digital subscriber line"));

        questionList.add(new Question("ISP exchanges internet traffic between their networks by",
                "a) internet exchange point",
                "b) subscriber end point",
                "c) ISP endpoint",
                "d) none of the mentioned",
                "a) internet exchange point"));

        questionList.add(new Question( "Which is not the search engine:",
                "a) Altavista.com",
                "b) Google.com",
                "c) Facebook.com",
                "d) Yahoo.com",
                "c) Facebook.com"));

        questionList.add(new Question( "Email stands for:",
                "a) Easy mail",
                "b) Electronic mail",
                "c) Electric mail",
                "d) None of these",
                "b) Electronic mail"));

        questionList.add(new Question( "Which is the chatting application",
                "a) Yahoo messenger",
                "b) Google earth",
                "c) Youtube",
                "d) None of these",
                "a) Yahoo messenger"));

        questionList.add(new Question( "Which is not the application of internet:",
                "a) Communication",
                "b) Banking",
                "c) Shopping",
                "d) Surfing",
                "d) Surfing"));

        questionList.add(new Question( "Which is the advantage of e-business:",
                "a) Better Service",
                "b) Reduction of cost",
                "c) Reduction of paperwork",
                "d) All of these",
                "d) All of these"));

        questionList.add(new Question( "Which is the advantage of e-business:",
                "a) Better Service",
                "b) Reduction of cost",
                "c) Reduction of paperwork",
                "d) All of these",
                "d) All of these"));

        questionList.add(new Question( "An operating system",
                "a) is not required on large computers",
                "b) is always supplied with the computer",
                "c) is always supplied with the BASIC",
                "d) is a system software",
                "d) is a system software"));

        questionList.add(new Question( "Which of the following would cause quickest access",
                "a) direct access from a cache",
                "b) direct access from a hard disk",
                "c) direct access from a floppy disk",
                "d) direct access from a memory",
                "a) direct access from a cache"));

        questionList.add(new Question( "The process of retaining data for future use is called",
                "a) reading",
                "b) writing",
                "c) storing",
                "d) coding",
                "c) storing"));

        questionList.add(new Question( "25. _________is a interconnection of computers that facilitates the sharing of "+
                "information "+
                "between computing devices",
                "a) network",
                "b) peripheral",
                "c) expansion board",
                "d) digital device",
                "a) network"));

        questionList.add(new Question( "Which of the following statements is true?",
                "a) Mini computer works faster than Micro computer",
                "b) Micro computer works faster than Mini computer",
                "c) Speed of both the computers is the same",
                "d) The speeds of both these computers cannot be compared with the speed " +
                "of advanced",
                "b) Micro computer works faster than Mini computer"));

        questionList.add(new Question( "Where are data and programme stored when the processor uses them?",
                "a) Main memory",
                "b) Secondary memory",
                "c) Disk memory",
                "d) Programme memory",
                "a) Main memory"));

        questionList.add(new Question( "_______ represents raw facts, whereas________ is data made meaningful.",
                "a) Information,reporting",
                "b) Data,information",
                "c) Information,bits",
                "d) Records,bytes",
                "b) Data,information"));

        questionList.add(new Question( "Which device is required for the Internet connection?",
                "a) Joystick",
                "b) VDU",
                "c) CDDrive",
                "d) Distributor",
                "d) Distributor"));

        questionList.add(new Question( "What is a light pen?",
                "a) Mechanical Input device",
                "b) Optical input device",
                "c) Electronic input device",
                "d) Optical output device",
                "b) Optical input device"));

        questionList.add(new Question( "When you insert an Excel file into a Word document,the data are",
                "a) Hyper linked",
                "b) Placed in a word table",
                "c) Linked",
                "d) Embedded",
                "b) Placed in a word table"));

        questionList.add(new Question( "Each excel file is called a workbook because",
                "a) It can contain text and data",
                "b) It can be modified",
                "c) It can contain many sheets including worksheets and chart sheets",
                "d) You have to work hard to create it",
                "c) It can contain many sheets including worksheets and chart sheets"));

        questionList.add(new Question( "Which types of charts can excel produce?",
                "a) Line graphs and pie charts only",
                "b) Only line graphs",
                "c) Bar charts, line graphs and pie charts",
                "d) Bar charts and line graphs only",
                "c) Bar charts, line graphs and pie charts"));

        questionList.add(new Question( "How are data organized in a spreadsheet?",
                "a) Lines and spaces",
                "b) Layers and planes",
                "c) Rows and columns",
                "d) Height and width",
                "c) Rows and columns"));

        questionList.add(new Question( "You can print only an embedded chart by",
                "a) Moving the chart to a chart sheet before you print",
                "b) Formatting the chart before you print",
                "c) Selecting the chart before you print",
                "d) a and c",
                "d) a and c"));

        questionList.add(new Question( "How should you print a selected area of a worksheet, if you’ll want to print a "+
                "different area next time?",
                "a) On the file menu, point to print area, and then click set print area)",
                "b) On the file menu, click print, and then click selection under print what",
                "c) On the view menu, click custom views, then click add",
                "d) All of above",
                "b) On the file menu, click print, and then click selection under print what"));

        questionList.add(new Question( "Which of the following methods cannot be used to enter data in a cell?",
                "a) Pressing an arrow key",
                "b) Pressing the tab key",
                "c) Pressing the Esc key",
                "d) Clicking the enter button to the formula bar",
                "c) Pressing the Esc key"));

        questionList.add(new Question( "Which of the following methods cannot be used to edit the content of cell?",
                "a) Pressing the Alt key",
                "b) Clicking the formula bar",
                "c) Pressing F2",
                "d) Double clicking the cell",
                "a) Pressing the Alt key"));

        return null;
    }

    protected void showOtherButton() {
        Question list = new Question();
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet,
                findViewById(R.id.bottom_sheet_answer_dialog));
        TextView scoreShow = bottomSheet.findViewById(R.id.question);
        TextView answer = bottomSheet.findViewById(R.id.answertxt);
        Button goHome = bottomSheet.findViewById(R.id.btnHome);

        scoreShow.setText(list.getQuestion());
        answer.setText(list.getAnswer());

        goHome.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
            dialog.dismiss();
            finish();
        });
        dialog.setCancelable(false);
        dialog.setContentView(bottomSheet);
        dialog.show();
    }
}