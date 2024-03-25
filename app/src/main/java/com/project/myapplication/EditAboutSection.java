package com.project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditAboutSection extends AppCompatActivity {
    private EditText editText;
    private Button btnBold, btnItalic, btnNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_about_section);

        editText = findViewById(R.id.editText);
        btnBold = findViewById(R.id.btnBold);
        btnItalic = findViewById(R.id.btnItalic);
        btnNormal = findViewById(R.id.btnNormal);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                updateButtonsState();
            }
        });

        btnBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyStyleToSelection(Typeface.BOLD);
            }
        });

        btnItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyStyleToSelection(Typeface.ITALIC);
            }
        });

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyStyleToSelection(Typeface.NORMAL);
            }
        });
    }

    private void updateButtonsState() {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();

        // Check if any text is selected
        boolean textSelected = start != end;
    }

    private void applyStyleToSelection(int style) {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();

        Editable editable = editText.getText();
        SpannableStringBuilder builder = new SpannableStringBuilder(editable);
        StyleSpan[] styleSpans = builder.getSpans(start, end, StyleSpan.class);

        // Remove any existing style spans in the selected range
        for (StyleSpan span : styleSpans) {
            builder.removeSpan(span);
        }

        // Apply the new style to the selected text
        StyleSpan styleSpan = new StyleSpan(style);
        builder.setSpan(styleSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        editText.setText(builder);
    }

}