		try (Repository submoduleRepo = SubmoduleWalk.getSubmoduleRepository(
				directory
				repository != null ? repository.getFS() : FS.DETECTED)) {
			if (submoduleRepo == null) {
				return zeroid;
			}
			ObjectId head = submoduleRepo.resolve(Constants.HEAD);
			if (head == null) {
				return zeroid;
			}
			byte[] id = new byte[Constants.OBJECT_ID_LENGTH];
			head.copyRawTo(id
			return id;
