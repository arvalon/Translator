package ru.arvalon.translator.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ru.arvalon.translator.R;
import ru.arvalon.translator.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    public static final String LOGTAG = "mytranslator";

    public TabLayout tabLayout;

    private ViewPager viewPager;

    public ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // подготовка тулбара
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // подготовка ViewPager'а
        viewPager=findViewById(R.id.viewpager);
        setViewPager();

        // подготовка вкладок, связывание с ViewPager'ом
        tabLayout=findViewById(R.id.tabs);
        setTabs();
    }

    private void setViewPager() {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new TranslateFragment(),getString(R.string.translate_fragment_title));
        adapter.addFragment(new DictionaryFragment(),getString(R.string.dictionary_fragment_title));

        viewPager.setAdapter(adapter);
    }

    private void setTabs() {
        tabLayout.setupWithViewPager(viewPager,true);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_translate);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_dictionary);
    }
}
