		if (this.myBaseCommit != null) {
			sourceLabel.setText(UIText.CreateBranchPage_SourceCommitLabel);
			sourceLabel
					.setToolTipText(UIText.CreateBranchPage_SourceCommitTooltip);

		} else {
			sourceLabel.setText(UIText.CreateBranchPage_SourceBranchLabel);
			sourceLabel
					.setToolTipText(UIText.CreateBranchPage_SourceBranchTooltip);
		}
		this.branchCombo = new Combo(main, SWT.READ_ONLY | SWT.DROP_DOWN);
		branchCombo.setData("org.eclipse.swtbot.widget.key", "BaseBranch"); //$NON-NLS-1$ //$NON-NLS-2$

		GridDataFactory.fillDefaults().span(2, 1).grab(true, false).applyTo(
				this.branchCombo);
