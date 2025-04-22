package org.eclipse.ui.wizards;


public interface IWizardRegistry {

	IWizardDescriptor findWizard(String id);
	
	IWizardDescriptor [] getPrimaryWizards();

	IWizardCategory findCategory(String id);
	
	IWizardCategory getRootCategory();
}
