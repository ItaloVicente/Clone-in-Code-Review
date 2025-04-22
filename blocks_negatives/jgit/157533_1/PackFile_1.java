	/** Sorts PackFiles to be most recently created to least recently created. */
	public static final Comparator<PackFile> SORT = new Comparator<PackFile>() {
		@Override
		public int compare(PackFile a, PackFile b) {
			return b.packLastModified.compareTo(a.packLastModified);
		}
	};
