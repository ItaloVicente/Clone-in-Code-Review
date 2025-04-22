		diskRepo = createRepositoryWithOptions();
		refDb = diskRepo.getRefDatabase();
		if (refDb instanceof RefDirectory) {
			refDir = (RefDirectory) refDb;
		} else if (refDb instanceof InMemoryRefDatabase) {
			refDir = (RefDirectory) ((InMemoryRefDatabase) refDb)
					.getWrappedRefDatabase();
		}
