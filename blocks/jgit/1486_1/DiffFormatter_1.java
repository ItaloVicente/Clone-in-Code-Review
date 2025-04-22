	private byte[] open(ObjectReader reader
			AbbreviatedObjectId id) throws IOException {
		if (mode == FileMode.MISSING)
			return EMPTY;

		if (mode.getObjectType() != Constants.OBJ_BLOB)
			return EMPTY;

		if (isBinary(path))
			return BINARY;

		if (!id.isComplete()) {
			Collection<ObjectId> ids = reader.resolve(id);
			if (ids.size() == 1)
				id = AbbreviatedObjectId.fromObjectId(ids.iterator().next());
			else if (ids.size() == 0)
				throw new MissingObjectException(id
			else
				throw new AmbiguousObjectException(id
		}

		try {
			ObjectLoader ldr = reader.open(id.toObjectId());
			return ldr.getBytes(binaryFileThreshold);

		} catch (LargeObjectException.ExceedsLimit overLimit) {
			return BINARY;

		} catch (LargeObjectException.ExceedsByteArrayLimit overLimit) {
			return BINARY;

		} catch (LargeObjectException.OutOfMemory tooBig) {
			return BINARY;

		} catch (LargeObjectException tooBig) {
			tooBig.setObjectId(id.toObjectId());
			throw tooBig;
		}
	}

	private boolean isBinary(String path) {
		return false;
	}

