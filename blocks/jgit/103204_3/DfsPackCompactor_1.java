
			RefDatabase refdb = repo.getRefDatabase();
			if (refdb instanceof DfsReftableDatabase) {
				objdb.clearCache();
				((DfsReftableDatabase) refdb).clearCache();
			}
