    private IWorkbenchWindow window;

    private EditorHistory history;

    private boolean showSeparator;

    private static final int MAX_TEXT_LENGTH = 40;

    private static final int MAX_MNEMONIC_SIZE = 9;

    /**
     * Create a new instance.
     * @param window the window on which the menu is to be created
     * @param id menu's id
     * @param showSeparator whether or not to show a separator
     */
    public ReopenEditorMenu(IWorkbenchWindow window, String id,
            boolean showSeparator) {
        super(id);
        this.window = window;
        this.showSeparator = showSeparator;
        IWorkbench workbench = window.getWorkbench();
