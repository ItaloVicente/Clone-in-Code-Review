    private IWorkbenchPage page;

    NavigationHistoryEditorInfo editorInfo;

    String historyText;

    /* Both may be set at the same time. */
    INavigationLocation location;

    private IMemento locationMemento;

    /**
     * Constructs a new HistoryEntry and intializes its editor input and editor id.
     */
    public NavigationHistoryEntry(NavigationHistoryEditorInfo editorInfo,
            IWorkbenchPage page, IEditorPart part, INavigationLocation location) {
        this.editorInfo = editorInfo;
        this.page = page;
        this.location = location;
        if (location != null) {
            historyText = location.getText();
        }
        if (historyText == null || historyText.length() == 0) {
            if (part != null) {
