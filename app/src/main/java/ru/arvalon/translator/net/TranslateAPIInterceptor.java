package ru.arvalon.translator.net;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.arvalon.translator.BuildConfig;

public class TranslateAPIInterceptor implements Interceptor {

    private static final String KEY = "key";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        HttpUrl url = request.url().newBuilder()
                .addQueryParameter(KEY, BuildConfig.TRANSLATE_API_KEY)
                .build();

        request = request.newBuilder().url(url).build();

        return chain.proceed(request);

    }
}
