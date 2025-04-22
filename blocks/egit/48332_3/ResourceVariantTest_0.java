		try (RevWalk walk = new RevWalk(repo);
				TreeWalk tw = new TreeWalk(repo)) {
			tw.addTree(walk.parseTree(baseId));

			while (tw.next()) {
				AbstractGitResourceVariant variant = TreeParserResourceVariant
						.create(repo, tw.getTree(0, CanonicalTreeParser.class));

				assertEquals(tw.getObjectId(0).getName(),
						variant.getContentIdentifier());
				assertEquals(tw.getObjectId(0), variant.getObjectId());
				assertEquals(tw.getRawMode(0), variant.getRawMode());
				if (iFile1.getName().equals(variant.getName())) {
					assertContentEquals(variant,
							BRANCH_CHANGE + INITIAL_CONTENT_1);
				} else if (!tw.isSubtree()) {
					assertContentEquals(variant,
							BRANCH_CHANGE + INITIAL_CONTENT_2);
				}
