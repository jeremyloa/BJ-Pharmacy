package edu.bluejack22_1.bluejackpharmacy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import edu.bluejack22_1.bluejackpharmacy.R;
import edu.bluejack22_1.bluejackpharmacy.model.Medicine;
import edu.bluejack22_1.bluejackpharmacy.model.Transaction;
import edu.bluejack22_1.bluejackpharmacy.model.User;

public class DetailMed extends AppCompatActivity {

    Medicine med;
    ImageView detailImg;
    TextView detailManu, detailName, detailPrice, detailDesc;

    EditText buyQty;
    Button goBuy;
    int qty = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_med);
        goBuy = findViewById(R.id.goBuy);

        detailImg = findViewById(R.id.detailImg);
        detailManu = findViewById(R.id.detailManu);
        detailName = findViewById(R.id.detailName);
        detailPrice = findViewById(R.id.detailPrice);
        detailDesc = findViewById(R.id.detailDesc);

        med = (Medicine) getIntent().getSerializableExtra("medicine");

        Picasso.get().load(med.getImage()).into(detailImg);
        detailManu.setText(med.getManufacturer());
        detailName.setText(med.getName());
        detailPrice.setText("Rp" + med.getPrice());
        detailDesc.setText(med.getDesc());

        goBuy.setOnClickListener(view -> {
            buyQty = findViewById(R.id.buyQty);
            try{
                qty = Integer.parseInt(buyQty.getText().toString());
            } catch (Exception e){
                qty = 0;
            }
            Toast tst = err();
            if (tst!=null) tst.show();
            else {
                Transaction.INSERT_TRANSACTION(new Transaction(User.curr.getID(), med.getID(), qty));
                tst = succ();
                tst.show();
                buyQty.setText("0");
            }
        });

    }

    private Toast err(){
       if (qty <=0) return Toast.makeText(this, "Quantity should be more than 0.", Toast.LENGTH_SHORT);
       return null;
    }

    private Toast succ(){
        return Toast.makeText(this, "Transaction successfully made.", Toast.LENGTH_SHORT);
    }
}