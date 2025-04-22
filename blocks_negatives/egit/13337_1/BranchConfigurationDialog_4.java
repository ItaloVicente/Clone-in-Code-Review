		Label branchLabel = new Label(main, SWT.NONE);
		branchLabel.setText(UIText.BranchConfigurationDialog_UpstreamBranchLabel);
		branchText = new Combo(main, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(branchText);

		try {
			for (Ref ref : myRepository.getRefDatabase()
					.getRefs(Constants.R_HEADS).values())
				branchText.add(ref.getName());
			for (Ref ref : myRepository.getRefDatabase()
					.getRefs(Constants.R_REMOTES).values())
				branchText.add(ref.getName());
		} catch (IOException e) {
			Activator.logError(UIText.BranchConfigurationDialog_ExceptionGettingRefs, e);
		}
