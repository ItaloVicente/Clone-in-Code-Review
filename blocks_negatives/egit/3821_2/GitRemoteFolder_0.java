			TreeWalk tw;
			if (path.length() > 0) {
				tw = TreeWalk.forPath(repo, path, revCommit.getTree());
			} else {
				tw = new TreeWalk(repo);
				tw.addTree(revCommit.getTree());
			}
			while (tw.next()) {
				if (monitor.isCanceled())
					break;
