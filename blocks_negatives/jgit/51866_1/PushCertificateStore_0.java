		if (reader == null) {
			load();
		}
		sortPending(pending);

		ObjectId curr = commit;
		DirCache dc = newDirCache();
