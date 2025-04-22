package org.eclipse.ui.internal.layout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

public class LayoutCache {
    private SizeCache[] caches = new SizeCache[0];

    public LayoutCache() {
    }

    public LayoutCache(Control[] controls) {
        rebuildCache(controls);
    }

    public SizeCache getCache(int idx) {
        return caches[idx];
    }

    public void setControls(Control[] controls) {
        if (controls.length != caches.length) {
            rebuildCache(controls);
            return;
        }

        for (int idx = 0; idx < controls.length; idx++) {
            caches[idx].setControl(controls[idx]);
        }
    }

    private void rebuildCache(Control[] controls) {
        SizeCache[] newCache = new SizeCache[controls.length];

        for (int idx = 0; idx < controls.length; idx++) {
            if (idx < caches.length) {
                newCache[idx] = caches[idx];
                newCache[idx].setControl(controls[idx]);
            } else {
                newCache[idx] = new SizeCache(controls[idx]);
            }
        }

        caches = newCache;
    }

    public Point computeSize(int controlIndex, int widthHint, int heightHint) {
        return caches[controlIndex].computeSize(widthHint, heightHint);
    }

    public void flush(int controlIndex) {
        caches[controlIndex].flush();
    }

    public void flush() {
        for (int idx = 0; idx < caches.length; idx++) {
            caches[idx].flush();
        }
    }
}
