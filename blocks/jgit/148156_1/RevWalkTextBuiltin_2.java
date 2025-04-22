	void enableAll(boolean on) throws Exception {
		if (on) {
			RevWalk walk = new RevWalk(getRepository());
			Set<ObjectId> processed = new HashSet<>();
			for (Ref a : db.getRefDatabase().getRefs()) {
				ObjectId oid = a.getPeeledObjectId();
				if (oid == null)
					oid = a.getObjectId();
				try {
					if (processed.add(oid))
						commits.add(walk.parseCommit(oid));
				} catch (IncorrectObjectTypeException e) {
				}
			}
			walk.close();
		}
	}
