	private boolean isNewLine(Control ctrl) {
		MUIElement element = (MUIElement) ctrl
				.getData(AbstractPartRenderer.OWNING_ME);
		if (element != null && element.getTags().contains(NEW_LINE))
			return true;

		return false;
	}

