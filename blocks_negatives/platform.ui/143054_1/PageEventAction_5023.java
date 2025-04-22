public abstract class PageEventAction extends PartEventAction implements
        IPageListener, ActionFactory.IWorkbenchAction {
    /**
     * The active page, or <code>null</code> if none.
     */
    private IWorkbenchPage activePage;
