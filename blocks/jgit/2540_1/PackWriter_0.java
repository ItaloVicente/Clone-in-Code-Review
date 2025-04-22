		if (!wantTags.isEmpty()) {
			all = new ArrayList<ObjectId>(wantTags.size());
			for (RevTag tag : wantTags)
				all.add(tag.getObject());
			q = walker.parseAny(all
			try {
				while (q.next() != null) {
				}
			} finally {
				q.release();
			}
		}

		for (RevObject obj : wantObjs)
			walker.markStart(obj);
		for (RevObject obj : haveObjs)
			walker.markUninteresting(obj);

