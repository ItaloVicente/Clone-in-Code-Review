
package org.eclipse.ui.activities;

public final class IdentifierEvent {
    private boolean activityIdsChanged;

    private boolean enabledChanged;

    private IIdentifier identifier;

    public IdentifierEvent(IIdentifier identifier, boolean activityIdsChanged,
            boolean enabledChanged) {
        if (identifier == null) {
			throw new NullPointerException();
		}

        this.identifier = identifier;
        this.activityIdsChanged = activityIdsChanged;
        this.enabledChanged = enabledChanged;
    }

    public IIdentifier getIdentifier() {
        return identifier;
    }

    public boolean hasActivityIdsChanged() {
        return activityIdsChanged;
    }

    public boolean hasEnabledChanged() {
        return enabledChanged;
    }
}
