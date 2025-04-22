package org.eclipse.ui.internal.registry;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.IParameterValues;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.eclipse.ui.wizards.IWizardRegistry;

public abstract class WizardParameterValues implements IParameterValues {

	public static final class Export extends WizardParameterValues {
		@Override
		protected IWizardRegistry getWizardRegistry() {
			return PlatformUI.getWorkbench().getExportWizardRegistry();
		}
	}

	public static final class Import extends WizardParameterValues {
		@Override
		protected IWizardRegistry getWizardRegistry() {
			return PlatformUI.getWorkbench().getImportWizardRegistry();
		}
	}

	public static final class New extends WizardParameterValues {
		@Override
		protected IWizardRegistry getWizardRegistry() {
			return PlatformUI.getWorkbench().getNewWizardRegistry();
		}
	}

	private void addParameterValues(Map values, IWizardCategory wizardCategory) {

		final IWizardDescriptor[] wizardDescriptors = wizardCategory
				.getWizards();
		for (int i = 0; i < wizardDescriptors.length; i++) {
			final IWizardDescriptor wizardDescriptor = wizardDescriptors[i];

			
			String name = wizardDescriptor.getLabel();
			final String id = wizardDescriptor.getId();
			final String value = (String) values.get(name);
			if (value!=null && !value.equals(id)) {
				name = name + " (" + id + ")"; //$NON-NLS-1$//$NON-NLS-2$
			}
			values.put(name, id);
		}

		final IWizardCategory[] childCategories = wizardCategory
				.getCategories();
		for (int i = 0; i < childCategories.length; i++) {
			final IWizardCategory childCategory = childCategories[i];
			addParameterValues(values, childCategory);
		}
	}

	@Override
	public Map getParameterValues() {
		final Map values = new HashMap();

		final IWizardRegistry wizardRegistry = getWizardRegistry();
		addParameterValues(values, wizardRegistry.getRootCategory());

		return values;
	}

	protected abstract IWizardRegistry getWizardRegistry();

}
