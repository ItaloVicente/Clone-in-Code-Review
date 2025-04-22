	}

	public void setIgnoreCase(boolean ignoreCase) {
		fIgnoreCase = ignoreCase;
	}

	public boolean isCaseIgnored() {
		return fIgnoreCase;
	}

	public void setMatchEmptyString(boolean matchEmptyString) {
		fMatchEmptyString = matchEmptyString;
	}

	public void setMultipleSelection(boolean multipleSelection) {
		fIsMultipleSelection = multipleSelection;
	}

	public void setAllowDuplicates(boolean allowDuplicates) {
		fAllowDuplicates = allowDuplicates;
	}

	public void setSize(int width, int height) {
		fWidth = width;
		fHeight = height;
	}

	public void setEmptyListMessage(String message) {
		fEmptyListMessage = message;
	}

	public void setEmptySelectionMessage(String message) {
		fEmptySelectionMessage = message;
	}

	public void setValidator(ISelectionStatusValidator validator) {
		fValidator = validator;
	}

