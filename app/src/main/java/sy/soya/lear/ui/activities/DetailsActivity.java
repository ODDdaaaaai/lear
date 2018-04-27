package sy.soya.lear.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import sy.soya.lear.R;
import sy.soya.lear.ui.adapters.ToDoAdapter;

public class DetailsActivity extends AppCompatActivity {

    private final String INTENT_EXTRA_TITLE = ToDoAdapter.INTENT_EXTRA_TITLE;
    private final String INTENT_EXTRA_STATUS = ToDoAdapter.INTENT_EXTRA_STATUS;

    TextView textViewTitle;
    TextView textViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();
        setData();
    }

    private void init() {

        textViewTitle = findViewById(R.id.text_view_title);
        textViewStatus = findViewById(R.id.text_view_status);
    }

    private void setData() {
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_EXTRA_TITLE) && intent.hasExtra(INTENT_EXTRA_STATUS)) {
            textViewTitle.setText(intent.getExtras().getString(ToDoAdapter.INTENT_EXTRA_TITLE));
            textViewStatus.setText(intent.getExtras().getString(ToDoAdapter.INTENT_EXTRA_STATUS));
        } else {
            throw new IllegalArgumentException("Title and status text must be bundled with an intent");
        }
    }
}
