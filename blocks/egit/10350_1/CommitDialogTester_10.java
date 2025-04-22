
	public void setShowUntracked(boolean untracked) {
		SWTBotToolbarToggleButton button = commitDialog.bot()
				.toolbarToggleButtonWithTooltip(
						UIText.CommitDialog_ShowUntrackedFiles);
		selectToolbarToggle(button, untracked);
	}

	public boolean getShowUntracked() {
		SWTBotToolbarToggleButton button = commitDialog.bot()
				.toolbarToggleButtonWithTooltip(
						UIText.CommitDialog_ShowUntrackedFiles);
		return button.isChecked();
	}

