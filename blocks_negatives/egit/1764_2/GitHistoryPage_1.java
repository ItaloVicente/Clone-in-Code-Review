	private boolean inputIsSingleFile() {
		boolean isFile;
		isFile = (input.getItems() != null && input.getItems().length == 1 && input
				.getItems()[0].getType() == IResource.FILE);
		if (!isFile)
			isFile = (input.getFileList() != null
					&& input.getFileList().length == 1 && input.getFileList()[0]
					.isFile());
		return isFile;
	}

