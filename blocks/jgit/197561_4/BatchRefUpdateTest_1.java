			RefDatabase refDb = diskRepo.getRefDatabase();
			if (refDb instanceof RefDirectory) {
				refdir = (RefDirectory) refDb;
				refdir.setRetrySleepMs(Arrays.asList(0
			} else if (refDb instanceof InMemoryRefDatabase) {
				refdir = (RefDirectory) ((InMemoryRefDatabase) refDb)
						.getWrappedRefDatabase();
			}
