
package org.eclipse.ui.internal.activities;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IActivityManagerListener;

public abstract class AbstractActivityManager implements IActivityManager {
	private ListenerList activityManagerListeners;

    protected AbstractActivityManager() {
    }

    @Override
	public void addActivityManagerListener(
            IActivityManagerListener activityManagerListener) {
        if (activityManagerListener == null) {
			throw new NullPointerException();
		}

        if (activityManagerListeners == null) {
			activityManagerListeners = new ListenerList();
		}

		activityManagerListeners.add(activityManagerListener);
    }

    protected void fireActivityManagerChanged(
            ActivityManagerEvent activityManagerEvent) {
        if (activityManagerEvent == null) {
			throw new NullPointerException();
		}

		if (activityManagerListeners != null) {
			Object[] listeners = activityManagerListeners.getListeners();
			for (int i = 0; i < listeners.length; i++) {
				((IActivityManagerListener) listeners[i])
						.activityManagerChanged(activityManagerEvent);
			}
		}
    }

    @Override
	public void removeActivityManagerListener(
            IActivityManagerListener activityManagerListener) {
        if (activityManagerListener == null) {
			throw new NullPointerException();
		}

        if (activityManagerListeners != null) {
			activityManagerListeners.remove(activityManagerListener);
		}
    }
}
