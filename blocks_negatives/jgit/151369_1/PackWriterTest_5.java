		FileRepository repo = setupRepoForShallowFetch();

		PackIndex idx = writeShallowPack(repo, 1, wants(c5), NONE, NONE);
		assertContent(idx,
				Arrays.asList(c5.getId(), c5.getTree().getId(),
						contentA.getId(), contentB.getId(), contentC.getId(),
						contentD.getId(), contentE.getId()));

		idx = writeShallowPack(repo, 1, wants(c4), haves(c5), shallows(c5));
		assertContent(idx, Arrays.asList(c4.getId(), c4.getTree().getId()));
