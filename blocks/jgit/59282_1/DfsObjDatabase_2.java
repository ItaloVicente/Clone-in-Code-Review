	public boolean has(AnyObjectId objectId
			throws IOException {
		try (ObjectReader or = newReader()) {
			or.setAvoidUnreachableObjects(avoidUnreachableObjects);
			return or.has(objectId);
		}
	}

