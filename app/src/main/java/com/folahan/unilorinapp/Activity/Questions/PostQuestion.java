package com.folahan.unilorinapp.Activity.Questions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.folahan.unilorinapp.Model.QuestionList;
import com.folahan.unilorinapp.R;

public class PostQuestion extends AppCompatActivity {

    private Button btn;
    private EditText edtPost;
    public static final String EXTRA_POST = "com.folahan.unilorinapp." +
            "EXTRA_POST";
    private QuestionList list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_question);

        edtPost = findViewById(R.id.edtPost);

        btn = findViewById(R.id.btnPost);
        btn.setOnClickListener(view -> {
            Intent data = new Intent();
            String post = edtPost.getText().toString();

            if (post.trim().isEmpty()) {
                Toast.makeText(this, "Empty post" +
                        " cannot be created!", Toast.LENGTH_SHORT).show();
            }

            if (list == null) {
                list = new QuestionList();
            }

            list.setQuestion(post);
            list.setComment(0);
            list.setName("Folahan");
            list.setLike(3);
            list.setId("VicThor11");

            data.putExtra(EXTRA_POST, list);

            setResult(RESULT_OK, data);
            finish();
        });
    }
}