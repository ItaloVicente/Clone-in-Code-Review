	private static String[] getButtonLabel(Status status) {
		String[] buttonLabel = new String[1];
		switch (status) {
		case EDIT:
		case CONFLICTS:
		case STOPPED:
		case INTERACTIVE_PREPARED:
		case STASH_APPLY_CONFLICTS:
			buttonLabel[0] = IDialogConstants.PROCEED_LABEL;
			break;
		default:
			buttonLabel[0] = IDialogConstants.CLOSE_LABEL;
		}
		return buttonLabel;
	}

