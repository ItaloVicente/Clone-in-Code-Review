		FileRepository repo = setupRepoForShallowFetch();

		PackIndex idx = writeShallowPack(repo, 2, wants(c5), NONE, NONE);
		assertContent(idx,
				Arrays.asList(c4.getId(), c5.getId(), c4.getTree().getId(),
						c5.getTree().getId(), contentA.getId(),
						contentB.getId(), contentC.getId(), contentD.getId(),
						contentE.getId()));

		idx = writeShallowPack(repo, 2, wants(c2), haves(c4, c5), shallows(c4));
		assertContent(idx, Arrays.asList(c1.getId(), c2.getId(),
				c1.getTree().getId(), c2.getTree().getId()));
