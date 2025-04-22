		RefDatabase refDb = repo.getRefDatabase();
		if (refDb instanceof FileReftableDatabase) {
			((FileReftableDatabase) refDb).fullCompaction();
			return;
		}

		Collection<Ref> refs = refDb.getRefsByPrefix(Constants.R_REFS);
