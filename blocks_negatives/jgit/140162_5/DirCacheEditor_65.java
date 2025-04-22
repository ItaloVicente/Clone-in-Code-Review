	private static final Comparator<PathEdit> EDIT_CMP = new Comparator<PathEdit>() {
		@Override
		public int compare(PathEdit o1, PathEdit o2) {
			final byte[] a = o1.path;
			final byte[] b = o2.path;
			return cmp(a, a.length, b, b.length);
		}
	};
