	static final class AddEdit extends PathEdit {

		private final ObjectId data;

		public AddEdit(String entryPath
			super(entryPath);
			this.data = data;
		}

		@Override
		public void apply(DirCacheEntry ent) {
			ent.setFileMode(FileMode.REGULAR_FILE);
			ent.setLength(1);
			ent.setObjectId(data);
		}

	}

	private ObjectId buildTree(HashMap<String
			throws IOException {
		DirCache lockDirCache = DirCache.newInCore();
		DirCacheEditor editor = lockDirCache.editor();
		if (headEntries != null) {
