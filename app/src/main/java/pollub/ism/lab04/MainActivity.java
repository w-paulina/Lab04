package pollub.ism.lab04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button [] przyciski = new Button[9];
    int [] stanGry = {0,0,0,0,0,0,0,0,0};
    private boolean aktywnyGracz;
    private int licznikRuchow;

    int [][] pozycjeWygrane = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0; i<przyciski.length; i++){
            String IDprzycisku = "button" + i;
            int resourceID = getResources().getIdentifier(IDprzycisku, "id",getPackageName());
            przyciski[i] = (Button) findViewById(resourceID);
            przyciski[i].setOnClickListener(this);
        }

        licznikRuchow = 0;
        aktywnyGracz = true;
    }

    @Override
    public void onClick(View v) {

        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if(aktywnyGracz){
            ((Button)v).setText("O");
            stanGry[gameStatePointer] = 1;
        }else {
            ((Button)v).setText("X");
            stanGry[gameStatePointer] = 2;
        }
        licznikRuchow++;

        if(checkWinner()){
            if(aktywnyGracz){
                Toast.makeText(this,"Wygrały O", Toast.LENGTH_LONG).show();
                zagrajPonownie();
            }else{
                Toast.makeText(this,"Wygrały X", Toast.LENGTH_LONG).show();
                zagrajPonownie();
            }
        }else if (licznikRuchow == 9){
            zagrajPonownie();
        } else
            aktywnyGracz = !aktywnyGracz;
    }

    public boolean checkWinner(){
        boolean zwyciezca = false;

        for (int [] pozycjaWygrana : pozycjeWygrane){
            if(stanGry[pozycjaWygrana[0]] == stanGry[pozycjaWygrana[1]] &&
                    stanGry[pozycjaWygrana[1]] == stanGry[pozycjaWygrana[2]] &&
                    stanGry[pozycjaWygrana[0]] != 0){
                zwyciezca = true;
            }
        }
        return zwyciezca;
    }

    public void zagrajPonownie(){
        licznikRuchow = 0;
        aktywnyGracz = true;

        for(int i=0; i<przyciski.length; i++){
            stanGry[i]=0;
            przyciski[i].setText("");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("stanGry", stanGry);
        outState.putBoolean("aktywnyGracz",aktywnyGracz);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        stanGry = savedInstanceState.getIntArray("stanGry");
        aktywnyGracz = savedInstanceState.getBoolean("aktywnyGracz");
    }
}