	private boolean hasRun = false;

	protected MockAction(String text) {
		super(text);
		TestPlugin plugin = TestPlugin.getDefault();
		setImageDescriptor(plugin.getImageDescriptor("anything.gif"));
		setToolTipText(text);
	}

	protected MockAction(String text, ImageDescriptor image) {
		super(text, image);
		setToolTipText(text);
	}

	@Override
