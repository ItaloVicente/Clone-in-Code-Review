		RefDatabase refs = db.getRefDatabase();
		if (refs instanceof RefDirectory) {
			RefDirectory refDir = (RefDirectory) refs;
			RefUpdate update = refDir.newUpdate(refName
			update.setNewObjectId(newId);
			refDir.log(false
		} else if (refs instanceof InMemoryRefDatabase) {
			InMemoryRefDatabase refCache = (InMemoryRefDatabase) refs;
			RefUpdate update = refCache.newUpdate(refName
			update.setNewObjectId(newId);
			refCache.log(false
		}
