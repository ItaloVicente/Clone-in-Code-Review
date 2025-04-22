
package org.eclipse.ui.activities;

import java.util.Set;

import org.eclipse.ui.internal.util.Util;

public final class ActivityManagerEvent {
    private IActivityManager activityManager;

    private boolean definedActivityIdsChanged;

    private boolean definedCategoryIdsChanged;

    private boolean enabledActivityIdsChanged;

    private final Set previouslyDefinedActivityIds;

    private final Set previouslyDefinedCategoryIds;

    private final Set previouslyEnabledActivityIds;

    public ActivityManagerEvent(IActivityManager activityManager,
            boolean definedActivityIdsChanged,
            boolean definedCategoryIdsChanged,
            boolean enabledActivityIdsChanged,
            final Set previouslyDefinedActivityIds,
            final Set previouslyDefinedCategoryIds,
            final Set previouslyEnabledActivityIds) {
        if (activityManager == null) {
			throw new NullPointerException();
		}

        if (!definedActivityIdsChanged && previouslyDefinedActivityIds != null) {
			throw new IllegalArgumentException();
		}

        if (!definedCategoryIdsChanged && previouslyDefinedCategoryIds != null) {
			throw new IllegalArgumentException();
		}

        if (!enabledActivityIdsChanged && previouslyEnabledActivityIds != null) {
			throw new IllegalArgumentException();
		}

        if (definedActivityIdsChanged) {
            this.previouslyDefinedActivityIds = Util.safeCopy(
                    previouslyDefinedActivityIds, String.class);
        } else {
            this.previouslyDefinedActivityIds = null;
        }

        if (definedCategoryIdsChanged) {
            this.previouslyDefinedCategoryIds = Util.safeCopy(
                    previouslyDefinedCategoryIds, String.class);
        } else {
            this.previouslyDefinedCategoryIds = null;
        }

        if (enabledActivityIdsChanged) {
            this.previouslyEnabledActivityIds = Util.safeCopy(
                    previouslyEnabledActivityIds, String.class);
        } else {
            this.previouslyEnabledActivityIds = null;
        }

        this.activityManager = activityManager;
        this.definedActivityIdsChanged = definedActivityIdsChanged;
        this.definedCategoryIdsChanged = definedCategoryIdsChanged;
        this.enabledActivityIdsChanged = enabledActivityIdsChanged;
    }

    public IActivityManager getActivityManager() {
        return activityManager;
    }

    public final Set getPreviouslyDefinedActivityIds() {
        return previouslyDefinedActivityIds;
    }

    public final Set getPreviouslyDefinedCategoryIds() {
        return previouslyDefinedCategoryIds;
    }

    public final Set getPreviouslyEnabledActivityIds() {
        return previouslyEnabledActivityIds;
    }

    public boolean haveDefinedActivityIdsChanged() {
        return definedActivityIdsChanged;
    }

    public boolean haveDefinedCategoryIdsChanged() {
        return definedCategoryIdsChanged;
    }

    public boolean haveEnabledActivityIdsChanged() {
        return enabledActivityIdsChanged;
    }
}
