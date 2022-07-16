package sg.edu.np.mad.practical2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdaptor extends RecyclerView.Adapter<myAdaptor.myViewHolder>{
    ArrayList<User> data;
    RecyclerViewClickListener listener;

    public myAdaptor(ArrayList<User> input, RecyclerViewClickListener listener) {
        data = input;
        this.listener = listener;
    }

    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item;
        if (viewType == 0){
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout, parent, false);
        }
        else{
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout2, parent, false);
        }
        return new myViewHolder(item);
    }

    public void onBindViewHolder(myViewHolder holder, int position){
        User u = data.get(position);
        holder.name.setText(u.getName());
        holder.description.setText(u.getDescription());
    }

    public int getItemViewType(int position){
        User u = data.get(position);
        if (u.getName().charAt(u.getName().length() - 1) == '7'){
            return 1;
        }
        else{
            return 0;
        }
    }

    public int getItemCount(){
        return data.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView description;

        public myViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.NameView);
            description = itemView.findViewById(R.id.descriptionView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}

