package ru.andreev_av.authorizationtest;

import android.database.Cursor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import io.reactivex.observers.TestObserver;
import ru.andreev_av.authorizationtest.data.db.DatabaseHelper;
import ru.andreev_av.authorizationtest.data.db.Db;
import ru.andreev_av.authorizationtest.data.db.DbOpenHelper;
import ru.andreev_av.authorizationtest.data.model.WeatherCurrentDataModel;
import ru.andreev_av.authorizationtest.domain.model.WeatherCurrent;
import ru.andreev_av.authorizationtest.util.DefaultConfig;
import ru.andreev_av.authorizationtest.util.RxSchedulersOverrideRule;
import ru.andreev_av.authorizationtest.utils.WeatherCurrentConverter;

import static junit.framework.Assert.assertEquals;

/**
 * Unit tests integration with a SQLite Database using Robolectric
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class DatabaseHelperTest {

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    private DatabaseHelper mDatabaseHelper;

    @Before
    public void setup() {
        if (mDatabaseHelper == null)
            mDatabaseHelper = new DatabaseHelper(new DbOpenHelper(RuntimeEnvironment.application),
                    mOverrideSchedulersRule.getScheduler());
    }

    @Test
    public void setWeatherCurrent() {
        WeatherCurrentDataModel weatherCurrentDataModel = TestDataFactory.createWeatherCurrentDataModel();

        TestObserver<WeatherCurrent> result = new TestObserver<>();
        mDatabaseHelper.setWeatherCurrent(weatherCurrentDataModel).subscribe(result);
        result.assertNoErrors();

        WeatherCurrent weatherCurrent = WeatherCurrentConverter.convert(weatherCurrentDataModel);

        assertEquals(weatherCurrent, result.values().get(0));

        Cursor cursor = mDatabaseHelper.getBriteDb()
                .query("SELECT * FROM " + Db.WeatherCurrentTable.WeatherCurrentEntry.TABLE_NAME);
        assertEquals(1, cursor.getCount());

        cursor.moveToFirst();
        assertEquals(weatherCurrent, Db.WeatherCurrentTable.parseCursor(cursor));
        cursor.close();
    }

    @Test
    public void getWeatherCurrent() {
        WeatherCurrentDataModel weatherCurrentDataModel = TestDataFactory.createWeatherCurrentDataModel();

        mDatabaseHelper.setWeatherCurrent(weatherCurrentDataModel).subscribe();

        TestObserver<WeatherCurrent> result = new TestObserver<>();
        mDatabaseHelper.getWeatherCurrent().subscribe(result);
        result.assertNoErrors();

        WeatherCurrent weatherCurrent = WeatherCurrentConverter.convert(weatherCurrentDataModel);

        assertEquals(weatherCurrent, result.values().get(0));
    }

}
