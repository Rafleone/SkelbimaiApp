package skelbimas.lt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Locale;

import skelbimas.lt.Category.PhonesCategory;
import skelbimas.lt.R;

public class Home2Activity extends AppCompatActivity {

    private LinearLayout btnPhones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        btnPhones = findViewById(R.id.btnPhones);

        btnPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home2Activity.this, PhonesCategory.class);
                startActivity(intent);
            }
        });
    }
}
