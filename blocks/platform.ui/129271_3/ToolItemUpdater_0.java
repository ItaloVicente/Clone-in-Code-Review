	private final Set<AbstractContributionItem> itemsToUpdateLater = new LinkedHashSet<>();

	public ToolItemUpdater() {
		String delayProperty = System.getProperty("ToolItemUpdaterDelayInMs"); //$NON-NLS-1$
		if (delayProperty != null) {
			DELAY = Integer.parseInt(delayProperty);
		}
	}
