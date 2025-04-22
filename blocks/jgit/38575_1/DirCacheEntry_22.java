		try {
			DirCacheCheckout.checkValidPath(toString(path));
		} catch (InvalidPathException e) {
			CorruptObjectException p =
				new CorruptObjectException(e.getMessage());
			if (e.getCause() != null)
				p.initCause(e.getCause());
			throw p;
		}

