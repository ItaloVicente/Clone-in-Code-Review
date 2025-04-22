package org.eclipse.ui.commands;

import java.util.Set;
import org.eclipse.ui.internal.util.Util;

@Deprecated
@SuppressWarnings("all")
public final class CommandManagerEvent {

    private final boolean activeContextIdsChanged;

    private final boolean activeKeyConfigurationIdChanged;

    private final boolean activeLocaleChanged;

    private final boolean activePlatformChanged;

    private final ICommandManager commandManager;

    private final boolean definedCategoryIdsChanged;

    private final boolean definedCommandIdsChanged;

    private final boolean definedKeyConfigurationIdsChanged;

    private final Set previouslyDefinedCategoryIds;

    private final Set previouslyDefinedCommandIds;

    private final Set previouslyDefinedKeyConfigurationIds;

    public CommandManagerEvent(ICommandManager commandManager,
            boolean activeContextIdsChanged,
            boolean activeKeyConfigurationIdChanged,
            boolean activeLocaleChanged, boolean activePlatformChanged,
            boolean definedCategoryIdsChanged,
            boolean definedCommandIdsChanged,
            boolean definedKeyConfigurationIdsChanged,
            Set previouslyDefinedCategoryIds, Set previouslyDefinedCommandIds,
            Set previouslyDefinedKeyConfigurationIds) {
        if (commandManager == null) {
			throw new NullPointerException();
		}

        if (!definedCategoryIdsChanged && previouslyDefinedCategoryIds != null) {
			throw new IllegalArgumentException();
		}

        if (!definedCommandIdsChanged && previouslyDefinedCommandIds != null) {
			throw new IllegalArgumentException();
		}

        if (!definedKeyConfigurationIdsChanged
                && previouslyDefinedKeyConfigurationIds != null) {
			throw new IllegalArgumentException();
		}

        if (definedCategoryIdsChanged) {
            this.previouslyDefinedCategoryIds = Util.safeCopy(
                    previouslyDefinedCategoryIds, String.class);
        } else {
            this.previouslyDefinedCategoryIds = null;
        }

        if (definedCommandIdsChanged) {
            this.previouslyDefinedCommandIds = Util.safeCopy(
                    previouslyDefinedCommandIds, String.class);
        } else {
            this.previouslyDefinedCommandIds = null;
        }

        if (definedKeyConfigurationIdsChanged) {
            this.previouslyDefinedKeyConfigurationIds = Util.safeCopy(
                    previouslyDefinedKeyConfigurationIds, String.class);
        } else {
            this.previouslyDefinedKeyConfigurationIds = null;
        }

        this.commandManager = commandManager;
        this.activeContextIdsChanged = activeContextIdsChanged;
        this.activeKeyConfigurationIdChanged = activeKeyConfigurationIdChanged;
        this.activeLocaleChanged = activeLocaleChanged;
        this.activePlatformChanged = activePlatformChanged;
        this.definedCategoryIdsChanged = definedCategoryIdsChanged;
        this.definedCommandIdsChanged = definedCommandIdsChanged;
        this.definedKeyConfigurationIdsChanged = definedKeyConfigurationIdsChanged;
    }

	@Deprecated
    public ICommandManager getCommandManager() {
        return commandManager;
    }

	@Deprecated
    public Set getPreviouslyDefinedCategoryIds() {
        return previouslyDefinedCategoryIds;
    }

	@Deprecated
    public Set getPreviouslyDefinedCommandIds() {
        return previouslyDefinedCommandIds;
    }

	@Deprecated
    public Set getPreviouslyDefinedKeyConfigurationIds() {
        return previouslyDefinedKeyConfigurationIds;
    }

	@Deprecated
    public boolean hasActiveKeyConfigurationIdChanged() {
        return activeKeyConfigurationIdChanged;
    }

	@Deprecated
    public boolean hasActiveLocaleChanged() {
        return activeLocaleChanged;
    }

	@Deprecated
    public boolean hasActivePlatformChanged() {
        return activePlatformChanged;
    }

	@Deprecated
    public boolean haveActiveContextIdsChanged() {
        return activeContextIdsChanged;
    }

	@Deprecated
    public boolean haveDefinedCategoryIdsChanged() {
        return definedCategoryIdsChanged;
    }

	@Deprecated
    public boolean haveDefinedCommandIdsChanged() {
        return definedCommandIdsChanged;
    }

	@Deprecated
    public boolean haveDefinedKeyConfigurationIdsChanged() {
        return definedKeyConfigurationIdsChanged;
    }
}
