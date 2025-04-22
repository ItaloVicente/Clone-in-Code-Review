		if (remoteMode)
			nameLabel.setToolTipText(NLS.bind(
					UIText.CreateBranchPage_BranchNameTooltip,
					Constants.R_REMOTES));
		else
			nameLabel.setToolTipText(NLS.bind(
					UIText.CreateBranchPage_BranchNameTooltip,
					Constants.R_HEADS));
