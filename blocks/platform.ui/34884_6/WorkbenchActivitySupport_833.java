package org.eclipse.ui.internal.activities.ws;

import java.util.HashMap;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ITriggerPoint;
import org.eclipse.ui.activities.ITriggerPointManager;
import org.eclipse.ui.internal.activities.Persistence;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class TriggerPointManager implements ITriggerPointManager, IExtensionChangeHandler {

    private HashMap triggerMap = new HashMap();

    public TriggerPointManager() {
        super();
        triggerMap.put(ITriggerPointManager.UNKNOWN_TRIGGER_POINT_ID,
                new AbstractTriggerPoint() {

                    @Override
					public String getId() {
                        return ITriggerPointManager.UNKNOWN_TRIGGER_POINT_ID;
                    }

                    @Override
					public String getStringHint(String key) {
                        if (ITriggerPoint.HINT_INTERACTIVE.equals(key)) {
                            return Boolean.TRUE.toString();
                        }
                        return null;
                    }

                    @Override
					public boolean getBooleanHint(String key) {
                        if (ITriggerPoint.HINT_INTERACTIVE.equals(key)) {
                            return true;
                        }
                        return false;
                    }
                });
        IExtensionTracker tracker = PlatformUI.getWorkbench().getExtensionTracker();
        tracker.registerHandler(this, ExtensionTracker.createExtensionPointFilter(getExtensionPointFilter()));

        IExtensionPoint point = getExtensionPointFilter();
        IExtension[] extensions = point.getExtensions();
        for (int i = 0; i < extensions.length; i++) {
            addExtension(tracker,
                    extensions[i]);
        }
    }

    @Override
	public ITriggerPoint getTriggerPoint(String id) {
        return (ITriggerPoint) triggerMap.get(id);
    }

    @Override
	public Set getDefinedTriggerPointIds() {
        return triggerMap.entrySet();
    }

    @Override
	public void removeExtension(IExtension extension, Object[] objects) {
        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            if (object instanceof RegistryTriggerPoint) {
                triggerMap.remove(((RegistryTriggerPoint) object).getId());
            }
        }
    }

    @Override
	public void addExtension(IExtensionTracker tracker, IExtension extension) {
        IConfigurationElement[] elements = extension.getConfigurationElements();
        for (int i = 0; i < elements.length; i++) {
            IConfigurationElement element = elements[i];
            if (element.getName().equals(
                    IWorkbenchRegistryConstants.TAG_TRIGGERPOINT)) {
                String id = element
                        .getAttribute(IWorkbenchRegistryConstants.ATT_ID);
                if (id == null) {
					Persistence.log(element, Persistence.ACTIVITY_TRIGGER_DESC, "missing a unique identifier"); //$NON-NLS-1$
					continue;
				}
                RegistryTriggerPoint triggerPoint = new RegistryTriggerPoint(
                        id, element);
                triggerMap.put(id, triggerPoint);
                tracker.registerObject(extension, triggerPoint,
                        IExtensionTracker.REF_WEAK);
            }
        }
    }

    private IExtensionPoint getExtensionPointFilter() {
        return Platform.getExtensionRegistry().getExtensionPoint(
                PlatformUI.PLUGIN_ID, IWorkbenchRegistryConstants.PL_ACTIVITYSUPPORT);
    }
}
