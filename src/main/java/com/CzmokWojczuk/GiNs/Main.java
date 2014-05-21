package com.CzmokWojczuk.GiNs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main extends Activity {
    /**
     * Called when the activity is first created.
     */

    Button start;
    TextView tekst;
    ODE licz = new ODE();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         start = (Button) findViewById(R.id.button);
        tekst = (TextView) findViewById(R.id.LenghtShow);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Double  leng [] = new Double[licz.N];
               leng= licz.rungeKutyy();
                int count = leng.length;
                Double cos = leng[100];
               tekst.setText(Double.toString(cos));    */
                Intent intent = new Intent(Main.this,Moja.class)  ;
                startActivity(intent);
            }
        });

    }
}
