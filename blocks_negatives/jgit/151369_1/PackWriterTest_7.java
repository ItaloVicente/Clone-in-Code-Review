		FileRepository repo = setupRepoForShallowFetch();

		PackIndex idx = writeShallowPack(repo, 1, wants(c5), NONE, NONE);
		assertContent(idx,
				Arrays.asList(c5.getId(), c5.getTree().getId(),
						contentA.getId(), contentB.getId(), contentC.getId(),
						contentD.getId(), contentE.getId()));

		idx = writeShallowPack(repo, 1, wants(c3), haves(c5), shallows(c5));
		assertContent(idx, Arrays.asList(c3.getId(), c3.getTree().getId()));
