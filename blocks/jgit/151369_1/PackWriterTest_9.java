		try (FileRepository repo = setupRepoForShallowFetch()) {
			PackIndex idx = writeShallowPack(repo
			assertContent(idx
					Arrays.asList(c4.getId()
							c5.getTree().getId()
							contentB.getId()
							contentD.getId()

			idx = writeShallowPack(repo
					shallows(c4));
			assertContent(idx
					c1.getTree().getId()
		}
