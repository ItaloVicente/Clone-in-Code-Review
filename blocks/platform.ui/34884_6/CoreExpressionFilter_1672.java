
package org.eclipse.ui.internal.navigator.filters;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.internal.navigator.CommonNavigatorMessages;
import org.eclipse.ui.navigator.INavigatorContentDescriptor;
import org.eclipse.ui.navigator.INavigatorContentService;

public class ContentExtensionsTab extends CustomizationTab {

	protected ContentExtensionsTab(Composite parent,
			INavigatorContentService aContentService) {
		super(parent, aContentService);
		createControl();

	}

	private void createControl() { 
				
		createInstructionsLabel(CommonNavigatorMessages.CommonFilterSelectionDialog_Select_the_available_extensions);
		
		createTable();

		getTableViewer().setContentProvider(new ContentDescriptorContentProvider());
		getTableViewer().setLabelProvider(new CommonFilterLabelProvider());
		getTableViewer().setInput(getContentService());

		updateCheckedState();

	}


	private void updateCheckedState() {
		INavigatorContentDescriptor[] visibleExtensions = getContentService()
				.getVisibleExtensions();
		for (int i = 0; i < visibleExtensions.length; i++) {
			if (getContentService().isActive(visibleExtensions[i].getId())) {
				getTableViewer().setChecked(visibleExtensions[i], true);
			}
		}

	}

}
