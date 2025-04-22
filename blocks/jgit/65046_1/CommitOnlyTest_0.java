			try (RevWalk rw = new RevWalk(repo)) {
				final TreeWalk tw = TreeWalk.forPath(repo
						rw.parseTree(headId));
				return new String(tw.getObjectReader().open(tw.getObjectId(0))
						.getBytes());
			}
