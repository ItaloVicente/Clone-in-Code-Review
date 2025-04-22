		try (FileRepository repo = setupRepoForShallowFetch()) {
			PackIndex idx = writeShallowPack(repo
			assertContent(idx
					Arrays.asList(c1.getId()
							c2.getTree().getId()
							contentB.getId()));

			idx = writeShallowPack(repo
					shallows(c1));
			assertContent(idx
					Arrays.asList(c4.getId()
							c5.getTree().getId()
							contentD.getId()
		}
