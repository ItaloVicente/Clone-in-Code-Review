
		@Override
		public String toString() {
			String p = DirCacheEntry.toString(path);
			return getClass().getSimpleName() + '[' + p + ']';
		}
