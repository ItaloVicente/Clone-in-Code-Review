	private void replaceWith(String[] files, boolean headRevision) {
		if (files == null || files.length == 0)
			return;
		CheckoutCommand checkoutCommand = new Git(currentRepository).checkout();
		if (headRevision)
			checkoutCommand.setStartPoint(Constants.HEAD);
		for (String path : files)
			checkoutCommand.addPath(path);
		try {
			checkoutCommand.call();
		} catch (Exception e) {
			Activator.handleError(UIText.StagingView_checkoutFailed, e, true);
		}
	}

	private String[] getSelectedFiles(IStructuredSelection selection) {
		List<String> result = new ArrayList<String>();
		Iterator iterator = selection.iterator();
		while (iterator.hasNext()) {
			Object selectedItem = iterator.next();
			if (selectedItem instanceof StagingEntry) {
				StagingEntry stagingEntry = (StagingEntry) selectedItem;
				result.add(stagingEntry.getPath());
			}
		}
		return result.toArray(new String[result.size()]);
	}

