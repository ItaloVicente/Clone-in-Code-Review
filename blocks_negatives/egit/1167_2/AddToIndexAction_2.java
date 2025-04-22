	@Override
	protected void postOperation() {
		Collection<IFile> notAddedFiles = operation.getNotAddedFiles();
		if (notAddedFiles.size()==0)
			return;
		final String title = UIText.AddToIndexAction_addingFilesFailed;
		String message = UIText.AddToIndexAction_indexesWithUnmergedEntries;
		message += getFileList(notAddedFiles);
		final String fMessage = message;
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openWarning(wp.getSite().getShell(), title, fMessage);
			}
		});

	}

	private static String getFileList(Collection<IFile> notAddedFiles) {
		for (IFile file : notAddedFiles) {
			result += file.getName();
		}
		return result;
	}

