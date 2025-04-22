package org.eclipse.ui.examples.readmetool;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;

public class ReadmeSectionsView extends ViewPart implements ISelectionListener {
    ListViewer viewer;

    public ReadmeSectionsView() {
        super();
    }

    public void createPartControl(Composite parent) {
        viewer = new ListViewer(parent);

        PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(),
				IReadmeConstants.SECTIONS_VIEW_CONTEXT);

        viewer.setContentProvider(new WorkbenchContentProvider());
        viewer.setLabelProvider(new WorkbenchLabelProvider());

        getSite().getPage().addSelectionListener(this);

        selectionChanged(null, getSite().getPage().getSelection());
    }

    public void dispose() {
        super.dispose();
        getSite().getPage().removeSelectionListener(this);
    }

    public void selectionChanged(IWorkbenchPart part, ISelection sel) {
        AdaptableList input = ReadmeModelFactory.getInstance().getSections(sel);
        viewer.setInput(input);
    }

    public void setFocus() {
        viewer.getControl().setFocus();
    }
}
