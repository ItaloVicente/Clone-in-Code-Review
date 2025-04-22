
package org.eclipse.ui.commands;

@Deprecated
@SuppressWarnings("all")
public final class KeyConfigurationEvent {

    private final boolean activeChanged;

    private final boolean definedChanged;

    private final IKeyConfiguration keyConfiguration;

    private final boolean nameChanged;

    private final boolean parentIdChanged;

	@Deprecated
    public KeyConfigurationEvent(IKeyConfiguration keyConfiguration,
            boolean activeChanged, boolean definedChanged, boolean nameChanged,
            boolean parentIdChanged) {
        if (keyConfiguration == null) {
			throw new NullPointerException();
		}

        this.keyConfiguration = keyConfiguration;
        this.activeChanged = activeChanged;
        this.definedChanged = definedChanged;
        this.nameChanged = nameChanged;
        this.parentIdChanged = parentIdChanged;
    }

	@Deprecated
    public IKeyConfiguration getKeyConfiguration() {
        return keyConfiguration;
    }

	@Deprecated
    public boolean hasActiveChanged() {
        return activeChanged;
    }

	@Deprecated
    public boolean hasDefinedChanged() {
        return definedChanged;
    }

	@Deprecated
    public boolean hasNameChanged() {
        return nameChanged;
    }

	@Deprecated
    public boolean hasParentIdChanged() {
        return parentIdChanged;
    }
}
