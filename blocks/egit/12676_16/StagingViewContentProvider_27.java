
	public int getCount() {
		if (content == null)
			return 0;
		else
			return content.length;
	}

	private static class FolderComparator implements
			Comparator<StagingFolderEntry> {
		public static FolderComparator INSTANCE = new FolderComparator();
		public int compare(StagingFolderEntry o1, StagingFolderEntry o2) {
			return o1.getPath().toString().compareTo(o2.getPath().toString());
		}
	}

}
