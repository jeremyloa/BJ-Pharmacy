package edu.bluejack22_1.bluejackpharmacy.controller.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.bluejack22_1.bluejackpharmacy.R;
import edu.bluejack22_1.bluejackpharmacy.adapter.TransAdapter;
import edu.bluejack22_1.bluejackpharmacy.model.Transaction;
import edu.bluejack22_1.bluejackpharmacy.model.User;

public class TransactionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView rvTrans;
    TransAdapter transAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getTransDB();
        buildRecylclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        getTransDB();
        buildRecylclerView();
    }

    private void buildRecylclerView(){
        rvTrans = getView().findViewById(R.id.rvTrans);
        rvTrans.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        transAdapter = new TransAdapter(Transaction.dbTrans);
        rvTrans.setAdapter(transAdapter);
    }

    public static void getTransDB(){
        if (Transaction.dbTrans!=null) Transaction.dbTrans.clear();
        Transaction.dbTrans = Transaction.GET_TRANSACTIONS_USERID(User.curr.getID());
        if (Transaction.dbTrans.size()<=0) {
            Log.i("STATE", "dbTrans empty");
        } else {
            Log.i("STATE", "dbTrans not empty");
        }
    }
}