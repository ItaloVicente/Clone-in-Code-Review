package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.preferences.WorkbenchPreferenceExtensionNode;
import org.eclipse.ui.statushandlers.StatusManager;

public class PropertyPageNode extends WorkbenchPreferenceExtensionNode {
    private RegistryPageContributor contributor;

	private IPreferencePage page;

    private Image icon;

    private Object element;

    public PropertyPageNode(RegistryPageContributor contributor,
            Object element) {
        super(contributor.getPageId(), contributor.getConfigurationElement());
        this.contributor = contributor;
        this.element = element;
    }

    @Override
	public void createPage() {
        try {
            page = contributor.createPage(element);
        } catch (CoreException e) {
            IStatus errStatus = StatusUtil.newStatus(e.getStatus(), WorkbenchMessages.PropertyPageNode_errorMessage); 
            StatusManager.getManager().handle(errStatus, StatusManager.SHOW);
            page = new EmptyPropertyPage();
        }
        setPage(page);
    }

    @Override
	public void disposeResources() {

        if (page != null) {
            page.dispose();
            page = null;
        }
        if (icon != null) {
            icon.dispose();
            icon = null;
        }
    }

    @Override
	public Image getLabelImage() {
        if (icon == null) {
            ImageDescriptor desc = contributor.getPageIcon();
            if (desc != null) {
                icon = desc.createImage();
            }
        }
        return icon;
    }

    @Override
	public String getLabelText() {
        return contributor.getPageName();
    }

}
