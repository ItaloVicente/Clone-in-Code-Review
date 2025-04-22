		if (foundOld > 0) {
			String message = NLS.bind(
					UIText.RepositorySearchDialog_SomeDirectoriesHiddenMessage,
					Integer.valueOf(foundOld));
			setMessage(message, IMessageProvider.INFORMATION);
		} else if (directories.isEmpty())
			setMessage(UIText.RepositorySearchDialog_NothingFoundMessage,
					IMessageProvider.INFORMATION);

		checkAllItem.setEnabled(!validDirs.isEmpty());
		uncheckAllItem.setEnabled(!validDirs.isEmpty());
		fTree.clearFilter();
		fTreeViewer.setInput(validDirs);
		fTreeViewer.setAllChecked(true);
		enableOk();
