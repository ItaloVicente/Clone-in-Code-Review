package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.editor.HockeyleagueEditor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class HockeyleaguePropertySheetPage
	extends TabbedPropertySheetPage {

	protected HockeyleagueEditor editor;

	public HockeyleaguePropertySheetPage(HockeyleagueEditor editor) {
		super(editor);
		this.editor = editor;
	}

	public HockeyleagueEditor getEditor() {
		return editor;
	}

	public AdapterFactory getAdapterFactory() {
		return editor.getAdapterFactory();
	}
}
