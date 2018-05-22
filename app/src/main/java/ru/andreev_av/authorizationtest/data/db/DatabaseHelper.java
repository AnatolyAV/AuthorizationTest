package ru.andreev_av.authorizationtest.data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.VisibleForTesting;

import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import ru.andreev_av.authorizationtest.data.model.WeatherCurrentDataModel;
import ru.andreev_av.authorizationtest.domain.model.WeatherCurrent;
import ru.andreev_av.authorizationtest.utils.WeatherCurrentConverter;

@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        this(dbOpenHelper, Schedulers.io());
    }

    @VisibleForTesting
    public DatabaseHelper(DbOpenHelper dbOpenHelper, Scheduler scheduler) {
        SqlBrite.Builder briteBuilder = new SqlBrite.Builder();
        mDb = briteBuilder.build().wrapDatabaseHelper(dbOpenHelper, scheduler);
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    public Observable<WeatherCurrent> setWeatherCurrent(final WeatherCurrentDataModel weatherCurrentDataModel) {
        return Observable.create(new ObservableOnSubscribe<WeatherCurrent>() {
            @Override
            public void subscribe(ObservableEmitter<WeatherCurrent> emitter) throws Exception {
                if (emitter.isDisposed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Db.WeatherCurrentTable.WeatherCurrentEntry.TABLE_NAME, null);
                    long result = mDb.insert(Db.WeatherCurrentTable.WeatherCurrentEntry.TABLE_NAME,
                            Db.WeatherCurrentTable.toContentValues(weatherCurrentDataModel),
                            SQLiteDatabase.CONFLICT_REPLACE);
                    transaction.markSuccessful();
                    if (result >= 0) {
                        WeatherCurrent weatherCurrent = WeatherCurrentConverter.convert(weatherCurrentDataModel);
                        emitter.onNext(weatherCurrent);
                    }
                    emitter.onComplete();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<WeatherCurrent> getWeatherCurrent() {
        return Observable.fromCallable(new Callable<WeatherCurrent>() {

            @Override
            public WeatherCurrent call() throws Exception {
                Cursor cursor = null;
                WeatherCurrent weatherCurrent = null;
                try {
                    cursor = mDb.query("SELECT * FROM " + Db.WeatherCurrentTable.WeatherCurrentEntry.TABLE_NAME);
                    cursor.moveToFirst();
                    if (cursor.getCount()!=0 ) {
                        weatherCurrent = Db.WeatherCurrentTable.parseCursor(cursor);
                    }else{
                        weatherCurrent = WeatherCurrent.builder().buildEmpty();
                    }
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
                return weatherCurrent;

            }
        });

// Старый вариант (выполнялся в потоке UI)
//        return mDb.createQuery(Db.WeatherCurrentTable.WeatherCurrentEntry.TABLE_NAME,
//                "SELECT * FROM " + Db.WeatherCurrentTable.WeatherCurrentEntry.TABLE_NAME)
//                .mapToOne(new Function<Cursor, WeatherCurrent>() {
//                    @Override
//                    public WeatherCurrent apply(@NonNull Cursor cursor) throws Exception {
//                        return Db.WeatherCurrentTable.parseCursor(cursor);
//                    }
//                });
    }
}
