	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		getButton(OK).setEnabled(false);
	}

	private void findGitDirsRecursive(File root, Set<String> strings,
