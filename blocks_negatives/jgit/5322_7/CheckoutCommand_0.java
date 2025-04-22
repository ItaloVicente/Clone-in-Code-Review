			DirCacheCheckout dco;
			DirCache dc = repo.lockDirCache();
			try {
				dco = new DirCacheCheckout(repo, headTree, dc,
						newCommit.getTree());
				dco.setFailOnConflict(true);
