package org.eclipse.ui.wizards;

import org.eclipse.core.runtime.IPath;

public interface IWizardCategory {

	IWizardCategory findCategory(IPath path);

	IWizardDescriptor findWizard(String id);

	IWizardCategory[] getCategories();

	String getId();

	String getLabel();

	IWizardCategory getParent();

	IPath getPath();

	IWizardDescriptor[] getWizards();
}
