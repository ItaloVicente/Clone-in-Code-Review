			RefDatabase refDb = diskRepo.getRefDatabase();
			if (refDb instanceof RefDirectory) {
				refdir = (RefDirectory) refDb;
			} else if (refDb instanceof InMemoryRefDatabase) {
				RefDatabase wrapped = ((InMemoryRefDatabase) refDb)
						.getWrappedRefDatabase();
				if (wrapped instanceof RefDirectory) {
					refdir = (RefDirectory) wrapped;
				}
			}
