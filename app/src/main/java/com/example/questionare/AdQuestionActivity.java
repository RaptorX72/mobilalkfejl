package com.example.questionare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdQuestionActivity extends AppCompatActivity {

    String linkId;
    Question.QuestionType type;
    String titleText;
    String questionText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_question);

        TextView questionTitle = findViewById(R.id.questionTitleTextBox);
        TextView questionQuestion = findViewById(R.id.questionQuestionMultiLineTextBox);
        RadioButton rbBoolean = findViewById(R.id.radioButtonBoolean);
        RadioButton rbDecimal = findViewById(R.id.radioButtonDecimal);
        RadioButton rbInteger = findViewById(R.id.radioButtonInteger);
        RadioButton rbString = findViewById(R.id.radioButtonString);

        rbBoolean.setChecked(true);

        Button saveButton = findViewById(R.id.buttonSaveQuestion);
        Button discardButton = findViewById(R.id.buttonDiscardQuestion);

        if (saveButton == null) Log.d("Error", "savebutton is null");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleText = questionTitle.getText().toString();
                questionText = questionQuestion.getText().toString();
                if (ValidateFields(titleText, questionText)) {
                    if (rbBoolean.isChecked()) type = Question.QuestionType.BOOLEAN;
                    else if (rbDecimal.isChecked()) type = Question.QuestionType.DECIMAL;
                    else if (rbInteger.isChecked()) type = Question.QuestionType.INTEGER;
                    else if (rbString.isChecked()) type = Question.QuestionType.STRING;
                    Question question = new Question(Common.GenerateUIID(), type, titleText , questionText);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("result", question);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else Toast.makeText(AdQuestionActivity.this, "Text fields cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });

        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(RESULT_CANCELED, resultIntent);
                finish();
            }
        });
    }

    private boolean ValidateFields(String title, String question) {
        return !title.isEmpty() && !question.isEmpty();
    }
}
