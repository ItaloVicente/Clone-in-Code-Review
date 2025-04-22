package org.eclipse.ui.commands;

import java.util.Map;
import org.eclipse.ui.internal.util.Util;

@Deprecated
@SuppressWarnings("all")
public final class HandlerEvent {

    private final boolean attributeValuesByNameChanged;

    private final IHandler handler;

    private Map previousAttributeValuesByName;

    private final Map originalPreviousAttributeValuesByName;
    
	@Deprecated
    public HandlerEvent(IHandler handler, boolean attributeValuesByNameChanged,
            Map previousAttributeValuesByName) {
        if (handler == null) {
			throw new NullPointerException();
		}

        if (!attributeValuesByNameChanged
                && previousAttributeValuesByName != null) {
			throw new IllegalArgumentException();
		}

        if (attributeValuesByNameChanged) {
            this.originalPreviousAttributeValuesByName = previousAttributeValuesByName;
        } else {
            this.originalPreviousAttributeValuesByName = null;
        }

        this.handler = handler;
        this.attributeValuesByNameChanged = attributeValuesByNameChanged;
    }

	@Deprecated
    public IHandler getHandler() {
        return handler;
    }

	@Deprecated
    public Map getPreviousAttributeValuesByName() {
        if (originalPreviousAttributeValuesByName == null) {
            return null;
        }
        
        if (previousAttributeValuesByName == null) {
            previousAttributeValuesByName = Util.safeCopy(
                    originalPreviousAttributeValuesByName, String.class, Object.class,
                    false, true);
        }
        
        return previousAttributeValuesByName;
    }

	@Deprecated
    public boolean haveAttributeValuesByNameChanged() {
        return attributeValuesByNameChanged;
    }
}
