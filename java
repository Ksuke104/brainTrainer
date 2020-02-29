# brainTrainer


package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.zip.InflaterInputStream;

import static android.media.MediaPlayer.create;

public class MainActivity extends AppCompatActivity {

    Button goButton;

    //整数の入るanswersリスト（インスタンス）を生成
    ArrayList<Integer> answers = new ArrayList<Integer>();

    int locationOfCorrectAnswer;
    TextView resultTextView;
    TextView commentTextView;
    int score = 0;
    int numberOfQusetions = 0;
    TextView scoreTextView;
    TextView sumTextView;
    Button button0, button1, button2, button3;
    TextView timerTextview;
    Button playAgainButton;
    ConstraintLayout gameLayout;
    ImageView imageNirami;
    ImageView imageKakure;
    ImageView imageYousumi;




    //playaAgainボタンを押したときの挙動を定義
    public void playAgain(View view) {
        score = 0;
        numberOfQusetions = 0;
        timerTextview.setText("15s");
        scoreTextView.setText("0/0");

        //新しい問題と選択肢を表示
        newQuetion();

        //resultTextViewを見えるようにする
        resultTextView.setVisibility(View.VISIBLE);

        //commentTextViewを見えなくする
        commentTextView.setVisibility(View.INVISIBLE);

        //playAgainButtonを見えなくする
        playAgainButton.setVisibility(View.INVISIBLE);

        //猫の画像見えなくする
        imageNirami.setVisibility(View.INVISIBLE);
        imageYousumi.setVisibility(View.INVISIBLE);
        imageKakure.setVisibility(View.INVISIBLE);

        resultTextView.setText("");

        //４つのボタン（選択肢）出現
        buttonShow();



        //インスタンス.start()     インスタンスのメソッド呼び出し
        //CountDownTimerクラスから生成したインスタンスにstart()メソッドという指示を与える
        new CountDownTimer(15100, 1000) {

            //CountDownTimerクラスのメソッドを上書き
            @Override
            //カウントダウンしている間の挙動
            public void onTick(long millisUntilFinished) {

                //残り何秒か表示
                timerTextview.setText(String.valueOf(millisUntilFinished / 1000) + "s");

            }


            @Override
            //カウントダウンが0になった時の挙動
            public void onFinish() {
                MediaPlayer mediaPlayer;

                //４つのボタン（選択肢）を見えなくする
                buttonHide();

                resultTextView.setVisibility(View.INVISIBLE);
                commentTextView.setVisibility(View.VISIBLE);

                if(score > 9) {
                    commentTextView.setText("ほ、ほめてあげるわ//");
                    imageKakure.setVisibility(View.VISIBLE);

                    //効果音キラキラが鳴る
                    mediaPlayer = create(getApplicationContext(), R.raw.shine1);
                    mediaPlayer.start();



                } else if(score > 5) {
                    commentTextView.setText("ふん、まあまあね");
                    imageYousumi.setVisibility(View.VISIBLE);

                    //効果音シャキーン！が鳴る
                    mediaPlayer = create(getApplicationContext(), R.raw.shakin1);
                    mediaPlayer.start();

                } else {
                    commentTextView.setText("あんたやる気あるの？");
                    imageNirami.setVisibility(View.VISIBLE);

                    //効果音チーンが鳴る
                    mediaPlayer = create(getApplicationContext(), R.raw.tin1);
                    mediaPlayer.start();

                }

                //playAgainButton出現
                playAgainButton.setVisibility(View.VISIBLE);

            }

        }.start();

    }

    //4種類のボタンを押したときの挙動（メソッド）を定義
    public void chooseAnswer(View view) {

        //正解のタグと押したタグが同じとき（４つのボタンに０～３の4種類のタグを設定している）
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
            resultTextView.setText("正解にゃ");
            score++;

        } else {
            resultTextView.setText("間違いにゃ");

        }

        numberOfQusetions++;

        //scoreTevtView　何問中何問正解しているか表示
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQusetions));

        //新しい問題と選択肢を表示
        newQuetion();
    }

    //Goボタンを押したときの挙動（メソッド）を定義
    public void start(View view) {
        goButton.setVisibility(View.INVISIBLE);

        //残り時間、問題、得点/問題数、4つの選択肢（ボタン）を出現させる
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.timerTextView));

    }

    public void newQuetion() {

        //インスタンス生成　　
        //クラス名　変数　=　new クラス名();
        Random rand = new Random();

        //整数の変数a,bを定義　　0～20の21通りの整数をランダムに代入
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a)+ " + " + Integer.toString(b));

        //0～3の4通りの整数をランダムに代入　Tagも0～3の4通り
        locationOfCorrectAnswer = rand.nextInt(4);
        //answersリストの中身（４つの答え）を消去　
        answers.clear();

        for(int i=0; i<4; i++) {
            if(i==locationOfCorrectAnswer) {
                answers.add(a+b);
            } else {
                //0～40の41通りの整数をランダムに代入（a,bにはそれぞれ0～20の整数が入るため　最小0+0　最大20+20）
                int wrongAnswer = rand.nextInt(41);

                //wrongAnswerが正解と一緒の場合、再び0から40の41通りの整数をランダムに代入
                while(wrongAnswer == a+b) {
                    wrongAnswer = rand.nextInt(41);

                }

                //answersリストに間違った答えを追加
                answers.add(wrongAnswer);
            }

        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    //4つのボタン（選択肢）を見えるようにするメソッドを定義
    public void buttonShow() {
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
    }

    //4つのボタン（選択肢）を見えなくするメソッドを定義
    public void buttonHide(){
        button0.setVisibility(View.INVISIBLE);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //それぞれlayoutのViewと紐づけ
        goButton = findViewById(R.id.goButton);
        sumTextView = findViewById(R.id.sumTextView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        resultTextView = findViewById(R.id.resultTextView);
        commentTextView = findViewById(R.id.commentTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextview = findViewById(R.id.timerTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        gameLayout = findViewById(R.id.gameLayout);
        imageNirami = findViewById(R.id.nirami);
        imageKakure = findViewById(R.id.kakure);
        imageYousumi = findViewById(R.id.yousumi);
        goButton.setVisibility(View.VISIBLE);

    }
}

