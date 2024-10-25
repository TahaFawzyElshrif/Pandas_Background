package pandas.android.pansasbackground;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import pandas.android.pansasbackground.fragments.choose_op;

public class fragments_place extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragments_place);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        choode_op();
    }
    private void choode_op() {
        choose_op f2 = new choose_op();
        FragmentManager f_mang = getSupportFragmentManager();
        FragmentTransaction f_mang_trans = f_mang.beginTransaction();
        f_mang_trans.add(R.id.fragmentContainerView, f2);
        f_mang_trans.commit();
    }
}