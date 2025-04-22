		if (refs instanceof RefDirectory) {
			RefDirectory refDir = (RefDirectory) refs;
			refDir.log(false
		} else if (refs instanceof InMemoryRefDatabase) {
			InMemoryRefDatabase refCache = (InMemoryRefDatabase) refs;
			refCache.log(false
		}
