
package org.eclipse.ui.contexts;

import java.util.Set;

import org.eclipse.ui.internal.util.Util;

@Deprecated
public final class ContextManagerEvent {

    private final IContextManager contextManager;

    private final boolean definedContextIdsChanged;

    private final boolean enabledContextIdsChanged;

    private final Set previouslyDefinedContextIds;

    private final Set previouslyEnabledContextIds;

    public ContextManagerEvent(IContextManager contextManager,
            boolean definedContextIdsChanged, boolean enabledContextIdsChanged,
            Set previouslyDefinedContextIds, Set previouslyEnabledContextIds) {
        if (contextManager == null) {
			throw new NullPointerException();
		}

        if (!definedContextIdsChanged && previouslyDefinedContextIds != null) {
			throw new IllegalArgumentException();
		}

        if (!enabledContextIdsChanged && previouslyEnabledContextIds != null) {
			throw new IllegalArgumentException();
		}

        if (definedContextIdsChanged) {
            this.previouslyDefinedContextIds = Util.safeCopy(
                    previouslyDefinedContextIds, String.class);
        } else {
            this.previouslyDefinedContextIds = null;
        }

        if (enabledContextIdsChanged) {
            this.previouslyEnabledContextIds = Util.safeCopy(
                    previouslyEnabledContextIds, String.class);
        } else {
            this.previouslyEnabledContextIds = null;
        }

        this.contextManager = contextManager;
        this.definedContextIdsChanged = definedContextIdsChanged;
        this.enabledContextIdsChanged = enabledContextIdsChanged;
    }

    public IContextManager getContextManager() {
        return contextManager;
    }

    public Set getPreviouslyDefinedContextIds() {
        return previouslyDefinedContextIds;
    }

    public Set getPreviouslyEnabledContextIds() {
        return previouslyEnabledContextIds;
    }

    public boolean haveDefinedContextIdsChanged() {
        return definedContextIdsChanged;
    }

    public boolean haveEnabledContextIdsChanged() {
        return enabledContextIdsChanged;
    }
}
