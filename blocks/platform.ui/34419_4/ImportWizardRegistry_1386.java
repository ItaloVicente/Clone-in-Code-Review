package org.eclipse.ui.internal.wizards;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class ExportWizardRegistry extends AbstractExtensionWizardRegistry {

	private static ExportWizardRegistry singleton;
	
	public static synchronized ExportWizardRegistry getInstance() {		
		if (singleton == null) {
			singleton = new ExportWizardRegistry();
		}
		return singleton;
	}
	
	public ExportWizardRegistry() {
		super();
	}

	@Override
	protected String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_EXPORT;
	}

	@Override
	protected String getPlugin() {
		return PlatformUI.PLUGIN_ID;
	}
}
