		try (FileRepository repo = setupRepoForShallowFetch()) {
			PackIndex idx = writeShallowPack(repo
			assertContent(idx
					contentA.getId()
					contentD.getId()

			idx = writeShallowPack(repo
			assertContent(idx
		}
