	}

	private void setClipboard(IResource[] resources, String[] fileNames, String names) {
		try {
			if (fileNames.length > 0) {
				clipboard.setContents(new Object[] { resources, fileNames, names }, new Transfer[] {
						ResourceTransfer.getInstance(), FileTransfer.getInstance(), TextTransfer.getInstance() });
			} else {
				clipboard.setContents(new Object[] { resources, names },
						new Transfer[] { ResourceTransfer.getInstance(), TextTransfer.getInstance() });
			}
		} catch (SWTError e) {
			if (e.code != DND.ERROR_CANNOT_SET_CLIPBOARD) {
