	static final class AddEdit extends PathEdit {

		private final ObjectId data;

		private final long length;

		public AddEdit(String entryPath
			super(entryPath);
			this.data = data;
			this.length = length;
		}

		@Override
		public void apply(DirCacheEntry ent) {
			ent.setFileMode(FileMode.REGULAR_FILE);
			ent.setLength(length);
			ent.setObjectId(data);
		}

	}

	private ObjectId buildTree(HashMap<String
			throws IOException {
		DirCache lockDirCache = DirCache.newInCore();
		DirCacheEditor editor = lockDirCache.editor();
		if (headEntries != null) {
