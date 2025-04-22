
		ObjectIdSubclassMap<ObjectId> provided =
			new ObjectIdSubclassMap<ObjectId>();
		if (ensureObjectsProvidedVisible) {
			for (ObjectId id : getNewObjectIds()) {
				provided.add(id);
			}
		}

		RevCommit c;
		while ((c = ow.next()) != null) {
			if (ensureObjectsProvidedVisible) {
				if (!provided.contains(c)) {
					reject(commands);
					break;
				}
			}
		}

		RevObject o;
		while ((o = ow.nextObject()) != null) {
			if (o instanceof RevBlob && !db.hasObject(o))
				throw new MissingObjectException(o

			if (ensureObjectsProvidedVisible) {
				if (!provided.contains(o)) {
					reject(commands);
					break;
				}
			}
		}
	}

	private static void reject(List<ReceiveCommand> commands) {
		for (ReceiveCommand cmd : commands)
			cmd.setResult(Result.REJECTED_OTHER_REASON);
