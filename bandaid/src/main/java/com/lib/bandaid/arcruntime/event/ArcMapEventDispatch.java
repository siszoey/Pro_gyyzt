package com.lib.bandaid.arcruntime.event;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.lib.bandaid.arcruntime.core.ArcMap;

/**
 * Created by zy on 2019/5/28.
 */

public class ArcMapEventDispatch extends DefaultMapViewOnTouchListener {

    public ArcMapEventDispatch(ArcMap arcMap) {
        super(arcMap.getContext(), arcMap.getMapView());
    }

    

    private IArcMapEvent iMapEvent;


    public IArcMapEvent getCurIMapEvent() {
        return iMapEvent;
    }

    public void setCurIMapEvent(IArcMapEvent iMapEvent) {
        this.iMapEvent = iMapEvent;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if (iMapEvent != null) return iMapEvent.onDoubleTap(e);
        return super.onDoubleTap(e);
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        if (iMapEvent != null) return iMapEvent.onDoubleTapEvent(e);
        return super.onDoubleTapEvent(e);
    }

    @Override
    public boolean onDoubleTouchDrag(MotionEvent event) {
        if (iMapEvent != null) return iMapEvent.onDoubleTouchDrag(event);
        return super.onDoubleTouchDrag(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (iMapEvent != null) return iMapEvent.onDown(e);
        return super.onDown(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (iMapEvent != null) return iMapEvent.onFling(e1, e2, velocityX, velocityY);
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public boolean onMultiPointerTap(MotionEvent event) {
        if (iMapEvent != null) return iMapEvent.onMultiPointerTap(event);
        return super.onMultiPointerTap(event);
    }

    @Override
    public boolean onRotate(MotionEvent event, double rotationAngle) {
        if (iMapEvent != null) return iMapEvent.onRotate(event, rotationAngle);
        return super.onRotate(event, rotationAngle);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (iMapEvent != null) return iMapEvent.onScale(detector);
        return super.onScale(detector);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        if (iMapEvent != null) return iMapEvent.onScaleBegin(detector);
        return super.onScaleBegin(detector);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (iMapEvent != null) return iMapEvent.onScroll(e1, e2, distanceX, distanceY);
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (iMapEvent != null) return iMapEvent.onSingleTapConfirmed(e);
        return super.onSingleTapConfirmed(e);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (iMapEvent != null) return iMapEvent.onSingleTapUp(e);
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (iMapEvent != null) return iMapEvent.onTouch(view, event);
        return super.onTouch(view, event);
    }

    @Override
    public boolean onUp(MotionEvent e) {
        if (iMapEvent != null) return iMapEvent.onUp(e);
        return super.onUp(e);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        if (iMapEvent != null) iMapEvent.onLongPress(e);
        super.onLongPress(e);
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        if (iMapEvent != null) iMapEvent.onScaleEnd(detector);
        super.onScaleEnd(detector);
    }

    @Override
    public void onShowPress(MotionEvent e) {
        if (iMapEvent != null) iMapEvent.onShowPress(e);
        super.onShowPress(e);
    }
}
