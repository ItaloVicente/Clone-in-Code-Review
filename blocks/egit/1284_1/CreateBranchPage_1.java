		if (commitMode) {
			sourceLabel.setText(UIText.CreateBranchPage_SourceCommitLabel);
			sourceLabel
					.setToolTipText(UIText.CreateBranchPage_SourceCommitTooltip);

		} else {
			sourceLabel.setText(UIText.CreateBranchPage_SourceBranchLabel);
			sourceLabel
					.setToolTipText(UIText.CreateBranchPage_SourceBranchTooltip);
		}
		if (commitMode)
			this.branchCombo = new Combo(main, SWT.READ_ONLY | SWT.SINGLE);
		else
			this.branchCombo = new Combo(main, SWT.READ_ONLY | SWT.DROP_DOWN);
