package shangxiazuoyou.advancedslidelayout.library.transformer;

import android.view.View;

import shangxiazuoyou.advancedslidelayout.library.LayerTransformer;

public final class AlphaTransformer extends LayerTransformer {

    private static final int DEFAULT_MULTIPLIER = 1;

    private final float mMultiplier;

    public AlphaTransformer() {
        this(DEFAULT_MULTIPLIER);
    }

    public AlphaTransformer(float multiplier) {
        mMultiplier = multiplier;
    }

    @Override
    public void transform(View layerView, float previewProgress, float layerProgress) {
        final float progressRatioToAnimate = Math.max(previewProgress, layerProgress);
        final float alpha = Math.max(0, Math.min(1, progressRatioToAnimate * mMultiplier));
        layerView.setAlpha(alpha);
    }
}
