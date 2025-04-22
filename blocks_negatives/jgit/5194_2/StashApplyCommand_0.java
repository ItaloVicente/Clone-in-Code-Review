			RevTree indexTree = revWalk.parseCommit(wtCommit.getParent(1))
					.getTree();
			DirCacheCheckout dco = new DirCacheCheckout(repo,
					repo.lockDirCache(), indexTree, new FileTreeIterator(repo));
			dco.setFailOnConflict(true);
			dco.checkout();

			RevTree headTree = revWalk.parseCommit(wtCommit.getParent(0))
					.getTree();
