
		ObjectIdSubclassMap<ObjectId> provided =
			new ObjectIdSubclassMap<ObjectId>();
		if (ensureObjectsProvidedVisible) {
			for (ObjectId id : getBaseObjectIds()) {
				   RevObject b = ow.lookupAny(id);
				   if (!b.has(RevFlag.UNINTERESTING))
				     throw new MissingObjectException(b
			}
			for (ObjectId id : getNewObjectIds()) {
				provided.add(id);
			}
		}

		RevCommit c;
		while ((c = ow.next()) != null) {
			if (ensureObjectsProvidedVisible && !provided.contains(c))
				throw new MissingObjectException(c
		}

		RevObject o;
		while ((o = ow.nextObject()) != null) {
			if (o instanceof RevBlob && !db.hasObject(o))
				throw new MissingObjectException(o

			if (ensureObjectsProvidedVisible && !provided.contains(o))
				throw new MissingObjectException(o
		}
