package by.gurinovich.googletask.guice;

import by.gurinovich.googletask.httpclient.HttpClient;
import com.google.inject.AbstractModule;

public class JSONPlaceholderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HttpClient.class).toInstance(new HttpClient());
    }
}
