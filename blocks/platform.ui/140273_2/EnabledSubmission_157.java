	private final String activePartId;

	private final Shell activeShell;

	private final IWorkbenchPartSite activeWorkbenchPartSite;

	private final String contextId;

	private transient String string = null;

	public EnabledSubmission(String activePartId, Shell activeShell, IWorkbenchPartSite activeWorkbenchPartSite,
			String contextId) {
		if (contextId == null) {
