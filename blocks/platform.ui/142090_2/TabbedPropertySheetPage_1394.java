	public String getTitleText(ISelection selection) {
		if (selection == null) {
			selection = currentSelection;
		}
		return registry.getLabelProvider().getText(selection);
	}

	public Image getTitleImage(ISelection selection) {
		if (selection == null) {
			selection = currentSelection;
		}
