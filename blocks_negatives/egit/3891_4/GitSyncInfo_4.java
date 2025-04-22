		TreeWalk tw = new TreeWalk(repo);
		tw.setFilter(AndTreeFilter.create(TreeFilter.ANY_DIFF,
				PathFilter.create(localPath)));
		tw.setRecursive(true);

		try {
			int srcNth = tw.addTree(gsd.getSrcRevCommit().getTree());
			int dstNth = tw.addTree(gsd.getDstRevCommit().getTree());

			if (tw.next()) {
				return calculateKindImpl(repo, tw, srcNth, dstNth);
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}

		return IN_SYNC;
	}
