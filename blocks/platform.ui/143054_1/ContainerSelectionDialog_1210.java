	ContainerSelectionGroup group;

	private IContainer initialSelection;

	private boolean allowNewContainerName = true;

	Label statusMessage;

	ISelectionValidator validator;

	private boolean showClosedProjects = true;

	public ContainerSelectionDialog(Shell parentShell, IContainer initialRoot, boolean allowNewContainerName,
			String message) {
		super(parentShell);
		setTitle(IDEWorkbenchMessages.ContainerSelectionDialog_title);
		this.initialSelection = initialRoot;
		this.allowNewContainerName = allowNewContainerName;
		if (message != null) {
