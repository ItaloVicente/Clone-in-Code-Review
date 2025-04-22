package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.preferences.WorkbenchPreferenceExtensionNode;
import org.eclipse.ui.internal.registry.CategorizedPageRegistryReader;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.statushandlers.StatusManager;

public class WorkbenchPreferenceNode extends WorkbenchPreferenceExtensionNode {

	public WorkbenchPreferenceNode(String nodeId, IConfigurationElement element) {
		super(nodeId, element);
	}

	@Override
	public void createPage() {
		IWorkbenchPreferencePage page;
		try {
			page = (IWorkbenchPreferencePage) WorkbenchPlugin.createExtension(
					getConfigurationElement(), IWorkbenchRegistryConstants.ATT_CLASS);
		} catch (CoreException e) {
			IStatus errStatus = StatusUtil.newStatus(e.getStatus(), WorkbenchMessages.PreferenceNode_errorMessage); 
			StatusManager.getManager().handle(errStatus, StatusManager.SHOW | StatusManager.LOG);
			page = new ErrorPreferencePage();
		}

		page.init(PlatformUI.getWorkbench());
		if (getLabelImage() != null) {
			page.setImageDescriptor(getImageDescriptor());
		}
		page.setTitle(getLabelText());
		setPage(page);
	}

	public String getCategory() {
		return getConfigurationElement().getAttribute(
				CategorizedPageRegistryReader.ATT_CATEGORY);
	}	
}
