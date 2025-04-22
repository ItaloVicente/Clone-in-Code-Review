	private final IWorkbenchWindow window;

	MarkAsReadAction(String text, IWorkbenchWindow window) {
		super(text);
		this.window = window;
		setId(ICommandIds.CMD_MARK_AS_READ);
		setActionDefinitionId(ICommandIds.CMD_MARK_AS_READ);
		setImageDescriptor(org.eclipse.e4.ui.examples.css.rcp.Activator.getImageDescriptor("/icons/sample3.gif"));
	}

	@Override
	public void run() {

