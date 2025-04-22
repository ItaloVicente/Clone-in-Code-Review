	private ISection[] sections;

	private boolean controlsCreated;

	public TabContents() {
		controlsCreated = false;
	}

	public int getSectionIndex(ISection section) {
		for (int i = 0; i < sections.length; i++) {
