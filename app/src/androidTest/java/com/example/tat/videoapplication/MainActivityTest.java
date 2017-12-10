package com.example.tat.videoapplication;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.tat.videoapplication.data.model.Video;
import com.example.tat.videoapplication.rule.TestComponentRule;
import com.example.tat.videoapplication.ui.main.MainActivity;
import com.example.tat.videoapplication.ui.player.PlayerActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final IntentsTestRule<MainActivity> main =
            new IntentsTestRule<>(MainActivity.class, false, false);

    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(main);

    @Test
    public void shouldShowsListOfVideos() {
        List<Video> videos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            videos.add(
                    Video.builder()
                            .setTitle(String.format("Title%d", i))
                            .setDescription("Description")
                            .setPresenterName("PresenterName")
                            .setThumbnailUrl("https://example.com/thumbnail.jpg")
                            .setVideoUrl("https://example.com/video.jpg")
                            .setVideoDuration(60 * 1000)
                            .build()
            );
        }
        when(component.getMockDataManager().syncVideos())
                .thenReturn(Observable.empty());
        when(component.getMockDataManager().getVideos())
                .thenReturn(Observable.just(videos));

        main.launchActivity(null);

        int position = 0;
        for (Video video : videos) {
            onView(withId(R.id.video_list))
                    .perform(RecyclerViewActions.scrollToPosition(position));
            onView(withText(video.title()))
                    .check(matches(isDisplayed()));
            position++;
        }
    }

    @Test
    public void shouldStartsPlayerActivity() {
        List<Video> videos = Arrays.asList(
                Video.builder()
                        .setTitle("Title")
                        .setDescription("Description")
                        .setPresenterName("PresenterName")
                        .setThumbnailUrl("https://example.com/thumbnail.jpg")
                        .setVideoUrl("https://example.com/video.jpg")
                        .setVideoDuration(60 * 1000)
                        .build()
        );
        when(component.getMockDataManager().syncVideos())
                .thenReturn(Observable.empty());
        when(component.getMockDataManager().getVideos())
                .thenReturn(Observable.just(videos));

        main.launchActivity(null);

        onView(withId(R.id.video_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(PlayerActivity.class.getName()));
    }
}
