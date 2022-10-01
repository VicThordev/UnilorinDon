package com.folahan.unilorinapp.Activity.Questions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.Adapter.CommentAdapter;
import com.folahan.unilorinapp.CommentClassDatabase.CommentViewModel;
import com.folahan.unilorinapp.Model.CommentClass;
import com.folahan.unilorinapp.R;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    private ArrayList<CommentClass> comment;
    public static final String EXTRA_COMMENT = "com.folahan.unilorin_don.extra_comment";
    private CommentClass commentClass;
    public static int Add_Comment_Request = 1;
    private TextView txtName, txtComment, txtLike, txtCommentNo;
    private Button mBtnComment;
    private EditText mEdtComment;
    private CommentViewModel viewModel;
    private CommentAdapter adapter;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        txtName = findViewById(R.id.txtNameComment1);
        txtComment = findViewById(R.id.txtComment1);
        txtLike = findViewById(R.id.txtLikeComment1);
        txtCommentNo = findViewById(R.id.txtCommentNo1);
        mEdtComment = findViewById(R.id.edtComment);
        mRecyclerView = findViewById(R.id.recycler_view_comment);
        mBtnComment = findViewById(R.id.btnComment);

        mBtnComment.setOnClickListener(view -> {
            Intent data = new Intent();
            String message = mEdtComment.getText().toString();
            if (message.trim().isEmpty()) {
                Toast.makeText(this, "Empty comment cannot be post", Toast.LENGTH_SHORT).show();
                mBtnComment.setEnabled(false);

                commentClass.setComment(message);
                commentClass.setName("Folahan");
                commentClass.setLike("4 Likes");
                commentClass.setCommentNo("4 comments");

                data.putExtra(EXTRA_COMMENT, commentClass);
                setResult(RESULT_OK, data);
            }
        });

        adapter = new CommentAdapter();
        comment = new ArrayList<>();

        RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this)
                .get(CommentViewModel.class);
        viewModel.getAllComment().observe(this, commentClasses -> {
            adapter.submitList(comment);
            comment = new ArrayList<>();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Add_Comment_Request && resultCode == RESULT_OK) {
            assert data!=null;

            CommentClass comment = (CommentClass) data.getSerializableExtra(EXTRA_COMMENT);
            viewModel.insert(comment);
            Toast.makeText(this, "Comment Posted", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}