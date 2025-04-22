package org.eclipse.ui.commands;

import java.util.Collections;
import java.util.Map;
import org.eclipse.ui.internal.util.Util;

@Deprecated
@SuppressWarnings("all")
public final class CommandEvent {

    private final boolean attributeValuesByNameChanged;

    private final boolean categoryIdChanged;

    private final ICommand command;

    private final boolean definedChanged;

    private final boolean descriptionChanged;

    private final boolean handledChanged;

    private final boolean keySequenceBindingsChanged;

    private final boolean nameChanged;

    private Map previousAttributeValuesByName;

    public CommandEvent(ICommand command, boolean attributeValuesByNameChanged,
            boolean categoryIdChanged, boolean definedChanged,
            boolean descriptionChanged, boolean handledChanged,
            boolean keySequenceBindingsChanged, boolean nameChanged,
            Map previousAttributeValuesByName) {
        if (command == null) {
			throw new NullPointerException();
		}

        if (!attributeValuesByNameChanged
                && previousAttributeValuesByName != null) {
			throw new IllegalArgumentException();
		}

        if (attributeValuesByNameChanged) {
        	if (previousAttributeValuesByName == null) {
				this.previousAttributeValuesByName = Collections.EMPTY_MAP;
			} else {
				this.previousAttributeValuesByName = Util.safeCopy(
						previousAttributeValuesByName, String.class,
						Object.class, false, true);
			}
        }

        this.command = command;
        this.attributeValuesByNameChanged = attributeValuesByNameChanged;
        this.categoryIdChanged = categoryIdChanged;
        this.definedChanged = definedChanged;
        this.descriptionChanged = descriptionChanged;
        this.handledChanged = handledChanged;
        this.keySequenceBindingsChanged = keySequenceBindingsChanged;
        this.nameChanged = nameChanged;
    }

	@Deprecated
    public ICommand getCommand() {
        return command;
    }

	@Deprecated
    public Map getPreviousAttributeValuesByName() {
        return previousAttributeValuesByName;
    }

	@Deprecated
    public boolean hasCategoryIdChanged() {
        return categoryIdChanged;
    }

	@Deprecated
    public boolean hasDefinedChanged() {
        return definedChanged;
    }

	@Deprecated
    public boolean hasDescriptionChanged() {
        return descriptionChanged;
    }

	@Deprecated
    public boolean hasHandledChanged() {
        return handledChanged;
    }

	@Deprecated
    public boolean hasNameChanged() {
        return nameChanged;
    }

	@Deprecated
    public boolean haveAttributeValuesByNameChanged() {
        return attributeValuesByNameChanged;
    }

	@Deprecated
    public boolean haveKeySequenceBindingsChanged() {
        return keySequenceBindingsChanged;
    }
}
