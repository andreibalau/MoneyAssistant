package moneyassistant.expert.injection;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.AllArgsConstructor;
import moneyassistant.expert.util.SharedDataHandler;

/**
 * MoneyAssistant
 * Created by catalin on 14.11.2019
 */
@Module
@AllArgsConstructor
public class AppModule {

    private Context context;

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    Gson gson() {
        return new Gson();
    }

    @Provides
    @Singleton
    SharedDataHandler sharedDataHandler(Context context, Gson gson) {
        return new SharedDataHandler(context, gson);
    }

}
