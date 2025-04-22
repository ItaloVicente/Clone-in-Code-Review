		try {
			DirCacheCheckout.checkValidPath(refName);
		} catch (InvalidPathException e) {
			return false;
		}

