			if (filterBlobLimit >= 0) {
				pw.setFilterBlobLimit(filterBlobLimit);
				pw.setUseCachedPacks(false);
			} else {
				pw.setUseCachedPacks(true);
			}
