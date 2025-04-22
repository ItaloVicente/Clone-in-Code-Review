		diskRepo = createRepositoryWithOptions();
		refDb = diskRepo.getRefDatabase();
		if (refDb instanceof RefDirectory) {
			refDir = (RefDirectory) refDb;
		} else if (refDb instanceof InMemoryRefDatabase) {
			RefDatabase wrapped = ((InMemoryRefDatabase) refDb)
					.getWrappedRefDatabase();
			if (wrapped instanceof RefDirectory) {
				refDir = (RefDirectory) wrapped;
			}
		}
