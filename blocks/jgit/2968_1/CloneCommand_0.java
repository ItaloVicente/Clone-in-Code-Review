		if (!bare) {
			DirCache dc = repo.lockDirCache();
			DirCacheCheckout co = new DirCacheCheckout(repo
					commit.getTree());
			co.checkout();
		}
