package org.eclipse.ui.internal;

import org.eclipse.ui.IWorkbenchPage;

class PageSelectionService extends AbstractSelectionService {

    private IWorkbenchPage page;

    public PageSelectionService(IWorkbenchPage page) {
        setPage(page);
    }

    private void setPage(IWorkbenchPage page) {
        this.page = page;
    }

    protected IWorkbenchPage getPage() {
        return page;
    }

    @Override
	protected AbstractPartSelectionTracker createPartTracker(String partId) {
        return new PagePartSelectionTracker(getPage(), partId);
    }

}
