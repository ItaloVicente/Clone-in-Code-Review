	private String text;

	private int[][] boldRanges;

	private int[][] helpRanges;

	private String[] helpIds;

	private String[] helpHrefs;

	private int[][] actionRanges;

	private String[] actionPluginIds;

	private String[] actionClasses;

	public WelcomeItem(String text, int[][] boldRanges, int[][] actionRanges,
			String[] actionPluginIds, String[] actionClasses,
			int[][] helpRanges, String[] helpIds, String[] helpHrefs) {

		this.text = text;
		this.boldRanges = boldRanges;
		this.actionRanges = actionRanges;
		this.actionPluginIds = actionPluginIds;
		this.actionClasses = actionClasses;
		this.helpRanges = helpRanges;
		this.helpIds = helpIds;
		this.helpHrefs = helpHrefs;
	}

	public int[][] getActionRanges() {
		return actionRanges;
	}

	public int[][] getBoldRanges() {
		return boldRanges;
	}

	public int[][] getHelpRanges() {
		return helpRanges;
	}

	public String getText() {
		return text;
	}

	public boolean isLinkAt(int offset) {
		for (int[] helpRange : helpRanges) {
			if (offset >= helpRange[0]
					&& offset < helpRange[0] + helpRange[1]) {
				return true;
			}
		}

		for (int[] actionRange : actionRanges) {
			if (offset >= actionRange[0]
					&& offset < actionRange[0] + actionRange[1]) {
				return true;
			}
		}
		return false;
	}

	public void logActionLinkError(String actionPluginId, String actionClass) {
		IDEWorkbenchPlugin
				.log(IDEWorkbenchMessages.WelcomeItem_unableToLoadClass + actionPluginId + " " + actionClass); //$NON-NLS-1$
	}

	private void openHelpTopic(String topic, String href) {
		if (href != null) {
