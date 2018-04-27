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
import sy.soya.lear.databinding.ItemTodoBinding;
import sy.soya.lear.models.ToDoItem;
import sy.soya.lear.ui.activities.DetailsActivity;
import sy.soya.lear.ui.activities.MainActivity;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>{

    public static final String INTENT_EXTRA_TO_DO_ITEM = "INTENT_EXTRA_TO_DO_ITEM";

    private Context mContext;
    private ArrayList<ToDoItem> mToDoItems;

    public ToDoAdapter(Context mContext, ArrayList<ToDoItem> mToDoItems) {
        this.mContext = mContext;
        this.mToDoItems = mToDoItems;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTodoBinding binding = ItemTodoBinding.inflate(layoutInflater, parent, false);
        return new ToDoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        holder.bind(mToDoItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mToDoItems.size();
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder{

        private final ItemTodoBinding binding;
        private ToDoItem toDoItem;

        ToDoViewHolder(ItemTodoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(final ToDoItem toDoItem){
            this.toDoItem = toDoItem;
            binding.setToDoItem(toDoItem);
            binding.executePendingBindings();
            binding.setClickHandler(this);
        }

        public void onItemClick(View view) {
            Intent intent = new Intent(mContext, DetailsActivity.class);
            intent.putExtra(INTENT_EXTRA_TO_DO_ITEM, toDoItem);
            mContext.startActivity(intent);
        }

    }
}
