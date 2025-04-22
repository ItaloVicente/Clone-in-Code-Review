package org.eclipse.jface.examples.databinding.mask.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class SWTUtil {
    private static Map mapDisplayOntoWorkQueue = new HashMap();

    private SWTUtil() {
    }

    public static void greedyExec(Display d, Runnable r) {
        if (d.isDisposed()) {
            return;
        }

        WorkQueue queue = getQueueFor(d);
        queue.asyncExec(r);
    }

    public static void runOnce(Display d, Runnable r) {
        if (d.isDisposed()) {
            return;
        }
        WorkQueue queue = getQueueFor(d);
        queue.runOnce(r);
    }

    public static void cancelExec(Display d, Runnable r) {
        if (d.isDisposed()) {
            return;
        }
        WorkQueue queue = getQueueFor(d);
        queue.cancelExec(r);
    }

    private static WorkQueue getQueueFor(final Display d) {
        WorkQueue result;
        synchronized (mapDisplayOntoWorkQueue) {
            result = (WorkQueue) mapDisplayOntoWorkQueue.get(d);

            if (result == null) {
                result = new WorkQueue(d);
                final WorkQueue q = result;
                mapDisplayOntoWorkQueue.put(d, result);
                d.asyncExec(new Runnable() {
                    @Override
					public void run() {
                        d.disposeExec(new Runnable() {
                            @Override
							public void run() {
                                synchronized (mapDisplayOntoWorkQueue) {
                                    q.cancelAll();
                                    mapDisplayOntoWorkQueue.remove(d);
                                }
                            }
                        });
                    }
                });
            }
            return result;
        }
    }

    public static RGB mix(RGB rgb1, RGB rgb2, double ratio) {
        return new RGB(interp(rgb1.red, rgb2.red, ratio),
                interp(rgb1.green, rgb2.green, ratio),
                interp(rgb1.blue, rgb2.blue, ratio));
    }

    private static int interp(int i1, int i2, double ratio) {
        int result = (int)(i1 * ratio + i2 * (1.0d - ratio));
        if (result < 0) result = 0;
        if (result > 255) result = 255;
        return result;
    }
}
