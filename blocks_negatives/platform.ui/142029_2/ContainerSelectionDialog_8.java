    ContainerSelectionGroup group;

    private IContainer initialSelection;

    private boolean allowNewContainerName = true;

    Label statusMessage;

    ISelectionValidator validator;

    private boolean showClosedProjects = true;

    /**
     * Creates a resource container selection dialog rooted at the given resource.
     * All selections are considered valid.
     *
     * @param parentShell the parent shell
     * @param initialRoot the initial selection in the tree
     * @param allowNewContainerName <code>true</code> to enable the user to type in
     *  a new container name, and <code>false</code> to restrict the user to just
     *  selecting from existing ones
     * @param message the message to be displayed at the top of this dialog, or
     *    <code>null</code> to display a default message
     */
    public ContainerSelectionDialog(Shell parentShell, IContainer initialRoot,
            boolean allowNewContainerName, String message) {
        super(parentShell);
        setTitle(IDEWorkbenchMessages.ContainerSelectionDialog_title);
        this.initialSelection = initialRoot;
        this.allowNewContainerName = allowNewContainerName;
        if (message != null) {
