	@Override
	void openObjectInAllPacks(Collection<PackedObjectLoader> out,
			WindowCursor curs, AnyObjectId objectId) throws IOException {
		wrapped.openObjectInAllPacks(out, curs, objectId);
	}

