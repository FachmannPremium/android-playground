package lt.ro.fachmann.lab1;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {
    public static final int IMAGE_ANIMATION_DURATION = 1000;
    public static final int IMAGE_ANIMATION_DELAY = 500;

    @BindView(R.id.image_author_face)
    ImageView imageCircle;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        imageCircle.animate()
                .rotation(-180)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(IMAGE_ANIMATION_DURATION)
                .setStartDelay(IMAGE_ANIMATION_DELAY);
        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);
    }

    public void openPersonalSite(View view) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_site)));
            startActivity(browserIntent);
        } catch (ActivityNotFoundException e) {
            showToast(R.string.share_error);
        }
    }

    private void showToast(String message) {
        toast.setText(message);
        if (!toast.getView().isShown()) {
            toast.show();
        }
    }

    private void showToast(int resId) {
        showToast(getString(resId));
    }

}
