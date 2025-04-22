public class PagePartSelectionTracker extends AbstractPartSelectionTracker
        implements IPartListener, IPerspectiveListener2, ISelectionChangedListener {

    /**
     * The workbench page for which this is tracking selection.
     */
    private IWorkbenchPage fPage;

    /**
     * The part in this tracker's page, or <code>null</code> if one is not open.
     */
    private IWorkbenchPart fPart;

    private ISelectionChangedListener selectionListener = event -> fireSelection(getPart(), event.getSelection());

    private ISelectionChangedListener postSelectionListener = event -> firePostSelection(getPart(), event.getSelection());

    public PagePartSelectionTracker(IWorkbenchPage page, String partId) {
        super(partId);
        setPage(page);
        page.addPartListener(this);
        page.getWorkbenchWindow().addPerspectiveListener(this);
        String secondaryId = null;
        int indexOfColon;
        if ((indexOfColon = partId.indexOf(':')) != -1) {
        	secondaryId = partId.substring(indexOfColon + 1);
        	partId = partId.substring(0, indexOfColon);
        }
		IViewReference part = page.findViewReference(partId, secondaryId);
        if (part != null && part.getView(false) != null) {
            setPart(part.getView(false), false);
        }
    }

    /**
     * Disposes this selection provider - removes all listeners
     * currently registered.
     */
    @Override
	public void dispose() {
    	IWorkbenchPage page = getPage();
    	page.getWorkbenchWindow().removePerspectiveListener(this);
    	page.removePartListener(this);
        setPart(null, false);
        setPage(null);
        super.dispose();
    }

    /*
     * @see IPartListener#partActivated(IWorkbenchPart)
     */
    @Override
	public void partActivated(IWorkbenchPart part) {
    }

    /*
     * @see IPartListener#partBroughtToTop(IWorkbenchPart)
     */
    @Override
	public void partBroughtToTop(IWorkbenchPart part) {
    }

    /**
     * @see IPartListener#partClosed(IWorkbenchPart)
     */
    @Override
	public void partClosed(IWorkbenchPart part) {
        if (getPartId(part).equals(getPartId())) {
            setPart(null, true);
        }
    }

    /*
     * @see IPartListener#partDeactivated(IWorkbenchPart)
     */
    @Override
	public void partDeactivated(IWorkbenchPart part) {
    }

    /**
     * @see IPartListener#partOpened(IWorkbenchPart)
     */
    @Override
	public void partOpened(IWorkbenchPart part) {
        if (getPartId(part).equals(getPartId())) {
            setPart(part, true);
        }
    }

    /**
     * Returns the id for the given part, taking into account
     * multi-view instances which may have a secondary id.
     *
     * @since 3.0
     */
