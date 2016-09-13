package com.example.androidshoppinglist.actions;

import com.example.androidshoppinglist.TestSubscriber;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by joanna on 13.09.16.
 */


@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ActionCreatorTest {

    @Rule
    public Timeout globalTimeout = new Timeout(2, TimeUnit.SECONDS);

    protected CountDownLatch signal;
    protected static EventBus eventBus;
    protected static ActionCreator actionCreator;
    protected static String title;

    @BeforeClass
    public static void setup() {
        eventBus = EventBus.getDefault();
        actionCreator = new ActionCreator(eventBus);
        title = "test";
    }

    @Before
    public void setSignal() {
        signal = new CountDownLatch(1);
    }

    @Test
    public void addNewShoppingListActionTest() throws InterruptedException {
        TestSubscriber testSubscriber = new TestSubscriber();
        eventBus.register(testSubscriber);

        actionCreator.addNewShoppingListAction(getTitle());

        assertThat(testSubscriber.getResultAction().getData()
                .get(DataKeys.SHOPPING_LIST_TITLE, ""))
                .isEqualTo(getTitle());
    }

    public static String getTitle() {
        return title;
    }
}

