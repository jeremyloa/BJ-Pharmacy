package edu.bluejack22_1.bluejackpharmacy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.bluejack22_1.bluejackpharmacy.R;
import edu.bluejack22_1.bluejackpharmacy.controller.home.TransactionFragment;
import edu.bluejack22_1.bluejackpharmacy.model.Medicine;
import edu.bluejack22_1.bluejackpharmacy.model.Transaction;
import edu.bluejack22_1.bluejackpharmacy.model.User;

public class TransAdapter extends RecyclerView.Adapter<TransAdapter.TransVH> {
    ArrayList<Transaction> trans = new ArrayList<>();

    public TransAdapter(ArrayList<Transaction> trans){
        if (trans!=null && trans.size()>0) this.trans =trans;
    }
    @NonNull
    @Override
    public TransVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trans_item, parent, false);
        return new TransVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransAdapter.TransVH holder, int position) {
        Toast err = Toast.makeText(holder.transDate.getContext(), "Quantity should not be empty", Toast.LENGTH_SHORT);
        Toast succ = Toast.makeText(holder.transDate.getContext(), "Update quantity success", Toast.LENGTH_SHORT);
        Toast delsucc = Toast.makeText(holder.transDate.getContext(), "Delete success", Toast.LENGTH_SHORT);
        holder.transDate.setText(trans.get(holder.getAdapterPosition()).getDate());
        holder.transQty.setText(String.valueOf(trans.get(holder.getAdapterPosition()).getQuantity()));
        Medicine med = Medicine.GET_MEDICINE(trans.get(holder.getAdapterPosition()).getMedicineid());
        holder.transMedName.setText(med.getName());
        holder.transPrice.setText(String.valueOf(med.getPrice()));
        holder.transTotalPrice.setText(String.valueOf(med.getPrice() * trans.get(holder.getAdapterPosition()).getQuantity()));
        holder.updQty.setText(String.valueOf(trans.get(holder.getAdapterPosition()).getQuantity()));
        holder.goUpdate.setOnClickListener(view -> {
            if (holder.updQty.getText().toString() == null || holder.updQty.getText().toString().isEmpty() || Integer.parseInt(holder.updQty.getText().toString())<=0) err.show();
            else {
                Transaction.UPDATE_TRANS_QTY(trans.get(holder.getAdapterPosition()).getID(), Integer.parseInt(holder.updQty.getText().toString()));
                notifyItemChanged(holder.getAdapterPosition(), 1);
                trans = Transaction.GET_TRANSACTIONS_USERID(User.curr.getID());
                succ.show();
            }
        });
        holder.goDelete.setOnClickListener(view -> {
            Transaction.DELETE_TRANS(trans.get(position).getID());
            notifyItemRemoved(holder.getAdapterPosition());
            trans = Transaction.GET_TRANSACTIONS_USERID(User.curr.getID());
            delsucc.show();
        });
    }

    @Override
    public int getItemCount() {
        return trans.size();
    }

    class TransVH extends RecyclerView.ViewHolder{
        TextView transDate, transMedName, transPrice, transQty, transTotalPrice;
        EditText updQty;
        Button goUpdate, goDelete;
        public TransVH(@NonNull View itemView) {
            super(itemView);
            transDate = itemView.findViewById(R.id.transDate);
            transMedName = itemView.findViewById(R.id.transMedName);
            transPrice = itemView.findViewById(R.id.transPrice);
            transQty = itemView.findViewById(R.id.transQty);
            transTotalPrice = itemView.findViewById(R.id.transTotalPrice);
            updQty = itemView.findViewById(R.id.updQty);
            goUpdate = itemView.findViewById(R.id.goUpdate);
            goDelete = itemView.findViewById(R.id.goDelete);
        }
    }
}
