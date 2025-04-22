package org.eclipse.ui.part;

import org.eclipse.ui.PartInitException;

public interface IPageBookViewPage extends IPage {
    public IPageSite getSite();

    public void init(IPageSite site) throws PartInitException;
}
