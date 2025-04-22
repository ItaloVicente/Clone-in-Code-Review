
package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.viewers.IBasicPropertyConstants;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.ui.internal.registry.WizardsRegistryReader;

class DataTransferWizardCollectionComparator extends ViewerComparator {
    public final static DataTransferWizardCollectionComparator INSTANCE = new DataTransferWizardCollectionComparator();

    private DataTransferWizardCollectionComparator() {
        super();
    }

    @Override
	public int category(Object element) {
		if (element instanceof WizardCollectionElement){
			String id = ((WizardCollectionElement)element).getId();
    		if (WizardsRegistryReader.GENERAL_WIZARD_CATEGORY.equals(id)) {
				return 1;
			}
    		if (WizardsRegistryReader.UNCATEGORIZED_WIZARD_CATEGORY.equals(id)) {
				return 3;
			}
    		return 2;
		}
		return super.category(element);
	}

    @Override
	public boolean isSorterProperty(Object object, String propertyId) {
        return propertyId.equals(IBasicPropertyConstants.P_TEXT);
    }
}
