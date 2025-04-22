		if (confirm && saveHandler != null) {
			switch (saveHandler.promptToSave(part)) {
			case NO:
				return true;
			case CANCEL:
				return false;
			case YES:
				break;
			}
