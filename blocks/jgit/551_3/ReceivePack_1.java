		final Set<ObjectId> baseObjects;

		if (ensureObjectsProvidedVisible)
			baseObjects = getBaseObjectIds();
		else
			baseObjects = Collections.emptySet();

