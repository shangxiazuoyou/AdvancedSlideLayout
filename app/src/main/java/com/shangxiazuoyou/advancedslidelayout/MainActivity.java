package com.shangxiazuoyou.advancedslidelayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import shangxiazuoyou.advancedslidelayout.library.AdvancedSlideLayout;
import shangxiazuoyou.advancedslidelayout.library.LayerTransformer;
import shangxiazuoyou.advancedslidelayout.library.transformer.AlphaTransformer;
import shangxiazuoyou.advancedslidelayout.library.transformer.RotationTransformer;
import shangxiazuoyou.advancedslidelayout.library.transformer.SlideJoyTransformer;

public class MainActivity extends AppCompatActivity {

    private AdvancedSlideLayout advancedSlideLayout;
    private TextView swipeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        initToolbar();
        initState();
    }

    private void bindViews() {
        advancedSlideLayout = (AdvancedSlideLayout) findViewById(R.id.advancedSlideLayout);
        swipeText = (TextView) findViewById(R.id.swipeText);
    }

    /**
     * Initializes the origin state of the layer
     */
    private void initState() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        setupSlidingLayerPosition(prefs.getString("layer_location", "right"));
        setupSlidingLayerTransform(prefs.getString("layer_transform", "none"));

        setupShadow(prefs.getBoolean("layer_has_shadow", false));
        setupLayerOffset(prefs.getBoolean("layer_has_offset", false));
        setupPreviewMode(prefs.getBoolean("preview_mode_enabled", false));
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupSlidingLayerPosition(String layerPosition) {
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) advancedSlideLayout.getLayoutParams();
        int textResource;
        switch (layerPosition) {
            case "right":
                textResource = R.string.swipe_right_label;
                advancedSlideLayout.setStickTo(AdvancedSlideLayout.STICK_TO_RIGHT);
                break;
            case "left":
                textResource = R.string.swipe_left_label;
                advancedSlideLayout.setStickTo(AdvancedSlideLayout.STICK_TO_LEFT);
                break;
            case "top":
                textResource = R.string.swipe_up_label;
                advancedSlideLayout.setStickTo(AdvancedSlideLayout.STICK_TO_TOP);
                rlp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                rlp.height = getResources().getDimensionPixelSize(R.dimen.layer_size);
                break;
            default:
                textResource = R.string.swipe_down_label;
                advancedSlideLayout.setStickTo(AdvancedSlideLayout.STICK_TO_BOTTOM);
                rlp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                rlp.height = getResources().getDimensionPixelSize(R.dimen.layer_size);
        }
        swipeText.setText(getResources().getString(textResource));
        advancedSlideLayout.setLayoutParams(rlp);
    }

    private void setupSlidingLayerTransform(String layerTransform) {
        LayerTransformer transformer;
        switch (layerTransform) {
            case "alpha":
                transformer = new AlphaTransformer();
                break;
            case "rotation":
                transformer = new RotationTransformer();
                break;
            case "slide":
                transformer = new SlideJoyTransformer();
                break;
            default:
                return;
        }
        advancedSlideLayout.setLayerTransformer(transformer);
    }

    private void setupShadow(boolean enabled) {
        if (enabled) {
            advancedSlideLayout.setShadowSizeRes(R.dimen.shadow_size);
            advancedSlideLayout.setShadowDrawable(R.drawable.sidebar_shadow);
        } else {
            advancedSlideLayout.setShadowSize(0);
            advancedSlideLayout.setShadowDrawable(null);
        }
    }

    private void setupLayerOffset(boolean enabled) {
        int offsetDistance = enabled ? getResources().getDimensionPixelOffset(R.dimen.offset_distance) : 0;
        advancedSlideLayout.setOffsetDistance(offsetDistance);
    }

    private void setupPreviewMode(boolean enabled) {
        int previewOffset = enabled ? getResources().getDimensionPixelOffset(R.dimen.preview_offset_distance) : -1;
        advancedSlideLayout.setPreviewOffsetDistance(previewOffset);
    }

    public void buttonClicked(View v) {
        switch (v.getId()) {
            case R.id.buttonOpen:
                advancedSlideLayout.openLayer(true);
                break;
            case R.id.buttonClose:
                advancedSlideLayout.closeLayer(true);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (advancedSlideLayout.isOpened()) {
                    advancedSlideLayout.closeLayer(true);
                    return true;
                }

            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
