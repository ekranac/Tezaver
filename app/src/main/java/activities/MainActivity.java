package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;


import com.ziga.tezaver.R;

import adapters.WordSearchAdapter;


public class MainActivity extends AppCompatActivity {

    WordSearchAdapter adapter;
    public final static String WORD_ID = "com.ziga.tezaver.WORD_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button clearSearch = (Button) findViewById(R.id.btn_clear_search);
        final AutoCompleteTextView search = (AutoCompleteTextView) findViewById(R.id.main_search);

        adapter = new WordSearchAdapter(this, null);
        search.setAdapter(adapter);


        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                search.setText("");

                Intent intent = new Intent(MainActivity.this, WordActivity.class);
                intent.putExtra(WORD_ID, adapter.getItem(i).getId().toLowerCase());
                startActivity(intent);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (search.length() == 0) {
                    clearSearch.setVisibility(View.INVISIBLE);
                } else {
                    clearSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setText("");
            }
        });
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
            search.setFocusableInTouchMode(true);
            search.requestFocus();

            final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        AutoCompleteTextView search = (AutoCompleteTextView) findViewById(R.id.main_search);
        if(search.hasFocus())
        {
            search.clearFocus();
        }
    }

}
