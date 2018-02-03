/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */

package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox checkChoco = (CheckBox) findViewById(R.id.checkbox_chocolate);
        boolean choco = checkChoco.isChecked();
        CheckBox check = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
        boolean checked = check.isChecked();


        EditText input = (EditText) findViewById(R.id.input);
        String name = input.getText().toString();

        Log.v("MainActivity", "Has whipped cream" + checked);

        int price = calculatePrice(checked, choco);
        createOrderSummary(name, price, checked, choco);
        String priceMessage =createOrderSummary(name, price,checked, choco);
        displayMessage(priceMessage);

//        display(quantity);
//        displayPrice(quantity * 5);

        //intent part

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order for" + name);
        intent.putExtra(Intent.EXTRA_SUBJECT, priceMessage);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.summary_text_view);
        priceTextView.setText(message);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    int quantity=0;
    //this method will decrement number of items
    public void decrement(View view){


        if (quantity<=0){
            Toast.makeText(this, "You need to order at least one coffee", Toast.LENGTH_SHORT).show();
            quantity=0;
        }
        if (quantity>=1) {
            quantity -= 1;
        }
        display(quantity);

    }
    //this method will increment number of items
    public void increment(View view) {

        quantity += 1;
        if (quantity>= 100) {
            Toast.makeText(this, "You can order maximum 100 coffees", Toast.LENGTH_SHORT).show();
            quantity = 100;
        }
        display(quantity);


    }
    //this method will calculate appropriate price
    public int calculatePrice(boolean whippedCream, boolean chocolate){
        int price = quantity*5;
        if (chocolate){
            price= quantity*7;
        }
        if (whippedCream){
            price = quantity*6;
        }
        if (chocolate&&whippedCream){
            price = quantity *8;
        }

        return price;
    }

    //summary order method

    private String createOrderSummary(String name, int price, boolean addCream, boolean addChocolate){
        return getString(R.string.user_name, name) + "\n Add whipped cream?"+ addCream
                    + "\n Add chocolate?" + addChocolate
                    + "\nQuantity: " + quantity
                    + "\nTotal: " + price+"$"
                    + "\n"+ getString(R.string.thank_you);
    }

}