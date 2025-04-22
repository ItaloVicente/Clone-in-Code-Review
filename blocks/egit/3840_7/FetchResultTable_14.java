	void setData(final Repository db, final FetchResult fetchResult) {
		treeViewer.setInput(null);
		repo = db;
		reader = db.newObjectReader();
		abbrevations = new HashMap<ObjectId, String>();
		treeViewer.setInput(fetchResult);
