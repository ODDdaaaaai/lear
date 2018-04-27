package sy.soya.lear.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

import sy.soya.lear.R;
import sy.soya.lear.models.ToDoItem;
import sy.soya.lear.ui.activities.DetailsActivity;
import sy.soya.lear.ui.activities.MainActivity;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>{

    public static final String INTENT_EXTRA_TITLE = "INTENT_EXTRA_TITLE";
    public static final String INTENT_EXTRA_STATUS = "INTENT_EXTRA_STATUS";

    private Context mContext;
    private ArrayList<ToDoItem> mToDoItems;

    public ToDoAdapter(Context mContext, ArrayList<ToDoItem> mToDoItems) {
        this.mContext = mContext;
        this.mToDoItems = mToDoItems;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        holder.bind(mToDoItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mToDoItems.size();
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder{

        View item;
        TextView textViewTitle;
        TextView textViewStatus;

        ToDoViewHolder(View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewStatus = itemView.findViewById(R.id.text_view_status);
        }

        void bind(final ToDoItem toDoItem){

            textViewTitle.setText(toDoItem.getTitle());
            if(toDoItem.isCompleted()){
                textViewStatus.setText(mContext.getResources().getString(R.string.completed));
                textViewStatus.setTextColor(mContext.getResources().getColor(R.color.todo_completed));
            } else {
                textViewStatus.setText(mContext.getResources().getString(R.string.incomplete));
                textViewStatus.setTextColor(mContext.getResources().getColor(R.color.todo_incomplete));
            }

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    intent.putExtra(INTENT_EXTRA_TITLE, textViewTitle.getText().toString());
                    intent.putExtra(INTENT_EXTRA_STATUS, textViewStatus.getText().toString());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
