
package org.eclipse.ui.internal.quickaccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardDescriptor;

public class WizardProvider extends QuickAccessProvider {

	private QuickAccessElement[] cachedElements;
	private Map<String, WizardElement> idToElement = new HashMap<String, WizardElement>();

	@Override
	public QuickAccessElement getElementForId(String id) {
		getElements();
		return idToElement.get(id);
	}

	@Override
	public QuickAccessElement[] getElements() {
		if (cachedElements == null) {
			IWizardCategory rootCategory = WorkbenchPlugin.getDefault()
					.getNewWizardRegistry().getRootCategory();
			List<IWizardDescriptor> result = new ArrayList<IWizardDescriptor>();
			collectWizards(rootCategory, result);
			IWizardDescriptor[] wizards = result
					.toArray(new IWizardDescriptor[result.size()]);
			for (int i = 0; i < wizards.length; i++) {
				if (!WorkbenchActivityHelper.filterItem(wizards[i])) {
					WizardElement wizardElement = new WizardElement(wizards[i], this);
					idToElement.put(wizardElement.getId(), wizardElement);
				}
			}
			cachedElements = idToElement.values().toArray(
					new QuickAccessElement[idToElement.size()]);
		}
		return cachedElements;
	}

	private void collectWizards(IWizardCategory category, List<IWizardDescriptor> result) {
		result.addAll(Arrays.asList(category.getWizards()));
		IWizardCategory[] childCategories = category.getCategories();
		for (int i = 0; i < childCategories.length; i++) {
			collectWizards(childCategories[i], result);
		}
	}

	@Override
	public String getId() {
		return "org.eclipse.ui.wizards"; //$NON-NLS-1$
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return WorkbenchImages
				.getImageDescriptor(IWorkbenchGraphicConstants.IMG_OBJ_NODE);
	}

	@Override
	public String getName() {
		return QuickAccessMessages.QuickAccess_New;
	}

	@Override
	protected void doReset() {
		cachedElements = null;
		idToElement.clear();
	}
}
