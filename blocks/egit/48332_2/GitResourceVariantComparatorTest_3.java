		try (TreeWalk tw = new TreeWalk(repo)) {
			int nth = tw.addTree(commit.getTree());

			tw.next();
			tw.enterSubtree(); // enter project node
			tw.next();
			GitRemoteFolder base = new GitRemoteFolder(repo, null, commit,
					tw.getObjectId(nth), tw.getNameString());

			tw.next();
			GitRemoteFolder remote = new GitRemoteFolder(repo, null, commit,
					tw.getObjectId(nth), tw.getNameString());

			assertFalse(grvc.compare(base, remote));
		}
