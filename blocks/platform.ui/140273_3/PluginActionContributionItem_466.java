		implements IIdentifierListener, IActivityManagerListener {

	private IIdentifier identifier = null;

	public PluginActionContributionItem(PluginAction action) {
		super(action);
	}

	private void hookListeners() {
		PlatformUI.getWorkbench().getActivitySupport().getActivityManager().addActivityManagerListener(this);
		IIdentifier id = getIdentifier();
		if (id != null) {
