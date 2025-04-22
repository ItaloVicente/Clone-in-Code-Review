package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.HockeyleaguePropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AdvancedPropertySection
	extends org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection {

	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		HockeyleaguePropertySheetPage hockeyleaguePropertySheetPage = (HockeyleaguePropertySheetPage) tabbedPropertySheetPage;
		page.setPropertySourceProvider(new AdapterFactoryContentProvider(
			hockeyleaguePropertySheetPage.getAdapterFactory()));
	}
}
