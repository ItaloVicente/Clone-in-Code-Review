
package org.eclipse.ui.internal.navigator.resources.workbench;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class TabbedPropertySheetAdapterFactory
    implements IAdapterFactory {

    @Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (adaptableObject instanceof ProjectExplorer) {
        	if (IPropertySheetPage.class == adapterType)
                return new TabbedPropertySheetPage(
                    new TabbedPropertySheetProjectExplorerContributor(
                        (CommonNavigator) adaptableObject));
        }
        return null;
    }

    @Override
	public Class[] getAdapterList() {
        return new Class[] {IPropertySheetPage.class};
    }

}
