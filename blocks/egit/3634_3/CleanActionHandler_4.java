		Set<String> fileList = op.dryRun();

		ListSelectionDialog dialog = new ListSelectionDialog(
												getShell(event), // shell
												fileList, // set of file names to populate
												new ArrayContentProvider(), // acp
												new ItemLabelProvider(), // ilp
												UIText.CleanDialog_HeaderMessage // branch name
									);
		dialog.setTitle(UIText.CleanDialog_TitleMessage);

		if (dialog.open() != IDialogConstants.OK_ID)
			return null;

		for (int i = 0; i < dialog.getResult().length; i++) {
			fileListReturned.add(dialog.getResult()[i].toString());
		}

		JobUtil.scheduleUserJob(op.setPaths(fileListReturned), "Clean", //$NON-NLS-1$
