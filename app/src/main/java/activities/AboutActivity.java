package activities;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.widget.TextView;

import com.ziga.tezaver.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Typeface robotoSlab = Typeface.createFromAsset(this.getAssets(), "RobotoSlab.ttf");

        ActionBar bar = getSupportActionBar();
        if(bar!=null)
        {
            bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_blue)));
            bar.setDisplayHomeAsUpEnabled(true);
        }


        TextView questionOne = (TextView) findViewById(R.id.question_one);
        TextView questionTwo = (TextView) findViewById(R.id.question_two);
        TextView questionThree = (TextView) findViewById(R.id.question_three);
        TextView questionFour = (TextView) findViewById(R.id.question_four);
        TextView questionFive = (TextView) findViewById(R.id.question_five);
        TextView questionSix = (TextView) findViewById(R.id.question_six);
        TextView questionSeven = (TextView) findViewById(R.id.question_seven);

        TextView answerOne = (TextView) findViewById(R.id.answer_one);
        TextView answerFive = (TextView) findViewById(R.id.answer_five);
        TextView answerSix = (TextView) findViewById(R.id.answer_six);
        TextView answerSeven = (TextView) findViewById(R.id.answer_seven);

        questionOne.setTypeface(robotoSlab);
        questionTwo.setTypeface(robotoSlab);
        questionThree.setTypeface(robotoSlab);
        questionFour.setTypeface(robotoSlab);
        questionFive.setTypeface(robotoSlab);
        questionSix.setTypeface(robotoSlab);
        questionSeven.setTypeface(robotoSlab);

        // Gets the links working
        answerOne.setMovementMethod(LinkMovementMethod.getInstance());
        answerFive.setMovementMethod(LinkMovementMethod.getInstance());
        answerSix.setMovementMethod(LinkMovementMethod.getInstance());
        answerSeven.setMovementMethod(LinkMovementMethod.getInstance());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_word_activity, menu);

        return true;
    }

}
