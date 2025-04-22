	protected IDialogSettings getDialogSettings() {
		return GerritDialogSettings
				.getSection(GerritDialogSettings.FETCH_FROM_GERRIT_SECTION);
	}

	@Override
	public void createControl(Composite parent) {
		parent.addDisposeListener(event -> {
			for (ChangeList l : changeRefs.values()) {
				l.cancel(ChangeList.CancelMode.INTERRUPT);
			}
			changeRefs.clear();
		});
		Clipboard clipboard = new Clipboard(parent.getDisplay());
		String clipText;
