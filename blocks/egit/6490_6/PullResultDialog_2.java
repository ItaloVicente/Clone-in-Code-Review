		return UIUtils.getDialogBoundSettings(getClass());
	}

	@Override
	protected int getDialogBoundsStrategy() {
		return hasUpdates ? DIALOG_PERSISTLOCATION | DIALOG_PERSISTSIZE : DIALOG_PERSISTLOCATION;
