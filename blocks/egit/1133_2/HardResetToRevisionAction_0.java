
	@Override
	public void run(IAction act) {
		if (!MessageDialog.openQuestion(wp.getSite().getShell(),
				UIText.ResetTargetSelectionDialog_ResetQuestion,
				UIText.ResetTargetSelectionDialog_ResetConfirmQuestion))
			return;
		super.run(act);
	}

