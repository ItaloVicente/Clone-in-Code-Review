package org.eclipse.ui.part;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;

public class DrillDownComposite extends Composite {
    private ToolBarManager toolBarMgr;

    private TreeViewer fChildTree;

    private DrillDownAdapter adapter;

    public DrillDownComposite(Composite parent, int style) {
        super(parent, style);
        createNavigationButtons();
    }

    protected void createNavigationButtons() {
        GridData gid;
        GridLayout layout;

        layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = layout.horizontalSpacing = layout.verticalSpacing = 0;
        setLayout(layout);

        toolBarMgr = new ToolBarManager(SWT.FLAT);
        ToolBar toolBar = toolBarMgr.createControl(this);
        gid = new GridData();
        gid.horizontalAlignment = GridData.FILL;
        gid.verticalAlignment = GridData.BEGINNING;
        toolBar.setLayoutData(gid);
    }

    public void setChildTree(TreeViewer aViewer) {
        fChildTree = aViewer;

        adapter = new DrillDownAdapter(fChildTree);
        adapter.addNavigationActions(toolBarMgr);
        toolBarMgr.update(true);

        fChildTree.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        layout();
    }
}
