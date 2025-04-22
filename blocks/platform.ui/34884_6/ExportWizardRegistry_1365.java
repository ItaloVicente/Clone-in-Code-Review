package org.eclipse.ui.internal.wizards;

import org.eclipse.ui.internal.dialogs.WizardCollectionElement;
import org.eclipse.ui.internal.dialogs.WorkbenchWizardElement;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.eclipse.ui.wizards.IWizardRegistry;

public abstract class AbstractWizardRegistry implements IWizardRegistry {

	private boolean initialized = false;

	private WorkbenchWizardElement[] primaryWizards;

	private WizardCollectionElement wizardElements;

	public AbstractWizardRegistry() {
		super();
	}
	
	public void dispose() {
		primaryWizards = null;
		wizardElements = null;
		initialized = false;
	}

	protected abstract void doInitialize();

	@Override
	public IWizardCategory findCategory(String id) {
		initialize();
		return wizardElements.findCategory(id);
	}

	@Override
	public IWizardDescriptor findWizard(String id) {
		initialize();
		return wizardElements.findWizard(id, true);
	}

	@Override
	public IWizardDescriptor[] getPrimaryWizards() {
		initialize();
		return primaryWizards;
	}

	@Override
	public IWizardCategory getRootCategory() {
		initialize();
		return wizardElements;
	}

	protected WizardCollectionElement getWizardElements() {
		initialize();
		return wizardElements;
	}

	protected final synchronized void initialize() {
		if (isInitialized()) {
			return;
		}

		initialized = true;
		doInitialize();
	}

	private boolean isInitialized() {
		return initialized;
	}

	protected void setPrimaryWizards(WorkbenchWizardElement[] primaryWizards) {
		this.primaryWizards = primaryWizards;
	}

	protected void setWizardElements(WizardCollectionElement wizardElements) {
		this.wizardElements = wizardElements;
	}
}
