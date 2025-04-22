package org.eclipse.ui.part;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IActionBars2;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

public class EditorActionBarContributor implements IEditorActionBarContributor {
    private IActionBars bars;

    private IWorkbenchPage page;

    public EditorActionBarContributor() {
    }

    public void contributeToMenu(IMenuManager menuManager) {
    }

    public void contributeToStatusLine(IStatusLineManager statusLineManager) {
    }

    public void contributeToToolBar(IToolBarManager toolBarManager) {
    }

    public void contributeToCoolBar(ICoolBarManager coolBarManager) {
    }

    public IActionBars getActionBars() {
        return bars;
    }

    public IWorkbenchPage getPage() {
        return page;
    }

    @Override
	public void dispose() {
    }

    @Override
	public void init(IActionBars bars, IWorkbenchPage page) {
        this.page = page;
        init(bars);
    }

    public void init(IActionBars bars) {
        this.bars = bars;
        contributeToMenu(bars.getMenuManager());
        contributeToToolBar(bars.getToolBarManager());
        if (bars instanceof IActionBars2) {
            contributeToCoolBar(((IActionBars2) bars).getCoolBarManager());
        }
        contributeToStatusLine(bars.getStatusLineManager());

    }

    @Override
	public void setActiveEditor(IEditorPart targetEditor) {
    }
}
