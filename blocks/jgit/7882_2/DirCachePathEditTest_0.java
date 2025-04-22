	private static final class RecordingEdit extends PathEdit {
		final List<DirCacheEntry> entries = new ArrayList<DirCacheEntry>();

		public RecordingEdit(String entryPath) {
			super(entryPath);
		}

		@Override
		public void apply(DirCacheEntry ent) {
			entries.add(ent);
		}
	}

