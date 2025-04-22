			DirCacheCheckout dirCacheCheckout = new DirCacheCheckout(
					repository, oldTree, repository.lockDirCache(), newTree);
			dirCacheCheckout.setFailOnConflict(true);
			boolean result = dirCacheCheckout.checkout();
			if (!result)
				retryDelete(dirCacheCheckout);
