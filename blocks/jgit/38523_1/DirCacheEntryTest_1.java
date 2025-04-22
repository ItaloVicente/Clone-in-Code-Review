		try {
			DirCacheCheckout.checkValidPath(path);
			return true;
		} catch (InvalidPathException e) {
			return false;
		}
