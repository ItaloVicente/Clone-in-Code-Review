		try (RevWalk revWalk = new RevWalk(db)) {
			revWalk.setRetainBody(false);
			Map<String, Ref> tagsMap = db.getTags();
			Ref tagRef = null;

			for (Ref ref : tagsMap.values()) {
