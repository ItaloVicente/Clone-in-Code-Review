package org.eclipse.ui.internal.wizards;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.dialogs.WizardCollectionElement;
import org.eclipse.ui.internal.dialogs.WorkbenchWizardElement;
import org.eclipse.ui.internal.registry.WizardsRegistryReader;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.wizards.IWizardDescriptor;

public abstract class AbstractExtensionWizardRegistry extends
		AbstractWizardRegistry implements IExtensionChangeHandler{

	public AbstractExtensionWizardRegistry() {
		super();
	}

	@Override
	public void addExtension(IExtensionTracker tracker, IExtension extension) {
		WizardsRegistryReader reader = new WizardsRegistryReader(getPlugin(),
				getExtensionPoint());
		reader.setInitialCollection(getWizardElements());
		IConfigurationElement[] configurationElements = extension
				.getConfigurationElements();
		for (int i = 0; i < configurationElements.length; i++) {
			reader.readElement(configurationElements[i]);
		}
		setWizardElements(reader.getWizardElements());
		registerWizards(getWizardElements());

		WorkbenchWizardElement[] additionalPrimary = reader.getPrimaryWizards();
		if (additionalPrimary.length == 0) {
			return;
		}
		IWizardDescriptor[] localPrimaryWizards = getPrimaryWizards();
		WorkbenchWizardElement[] newPrimary = new WorkbenchWizardElement[additionalPrimary.length
				+ localPrimaryWizards.length];
		System.arraycopy(localPrimaryWizards, 0, newPrimary, 0,
				localPrimaryWizards.length);
		System.arraycopy(additionalPrimary, 0, newPrimary,
				localPrimaryWizards.length, additionalPrimary.length);
		setPrimaryWizards(newPrimary);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		PlatformUI.getWorkbench().getExtensionTracker()
				.unregisterHandler(this);
	}

	@Override
	protected void doInitialize() {
        
		PlatformUI.getWorkbench().getExtensionTracker().registerHandler(this, ExtensionTracker.createExtensionPointFilter(getExtensionPointFilter()));

		WizardsRegistryReader reader = new WizardsRegistryReader(getPlugin(),
				getExtensionPoint());
		setWizardElements(reader.getWizardElements());
		setPrimaryWizards(reader.getPrimaryWizards());
		registerWizards(getWizardElements());
	}

	protected abstract String getExtensionPoint();

	private IExtensionPoint getExtensionPointFilter() {
		return Platform.getExtensionRegistry().getExtensionPoint(getPlugin(),
				getExtensionPoint());
	}

	protected abstract String getPlugin();

	private void register(IExtension extension, Object object) {
		PlatformUI.getWorkbench().getExtensionTracker().registerObject(
				extension, object, IExtensionTracker.REF_WEAK);
	}

	private void registerWizards(WizardCollectionElement collection) {
		registerWizards(collection.getWorkbenchWizardElements());

		WizardCollectionElement[] collections = collection
				.getCollectionElements();
		for (int i = 0; i < collections.length; i++) {
			IConfigurationElement configurationElement = collections[i]
					.getConfigurationElement();
			if (configurationElement != null) {
				register(configurationElement.getDeclaringExtension(),
						collections[i]);
			}
			registerWizards(collections[i]);
		}
	}

	private void registerWizards(WorkbenchWizardElement[] wizards) {
		for (int i = 0; i < wizards.length; i++) {
			register(wizards[i].getConfigurationElement()
					.getDeclaringExtension(), wizards[i]);
		}
	}

	@Override
	public void removeExtension(IExtension extension, Object[] objects) {
		if (!extension.getExtensionPointUniqueIdentifier().equals(
				getExtensionPointFilter().getUniqueIdentifier())) {
			return;
		}
		for (int i = 0; i < objects.length; i++) {
			Object object = objects[i];
			if (object instanceof WizardCollectionElement) {
				WizardCollectionElement collection = (WizardCollectionElement) object;
				collection.getParentCollection().remove(collection);
			} else if (object instanceof WorkbenchWizardElement) {
				WorkbenchWizardElement wizard = (WorkbenchWizardElement) object;
				WizardCollectionElement parent = wizard.getCollectionElement();
				if (parent != null) {
					parent.remove(wizard);
				}
				IWizardDescriptor[] primaryWizards = getPrimaryWizards();
				for (int j = 0; j < primaryWizards.length; j++) {
					if (primaryWizards[j] == wizard) {
						WorkbenchWizardElement[] newPrimary = new WorkbenchWizardElement[primaryWizards.length - 1];
						Util
								.arrayCopyWithRemoval(primaryWizards,
										newPrimary, j);
						primaryWizards = newPrimary;
						break;
					}
				}
				setPrimaryWizards((WorkbenchWizardElement[]) primaryWizards);
			}
		}
	}
}
