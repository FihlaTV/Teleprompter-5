package com.tjohnn.teleprompter.ui.scripts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tjohnn.teleprompter.R;
import com.tjohnn.teleprompter.data.Script;
import com.tjohnn.teleprompter.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;

public class ScriptAdapter extends RecyclerView.Adapter<ScriptAdapter.ScriptViewHolder> {

    List<Script> items = new ArrayList<>();
    OnScriptItemListener listener;

    public ScriptAdapter(OnScriptItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ScriptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_script, parent, false);
        return new ScriptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScriptViewHolder holder, int position) {
        holder.bindDataAt(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void updateItems(List<Script> scriptList) {
        items = scriptList;
        notifyDataSetChanged();
    }

    class ScriptViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_date)
        TextView date;
        @BindView(R.id.btn_delete)
        ImageButton delete;
        @BindView(R.id.btn_edit)
        ImageButton edit;

        ScriptViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindDataAt(int position) {
            Script script = items.get(position);
            title.setText(script.getTitle());
            date.setText(DateUtils.dateToFormatString1(script.getTelepromptingDate()));

            delete.setOnClickListener(v -> listener.onItemDeleteClicked(script));
            edit.setOnClickListener(v -> listener.onItemEditClicked(script));
            itemView.setOnClickListener(v -> listener.onItemClicked(script));
        }
    }

    interface OnScriptItemListener {
        void onItemEditClicked(Script script);
        void onItemDeleteClicked(Script script);
        void onItemClicked(Script script);
    }
}
