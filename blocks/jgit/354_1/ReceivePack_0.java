
		ObjectIdSubclassMap<ObjectId> provided =
			new ObjectIdSubclassMap<ObjectId>();
		if (paranoidMode) {
			for (ObjectId id : getNewObjectIds()) {
				provided.add(id);
			}
		}

		RevCommit c;
		while ((c = ow.next()) != null) {
			if (paranoidMode) {
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

			if (paranoidMode) {
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
