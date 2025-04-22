		final Repository submoduleRepo;
		try {
			submoduleRepo = SubmoduleWalk.getSubmoduleRepository(directory,
					e.getName(),
					repository != null ? repository.getFS() : FS.DETECTED);
		} catch (IOException exception) {
			return zeroid;
		}
		if (submoduleRepo == null)
			return zeroid;

		final ObjectId head;
		try {
			head = submoduleRepo.resolve(Constants.HEAD);
