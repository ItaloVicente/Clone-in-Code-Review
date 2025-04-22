package org.eclipse.ui.part;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;

public abstract class Page implements IPageBookViewPage {
    private IPageSite site;

    protected Page() {
    }

    @Override
	public abstract void createControl(Composite parent);

    @Override
	public void dispose() {
        Control ctrl = getControl();
        if (ctrl != null && !ctrl.isDisposed()) {
			ctrl.dispose();
		}
    }

    @Override
	public abstract Control getControl();

    public void makeContributions(IMenuManager menuManager,
            IToolBarManager toolBarManager, IStatusLineManager statusLineManager) {
    }

    @Override
	public void setActionBars(IActionBars actionBars) {
        makeContributions(actionBars.getMenuManager(), actionBars
                .getToolBarManager(), actionBars.getStatusLineManager());
    }

    @Override
	public void init(IPageSite pageSite) {
        site = pageSite;
    }

    @Override
	public IPageSite getSite() {
        return site;
    }

    @Override
	public abstract void setFocus();
}
