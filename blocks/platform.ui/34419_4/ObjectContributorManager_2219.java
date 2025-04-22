package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.RegistryReader;

public class ObjectActionContributorReader extends RegistryReader {

    private ObjectActionContributorManager manager;

    protected void processObjectContribution(IConfigurationElement element) {
        String objectClassName = element.getAttribute(IWorkbenchRegistryConstants.ATT_OBJECTCLASS);
        if (objectClassName == null) {
            logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_OBJECTCLASS);
            return;
        }

        IObjectContributor contributor = new ObjectActionContributor(element);
        manager.registerContributor(contributor, objectClassName);
    }

    @Override
	protected boolean readElement(IConfigurationElement element) {
        String tagName = element.getName();
        if (tagName.equals(IWorkbenchRegistryConstants.TAG_OBJECT_CONTRIBUTION)) {
            processObjectContribution(element);
            return true;
        }
        if (tagName.equals(IWorkbenchRegistryConstants.TAG_VIEWER_CONTRIBUTION)) {
            return true;
        }

        return false;
    }

    public void readPopupContributors(ObjectActionContributorManager mng) {
        setManager(mng);
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        readRegistry(registry, PlatformUI.PLUGIN_ID,
                IWorkbenchRegistryConstants.PL_POPUP_MENU);
    }

    public void setManager(ObjectActionContributorManager mng) {
        manager = mng;
    }
}
