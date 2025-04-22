	private static final Comparator<PathEdit> EDIT_CMP = new Comparator<PathEdit>() {
		public int compare(final PathEdit o1, final PathEdit o2) {
			final byte[] a = o1.path;
			final byte[] b = o2.path;
			return DirCache.cmp(a, a.length, b, b.length);
		}
	};
