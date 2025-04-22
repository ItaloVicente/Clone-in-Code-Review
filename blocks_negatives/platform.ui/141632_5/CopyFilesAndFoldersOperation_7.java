		Runnable query = new Runnable() {
			@Override
			public void run() {
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
