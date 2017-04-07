package lt.ro.fachmann.lab1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {
    public static final int IMAGE_ANIMATION_DURATION = 800;
    public static final int IMAGE_ANIMATION_DELAY = 500;

    @BindView(R.id.image_author_face)
    ImageView imageCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        imageCircle.animate()
                .rotation(180)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(IMAGE_ANIMATION_DURATION)
                .setStartDelay(IMAGE_ANIMATION_DELAY);
    }
}
