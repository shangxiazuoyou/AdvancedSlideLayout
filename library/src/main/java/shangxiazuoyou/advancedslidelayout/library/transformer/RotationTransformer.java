package shangxiazuoyou.advancedslidelayout.library.transformer;

import android.view.View;

import shangxiazuoyou.advancedslidelayout.library.LayerTransformer;
import shangxiazuoyou.advancedslidelayout.library.AdvancedSlideLayout;

public final class RotationTransformer extends LayerTransformer {

    private static final int DEFAULT_ANGLE = 10;
    private final float mMaxAngle;
    private float mAngle;

    public RotationTransformer() {
        this(DEFAULT_ANGLE);
    }

    public RotationTransformer(float maxAngle) {
        mMaxAngle = maxAngle;
    }

    @Override
    protected void onMeasure(View layerView, int screenSide) {
        final int[] pivotPosition = pivotPositionForScreenSide(layerView, screenSide);
        layerView.setPivotX(pivotPosition[0]);
        layerView.setPivotY(pivotPosition[1]);
        mAngle = mMaxAngle * (screenSide == AdvancedSlideLayout.STICK_TO_LEFT || screenSide == AdvancedSlideLayout.STICK_TO_TOP ? -1 : 1);
    }

    @Override
    public void transform(View layerView, float previewProgress, float layerProgress) {
    }

    @Override
    protected void internalTransform(View layerView, float previewProgress, float layerProgress, int screenSide) {
        final float progressRatioToAnimate = Math.max(previewProgress, layerProgress);
        layerView.setRotation(mAngle * (1 - progressRatioToAnimate));
    }

    private int[] pivotPositionForScreenSide(View layerView, int screenSide) {
        switch (screenSide) {
            case AdvancedSlideLayout.STICK_TO_LEFT:
                return new int[]{0, layerView.getMeasuredHeight()};
            case AdvancedSlideLayout.STICK_TO_TOP:
                return new int[]{0, 0};
            case AdvancedSlideLayout.STICK_TO_RIGHT:
                return new int[]{layerView.getMeasuredWidth(), layerView.getMeasuredHeight()};
            case AdvancedSlideLayout.STICK_TO_BOTTOM:
                return new int[]{0, layerView.getMeasuredHeight()};
            default:
                return new int[]{0, 0};
        }
    }
}
