		if (all)
			for (Ref a : db.getAllRefs().values()) {
				ObjectId oid = a.getPeeledObjectId();
				commits.add(walk.parseCommit((oid == null) ? a.getObjectId() : oid));
			}

