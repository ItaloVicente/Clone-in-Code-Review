	public WorkbenchPlugin() {
		super();
		inst = this;
	}

	void reset() {
		editorRegistry = null;

		if (decoratorManager != null) {
