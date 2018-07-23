package ru.arvalon.translator.net;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.arvalon.translator.model.Translate;

public interface YandexTranslateService {

    // https://translate.yandex.net/api/v1.5/tr.json/translate
    // ?key=trnsl.1.1.20180720T090321Z.a184442ad3f19ffa.dc592052fdf32f87d4fed7d2c36a46d07a5e5ba3
    // &text=привет
    // &lang=ru-fr
    // &format=html

    @GET("/api/v1.5/tr.json/translate")
    Observable<Translate> translateReactive(
            @Query("lang") String lang,
            @Query("text") String text
    );
}