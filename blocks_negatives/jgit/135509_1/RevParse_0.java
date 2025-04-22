	protected void run() throws Exception {
		if (all) {
			for (Ref r : db.getRefDatabase().getRefs()) {
				ObjectId objectId = r.getObjectId();
				if (objectId == null) {
					throw new NullPointerException();
