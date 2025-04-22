package org.eclipse.ui.internal.wizards;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class ImportWizardRegistry extends AbstractExtensionWizardRegistry {

	private static ImportWizardRegistry singleton;
	
	public static synchronized ImportWizardRegistry getInstance() {		
		if (singleton == null) {
			singleton = new ImportWizardRegistry();
		}
		return singleton;
	}
		
	
	public ImportWizardRegistry() {
		super();
	}

	@Override
	protected String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_IMPORT;
	}

	@Override
	protected String getPlugin() {
		return PlatformUI.PLUGIN_ID;
	}
}
