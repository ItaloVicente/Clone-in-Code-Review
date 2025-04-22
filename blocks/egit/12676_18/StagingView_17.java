	Presentation getPresentation() {
		return presentation;
	}

	String getFilterString() {
		if (filterText != null)
			return filterText.getText().trim();
		else
			return ""; //$NON-NLS-1$
	}

