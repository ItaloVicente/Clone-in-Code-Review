
			final int cmp = walker.isPathPrefix(max, max.length);
			if (cmp > 0)
				throw StopWalkException.INSTANCE;

			return false;
