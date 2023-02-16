package ru.arvalon.translator.model;

//import androidx.core.util.LruCache;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.arvalon.translator.net.NetworkUtility;
import ru.arvalon.translator.net.YandexTranslateService;

import static ru.arvalon.translator.ui.MainActivity.LOGTAG;

import androidx.collection.LruCache;

public class Repository {

    private YandexTranslateService yts = NetworkUtility.getTranslateService();

    private LruCache<String, Translate> lru = new LruCache<>(100);

    private void put(String direction, String text, Translate translate)
    {
        lru.put(makeKey(direction, text), translate);
    }

    private String makeKey(String direction, String text)
    {
        return direction + text;
    }

    private Observable<Translate> getCache(String direction, String text)
    {
        Translate translate = lru.get(makeKey(direction,text));

        if (translate != null) {
            Log.d(LOGTAG, "from cache");
            return Observable.just(translate);
        }
        else{
            Log.d(LOGTAG, "from net");
            return Observable.empty();
        }
    }

    public Observable<Translate> translate(String direction, String text)
    {
        return getCache(direction, text)
                .switchIfEmpty(yts.translateReactive(direction, text)
                .doOnNext(t -> put(direction, text, t)))
                .subscribeOn(Schedulers.computation());
    }

    private Repository() { }

    private static Repository instance;

    public static Repository getInstance()
    {
        if(instance == null)
            instance = new Repository();
        return instance;
    }

}
