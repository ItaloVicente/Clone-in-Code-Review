		IStatusLineManager {
	private String message;

	private String errorMessage;

	private Image messageImage;

	private Image errorImage;

	public SubStatusLineManager(IStatusLineManager mgr) {
		super(mgr);
	}

	protected final IStatusLineManager getParentStatusLineManager() {
		return (IStatusLineManager) getParent();
	}

	@Override
