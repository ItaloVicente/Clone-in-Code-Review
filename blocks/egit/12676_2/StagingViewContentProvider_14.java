
	public int getCount() {
		if (content == null) {
			return 0;
		} else {
			return content.length;
		}
	}

	private class FolderComparator implements Comparator {
		public int compare(Object obj0, Object obj1) {
			StagingFolderEntry folder0 = (StagingFolderEntry) obj0;
			StagingFolderEntry folder1 = (StagingFolderEntry) obj1;
			return folder0.getPath().toOSString()
					.compareTo(folder1.getPath().toOSString());
		}
	}
}
