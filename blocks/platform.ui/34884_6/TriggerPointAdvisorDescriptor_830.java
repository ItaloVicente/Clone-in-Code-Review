package org.eclipse.ui.internal.activities.ws;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.internal.activities.Persistence;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class RegistryTriggerPoint extends AbstractTriggerPoint {

    private String id;

    private IConfigurationElement element;

    private Map hints;

    public RegistryTriggerPoint(String id, IConfigurationElement element) {
        this.id = id;
        this.element = element;
    }

    @Override
	public String getId() {
        return id;
    }

    @Override
	public String getStringHint(String key) {
        return (String) getHints().get(key);
    }

    @Override
	public boolean getBooleanHint(String key) {
        return Boolean.valueOf(getStringHint(key)).booleanValue();
    }

    private Map getHints() {
        if (hints == null) {
            hints = new HashMap();

            IConfigurationElement[] hintElements = element
                    .getChildren(IWorkbenchRegistryConstants.TAG_HINT);
            for (int i = 0; i < hintElements.length; i++) {
                String id = hintElements[i]
                        .getAttribute(IWorkbenchRegistryConstants.ATT_ID);
                String value = hintElements[i]
                        .getAttribute(IWorkbenchRegistryConstants.ATT_VALUE);

                if (id == null || value == null) {
					Persistence.log(element, Persistence.ACTIVITY_TRIGGER_HINT_DESC, "hint must contain ID and value"); //$NON-NLS-1$
					continue;
				}
				hints.put(id, value);
            }
        }

        return hints;
    }
}
