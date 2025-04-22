		if (showResetType) {
			if (resetType == ResetType.HARD) {
				if (!MessageDialog.openQuestion(getShell(),
						UIText.BranchSelectionDialog_ReallyResetTitle,
						UIText.BranchSelectionDialog_ReallyResetMessage)) {
					return;
				}
			}
		}

