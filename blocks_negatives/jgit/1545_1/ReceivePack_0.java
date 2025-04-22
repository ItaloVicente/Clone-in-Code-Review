		if (checkReferencedIsReachable) {
			for (ObjectId id : baseObjects) {
				   RevObject b = ow.lookupAny(id, Constants.OBJ_BLOB);
				   if (!b.has(RevFlag.UNINTERESTING))
				     throw new MissingObjectException(b, b.getType());
			}
		}

