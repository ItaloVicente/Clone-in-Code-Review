		FileRepository repo = setupRepoForShallowFetch();

		PackIndex idx = writeShallowPack(repo, 1, wants(c2), NONE, NONE);
		assertContent(idx, Arrays.asList(c2.getId(), c2.getTree().getId(),
				contentA.getId(), contentB.getId()));

		idx = writeShallowPack(repo, 1, wants(c5), haves(c2), shallows(c2));
		assertContent(idx, Arrays.asList(c5.getId(), c5.getTree().getId(),
				contentC.getId(), contentD.getId(), contentE.getId()));
