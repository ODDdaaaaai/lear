package sy.soya.lear.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import sy.soya.lear.R;
import sy.soya.lear.databinding.ActivityDetailsBinding;
import sy.soya.lear.models.ToDoItem;
import sy.soya.lear.ui.adapters.ToDoAdapter;

public class DetailsActivity extends AppCompatActivity {

    private final String INTENT_EXTRA_TO_DO_ITEM = ToDoAdapter.INTENT_EXTRA_TO_DO_ITEM;

    private ToDoItem toDoItem;
    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        setData();
    }

    private void setData() {
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_EXTRA_TO_DO_ITEM)) {
            toDoItem = intent.getExtras().getParcelable(INTENT_EXTRA_TO_DO_ITEM);
            binding.setToDoItem(toDoItem);
            binding.setHandler(this);
        } else {
            throw new IllegalArgumentException("Title and status text must be bundled with an intent");
        }
    }

    public void onButtonClick(View view){
        toDoItem.setTitle("Test");
    }
}
