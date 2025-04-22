		ObjectIdSubclassMap<ObjectId> baseObjects = null;
		ObjectIdSubclassMap<ObjectId> providedObjects = null;

		if (checkReferencedIsReachable) {
			baseObjects = ip.getBaseObjectIds();
			providedObjects = ip.getNewObjectIds();
		}
		ip = null;

