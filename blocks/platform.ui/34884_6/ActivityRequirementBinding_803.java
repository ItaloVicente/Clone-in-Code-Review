
package org.eclipse.ui.internal.activities;

final class ActivityRegistryEvent {
    private IActivityRegistry activityRegistry;

    ActivityRegistryEvent(IActivityRegistry activityRegistry) {
        if (activityRegistry == null) {
			throw new NullPointerException();
		}

        this.activityRegistry = activityRegistry;
    }

    IActivityRegistry getActivityRegistry() {
        return activityRegistry;
    }
}
