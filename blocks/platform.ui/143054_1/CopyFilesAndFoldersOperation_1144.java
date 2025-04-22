		Runnable query = () -> {
			String message;
			int resultId[] = { IDialogConstants.YES_ID,
					IDialogConstants.YES_TO_ALL_ID, IDialogConstants.NO_ID,
					IDialogConstants.CANCEL_ID };
			String labels[] = new String[] { IDialogConstants.YES_LABEL,
					IDialogConstants.YES_TO_ALL_LABEL,
					IDialogConstants.NO_LABEL,
					IDialogConstants.CANCEL_LABEL };

			if (destination.getType() == IResource.FOLDER) {
				if (homogenousResources(source, destination)) {
					message = NLS
							.bind(
									IDEWorkbenchMessages.CopyFilesAndFoldersOperation_overwriteMergeQuestion,
									destination.getFullPath()
									.makeRelative());
				} else {
					if (destination.isLinked()) {
