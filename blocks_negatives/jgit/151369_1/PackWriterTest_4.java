		FileRepository repo = setupRepoForShallowFetch();

		PackIndex idx = writeShallowPack(repo, 2, wants(c2), NONE, NONE);
		assertContent(idx,
				Arrays.asList(c1.getId(), c2.getId(), c1.getTree().getId(),
						c2.getTree().getId(), contentA.getId(),
						contentB.getId()));

		idx = writeShallowPack(repo, 2, wants(c5), haves(c1, c2), shallows(c1));
		assertContent(idx,
				Arrays.asList(c4.getId(), c5.getId(), c4.getTree().getId(),
						c5.getTree().getId(), contentC.getId(),
						contentD.getId(), contentE.getId()));
