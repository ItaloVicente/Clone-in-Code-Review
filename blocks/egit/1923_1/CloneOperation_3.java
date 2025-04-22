		DirCacheCheckout dirCacheCheckout = new DirCacheCheckout(
				local, null, local.lockDirCache(), mapCommit.getTree());
		dirCacheCheckout.setFailOnConflict(true);
		boolean result = dirCacheCheckout.checkout();
		if (!result)
			throw new IOException("Internal error occured on checking out files"); //$NON-NLS-1$
