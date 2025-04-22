	InsertLooseObjectResult insertUnpackedObject(File tmp
			boolean createDuplicate) {
		InsertLooseObjectResult result = wrapped.insertUnpackedObject(tmp
				objectId
		switch (result) {
		case INSERTED:
		case EXISTS_LOOSE:
			if (!unpackedObjects.contains(objectId))
				unpackedObjects.add(objectId);
			break;

		case EXISTS_PACKED:
		case FAILURE:
			break;
		}
		return result;
