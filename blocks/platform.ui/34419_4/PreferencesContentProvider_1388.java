package org.eclipse.ui.internal.wizards;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public final class NewWizardRegistry extends AbstractExtensionWizardRegistry {

	private static NewWizardRegistry singleton;
	
	public static synchronized NewWizardRegistry getInstance() {		
		if (singleton == null) {
			singleton = new NewWizardRegistry();
		}
		return singleton;
	}
		
	private NewWizardRegistry() {
		super();
	}

	@Override
	protected String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_NEW;
	}

	@Override
	protected String getPlugin() {
		return PlatformUI.PLUGIN_ID;
	}
}
