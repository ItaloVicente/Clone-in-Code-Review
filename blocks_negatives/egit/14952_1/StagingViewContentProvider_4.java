	private StagingFolderEntry[] getCompressedFolders() {
		if (compressedFolders == null) {
			String workTreePath = stagingView.getCurrentRepository()
					.getWorkTree().getAbsolutePath();
			List<File> parentList = new ArrayList<File>();
			List<StagingEntry> rootFileList = new ArrayList<StagingEntry>();
			compressedFolderList = new ArrayList<StagingFolderEntry>();
			for (StagingEntry file : content) {
				File parentFile = file.getSystemFile().getParentFile();
