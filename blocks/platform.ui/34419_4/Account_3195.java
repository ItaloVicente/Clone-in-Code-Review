package org.eclipse.jface.examples.databinding.mask.internal;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class WorkQueue {

    private boolean updateScheduled = false;

    private boolean paintListenerAttached = false;

    private LinkedList pendingWork = new LinkedList();

    private Display d;

    private Set pendingWorkSet = new HashSet();

    private Runnable updateJob = new Runnable() {
        @Override
		public void run() {
            doUpdate();
            updateScheduled = false;
        }
    };

    private Listener paintListener = new Listener() {
        @Override
		public void handleEvent(Event event) {
            paintListenerAttached = false;
            d.removeFilter(SWT.Paint, this);
            doUpdate();
        }
    };

    public WorkQueue(Display targetDisplay) {
        d = targetDisplay;
    }

    private void doUpdate() {
        for (;;) {
            Runnable next;
            synchronized (pendingWork) {
                if (pendingWork.isEmpty()) {
                    break;
                }
                next = (Runnable) pendingWork.removeFirst();
                pendingWorkSet.remove(next);
            }

            next.run();
        }

    }

    public void runOnce(Runnable work) {
        synchronized (pendingWork) {
            if (pendingWorkSet.contains(work)) {
                return;
            }

            pendingWorkSet.add(work);

            asyncExec(work);
        }
    }

    public void asyncExec(Runnable work) {
        synchronized (pendingWork) {
            pendingWork.add(work);
            if (!updateScheduled) {
                updateScheduled = true;
                d.asyncExec(updateJob);
            }

            if (Display.getCurrent() == d) {
                if (!paintListenerAttached) {
                    paintListenerAttached = true;
                    d.addFilter(SWT.Paint, paintListener);
                }
            }
        }
    }

    public void cancelExec(Runnable toCancel) {
        synchronized (pendingWork) {
            pendingWork.remove(toCancel);
            pendingWorkSet.remove(toCancel);
        }
    }

    public void cancelAll() {
        synchronized (pendingWork) {
            pendingWork.clear();
            pendingWorkSet.clear();
        }
    }
}
