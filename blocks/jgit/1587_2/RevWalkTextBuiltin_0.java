		if (all)
			for (Ref a : db.getAllRefs().values()) {
				ObjectId oid = a.getPeeledObjectId();
				if (oid == null)
					oid = a.getObjectId();
				try {
					commits.add(walk.parseCommit(oid));
				} catch (IncorrectObjectTypeException e) {
				}
			}

