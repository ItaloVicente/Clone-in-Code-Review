			String message) {
		if (filesToCommit != null) {
			this.filesToCommit = new IFile[filesToCommit.length];
			System.arraycopy(filesToCommit, 0, this.filesToCommit, 0,
					filesToCommit.length);
		}
		this.notIndexed = notIndexed;
		this.notTracked = notTracked;
