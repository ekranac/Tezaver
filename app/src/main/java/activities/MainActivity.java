package activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;


import com.ziga.tezaver.R;

import adapters.WordSearchAdapter;


public class MainActivity extends AppCompatActivity {

    WordSearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_btn) {
            AutoCompleteTextView search = (AutoCompleteTextView) findViewById(R.id.main_search);
            adapter = new WordSearchAdapter(this, null);
            search.setAdapter(adapter);

            search.setVisibility(View.VISIBLE);

            search.setFocusableInTouchMode(true);
            search.requestFocus();

            final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);
        }

        return super.onOptionsItemSelected(item);
    }


}
