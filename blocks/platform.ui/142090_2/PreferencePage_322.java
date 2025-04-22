	private Button defaultsButton = null;

	private IPreferencePageContainer container = null;

	private Button applyButton = null;

	private Label descriptionLabel;

	private Point size = null;


	protected PreferencePage() {
		this(""); //$NON-NLS-1$
	}

	protected PreferencePage(String title) {
		super(title);
	}

	protected PreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
