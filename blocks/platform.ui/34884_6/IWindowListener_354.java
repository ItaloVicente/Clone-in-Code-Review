package org.eclipse.ui;

public interface IViewSite extends IWorkbenchPartSite {

    public IActionBars getActionBars();

    public String getSecondaryId();
}
