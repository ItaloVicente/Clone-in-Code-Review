package org.eclipse.ui.examples.views.properties.tabbed.article.views;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class SampleView
    extends ViewPart
    implements ITabbedPropertySheetPageContributor {

    private ListViewer viewer;

    private Group grp1;

    public SampleView() {
    }

    public void createPartControl(Composite parent) {
        viewer = new ListViewer(parent, SWT.SINGLE);

        grp1 = new Group(parent, SWT.NONE);
        grp1.setText("Preview");//$NON-NLS-1$
        RowLayout rowLayout = new RowLayout();
        grp1.setLayout(rowLayout);

        Button btn = new Button(grp1, SWT.PUSH);
        btn.setText("Hello");//$NON-NLS-1$

        ArrayList ctlList = new ArrayList();
        ButtonElement btnEl = new ButtonElement(btn, "Button");//$NON-NLS-1$
        ctlList.add(btnEl);

        viewer.setContentProvider(new ArrayContentProvider());
        viewer.setLabelProvider(new WorkbenchLabelProvider());
        viewer.setInput(ctlList);
        getSite().setSelectionProvider(viewer);

    }

    public void setFocus() {
        viewer.getControl().setFocus();
    }

    public Object getAdapter(Class adapter) {
        if (adapter == IPropertySheetPage.class)
            return new TabbedPropertySheetPage(this);
        return super.getAdapter(adapter);
    }

    public void dispose() {
        super.dispose();
    }

    public String getContributorId() {
        return getSite().getId();
    }

}
