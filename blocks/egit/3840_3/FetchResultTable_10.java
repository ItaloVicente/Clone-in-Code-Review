	void setData(final Repository db, final FetchResult fetchResult) {
		treeViewer.setInput(null);
		repo = db;
		reader = db.newObjectReader();
		abbrevations = new HashMap<ObjectId, String>();
		treeViewer.setInput(fetchResult);
	}

	private String safeAbbreviate(ObjectId id) {
		String abbrev = abbrevations.get(id);
		if (abbrev == null) {
			try {
				abbrev = reader.abbreviate(id).name();
			} catch (IOException cannotAbbreviate) {
				abbrev = id.name();
			}
			abbrevations.put(id, abbrev);
		}
		return abbrev;
