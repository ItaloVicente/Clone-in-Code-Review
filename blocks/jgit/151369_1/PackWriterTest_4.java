		try (FileRepository repo = setupRepoForShallowFetch()) {
			PackIndex idx = writeShallowPack(repo
			assertContent(idx
					contentA.getId()

			idx = writeShallowPack(repo
			assertContent(idx
					contentC.getId()
		}
