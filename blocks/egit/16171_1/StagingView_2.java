	private Button rebaseContinueButton;

	private Button rebaseSkipButton;

	private Button rebaseAbortButton;

	protected ListenerHandle refsChangedListener;

	private Label rebaseLabel;

	private Composite buttonsContainer;

	private LocalResourceManager resources = new LocalResourceManager(
			JFaceResources.getResources());

	private Image getImage(ImageDescriptor descriptor) {
		return (Image) this.resources.get(descriptor);
	}

