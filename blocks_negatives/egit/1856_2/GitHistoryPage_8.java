		final IResource[] inResources = input.getItems();
		final File[] inFiles = input.getFileList();
		if (inResources != null && inResources.length == 0) {
			setErrorMessage(UIText.GitHistoryPage_NoInputMessage);
			return false;
		}
