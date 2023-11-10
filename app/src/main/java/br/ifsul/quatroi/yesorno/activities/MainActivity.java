package br.ifsul.quatroi.yesorno.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import br.ifsul.quatroi.yesorno.R;
import br.ifsul.quatroi.yesorno.classes.LocaleAnswers;
import br.ifsul.quatroi.yesorno.domain.YesOrNo;
import br.ifsul.quatroi.yesorno.exceptions.YesOrNoException;
import br.ifsul.quatroi.yesorno.services.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText editTextQuestion;
    private Button buttonDecide;
    private TextView textViewAnswer;
    private ImageView imageViewAnswer;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextQuestion = findViewById(R.id.textInputEditTextQuestion);
        buttonDecide = findViewById(R.id.buttonDecide);
        textViewAnswer = findViewById(R.id.textViewAnswer);
        imageViewAnswer = findViewById(R.id.imageViewAnswer);

        retrofit = new Retrofit.Builder()
                .baseUrl(RestService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        buttonDecide.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        hideSoftKeyboard();

        if(view.getId() == R.id.buttonDecide) {
            trimText(editTextQuestion);

            String question = editTextQuestion.getText().toString();

            if(question.replace("?", "").isBlank()) {
                makeToast("Ao menos digite algo!", Toast.LENGTH_SHORT);
                editTextQuestion.setText("");
            } else if(!question.contains("?")) {
                makeToast("Perguntas possuem uma interrogação...", Toast.LENGTH_SHORT);
            } else {
                textViewAnswer.setVisibility(View.GONE);
                textViewAnswer.setText("");
                callApi(question);
                editTextQuestion.setText("");
            }
        }
    }

    private String trimText(EditText editText) {
        editText.setText(editText.getText().toString().trim());
        return editText.getText().toString();
    }

    private void callApi(String question) {
        showLoading();

        RestService restService = retrofit.create(RestService.class);

        Call<YesOrNo> call = restService.getYesOrNo();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<YesOrNo> call, Response<YesOrNo> response) {
                if(response.isSuccessful()) {
                    YesOrNo yesOrNo = response.body();

                    String answer;

                    try {
                        answer = toLocaleAnswer(yesOrNo.getAnswer());
                    } catch (YesOrNoException e) {
                        answer = LocaleAnswers.MAYBE;
                    }

//                    makeToast(answer + "!", Toast.LENGTH_SHORT);
                    showAnswer(question, answer, yesOrNo.getImage());
                }
            }

            @Override
            public void onFailure(Call<YesOrNo> call, Throwable t) {
                makeToast("Erro: " + t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    private void makeToast(String text, int duration) {
        Toast.makeText(getApplicationContext(), text, duration).show();
    }

    private String toLocaleAnswer(String answer) throws YesOrNoException {
        switch (answer.toLowerCase()) {
            case "yes":
                return LocaleAnswers.YES;
            case "no":
                return LocaleAnswers.NO;
            case "maybe":
                return LocaleAnswers.MAYBE;
            default:
                throw new YesOrNoException("Answer is neither \"Yes\" nor \"No\".");
        }
    }

    private void showLoading() {
        textViewAnswer.setText("...");
        textViewAnswer.setVisibility(View.VISIBLE);
    }

    private void showAnswer(String question, String answer, String imageUrl) {
        textViewAnswer.setText(String.format("%s %s!", question, answer));
        textViewAnswer.setVisibility(View.VISIBLE); // ?

//        try {
//            imageViewAnswer.setImageDrawable(loadImageFromURL(imageUrl));
//            imageViewAnswer.setVisibility(View.VISIBLE);
//        } catch (IOException e) {
//            imageViewAnswer.setVisibility(View.GONE);
//        }
    }

//    private Drawable loadImageFromURL(String url) throws IOException {
//        InputStream inputStream = (InputStream) new URL(url).getContent();
//        return Drawable.createFromStream(inputStream, url);
//    }

//    private void hideSoftKeyboard() {
//        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//    }

}