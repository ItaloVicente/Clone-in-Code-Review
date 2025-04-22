	public LargeFileLimitsPreferenceHandler() {
		this(new DialogPromptForEditor());
	}

	public LargeFileLimitsPreferenceHandler(PromptForEditor promptForEditor) {
		this.promptForEditor = promptForEditor;
