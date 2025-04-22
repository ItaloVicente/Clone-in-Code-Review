
		rebase = new BranchRebaseModeCombo(main);
		BranchRebaseMode rebaseMode = PullCommand.getRebaseMode(myBranchName,
				myConfig);
		rebase.setRebaseMode(rebaseMode);
		GridDataFactory.fillDefaults().grab(true, false)
				.align(SWT.BEGINNING, SWT.CENTER)
				.applyTo(rebase.getViewer().getCombo());
