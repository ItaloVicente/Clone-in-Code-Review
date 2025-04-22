	private Tree buildTree(HashMap<String, String> headEntries) throws IOException {
		Tree tree = new Tree(db);
		if (headEntries == null)
			return tree;
		FileTreeEntry fileEntry;
		Tree parent;
		ObjectInserter oi = db.newObjectInserter();
		try {
