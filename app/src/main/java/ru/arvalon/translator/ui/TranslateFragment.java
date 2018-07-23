package ru.arvalon.translator.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.arvalon.translator.R;
import ru.arvalon.translator.model.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class TranslateFragment extends Fragment {

    private static final String DIRECTION = "ru-en";

    private Repository repo = Repository.getInstance();

    private CompositeDisposable disposables = new CompositeDisposable();

    public TranslateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_translate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText text = view.findViewById(R.id.etTranslate);
        Button button = view.findViewById(R.id.btnTranslate);
        TextView textView = view.findViewById(R.id.tvTranslate);

        Disposable d = RxView.clicks(button)
                .debounce(2, TimeUnit.SECONDS)
                .map(c -> text.getText())
                .map(Editable::toString)
                .filter(s -> s.length()>0)
                .observeOn(Schedulers.computation())
                .switchMap(s -> repo.translate(DIRECTION,s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p->textView.setText(p.getText().toString()));

        disposables.add(d);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}
