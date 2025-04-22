
package org.eclipse.ui.contexts;

@Deprecated
public final class ContextEvent {

    private final IContext context;

    private final boolean definedChanged;

    private final boolean enabledChanged;

    private final boolean nameChanged;

    private final boolean parentIdChanged;

    public ContextEvent(IContext context, boolean definedChanged,
            boolean enabledChanged, boolean nameChanged, boolean parentIdChanged) {
        if (context == null) {
			throw new NullPointerException();
		}

        this.context = context;
        this.definedChanged = definedChanged;
        this.enabledChanged = enabledChanged;
        this.nameChanged = nameChanged;
        this.parentIdChanged = parentIdChanged;
    }

    public IContext getContext() {
        return context;
    }

    public boolean hasDefinedChanged() {
        return definedChanged;
    }

    public boolean hasEnabledChanged() {
        return enabledChanged;
    }

    public boolean hasNameChanged() {
        return nameChanged;
    }

    public boolean hasParentIdChanged() {
        return parentIdChanged;
    }
}
