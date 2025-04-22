				startWalk.setFilter(PathFilterGroup.createFromStrings(paths));
			final boolean checkoutIndex = startCommit == null
					&& startPoint == null;
			if (!checkoutIndex)
				startWalk.addTree(revWalk.parseCommit(getStartPoint())
						.getTree());
			else
				startWalk.addTree(new DirCacheIterator(dc));

			final File workTree = repo.getWorkTree();
			final ObjectReader r = repo.getObjectDatabase().newReader();
