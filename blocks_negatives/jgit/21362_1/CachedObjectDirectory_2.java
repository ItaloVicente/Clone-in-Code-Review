		return openObjectImpl1(curs, objectId);
	}

	@Override
	ObjectLoader openObject1(WindowCursor curs, AnyObjectId objectId)
			throws IOException {
		if (unpackedObjects.contains(objectId))
			return wrapped.openObject2(curs, objectId.name(), objectId);
		return wrapped.openObject1(curs, objectId);
	}

	@Override
	boolean hasObject2(String objectId) {
		return unpackedObjects.contains(ObjectId.fromString(objectId));
	}

	@Override
	ObjectLoader openObject2(WindowCursor curs, String objectName,
			AnyObjectId objectId) throws IOException {
		if (unpackedObjects.contains(objectId))
			return wrapped.openObject2(curs, objectName, objectId);
