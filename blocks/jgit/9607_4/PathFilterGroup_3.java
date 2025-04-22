			byte[] r = max.pathRaw;
			final int cmp = walker.isPathPrefix(r
			if (cmp > 0)
				throw StopWalkException.INSTANCE;

			return false;
