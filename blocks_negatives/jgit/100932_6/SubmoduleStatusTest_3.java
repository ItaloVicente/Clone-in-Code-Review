		Repository subRepo = Git.init().setBare(false)
				.setDirectory(new File(db.getWorkTree(), path)).call()
				.getRepository();
		assertNotNull(subRepo);

		RefUpdate update = subRepo.updateRef(Constants.HEAD, true);
		update.setNewObjectId(ObjectId
				.fromString("aaaa0000aaaa0000aaaa0000aaaa0000aaaa0000"));
		update.forceUpdate();
