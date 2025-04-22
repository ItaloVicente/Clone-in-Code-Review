package org.eclipse.ui.examples.views.properties.tabbed.logic;

import org.eclipse.gef.examples.logicdesigner.LogicEditor;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class TabbedPropertiesLogicEditor
    extends LogicEditor
    implements ITabbedPropertySheetPageContributor {

    private TabbedPropertySheetPage tabbedPropertySheetPage;

    protected void initializeGraphicalViewer() {
        super.initializeGraphicalViewer();
        this.tabbedPropertySheetPage = new TabbedPropertySheetPage(this);
    }

    public String getContributorId() {
        return getSite().getId();
    }

    public Object getAdapter(Class type) {
        if (type == IPropertySheetPage.class)
            return tabbedPropertySheetPage;
        return super.getAdapter(type);
    }
}
