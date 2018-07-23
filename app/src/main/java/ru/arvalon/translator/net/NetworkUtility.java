package ru.arvalon.translator.net;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.arvalon.translator.BuildConfig;

public class NetworkUtility {

    private NetworkUtility() { }

    public static YandexTranslateService getTranslateService() {

        return getRetrofitBuilder(BuildConfig.TRANSLATE_API_ENDPOINT, new TranslateAPIInterceptor())
                        .build()
                        .create(YandexTranslateService.class);
    }

    private static Retrofit.Builder getRetrofitBuilder(String endPoint, Interceptor interceptor) {
        return new Retrofit.Builder()
                .client(new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create());
    }
}
