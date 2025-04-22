		RefDatabase refDb = repo.getRefDatabase();
		if (refDb instanceof FileReftableDatabase) {
			pm.beginTask(JGitText.get().packRefs
			try {
				((FileReftableDatabase) refDb).compactFully();
			} finally {
				pm.endTask();
			}
			return;
		}

		Collection<Ref> refs = refDb.getRefsByPrefix(Constants.R_REFS);
