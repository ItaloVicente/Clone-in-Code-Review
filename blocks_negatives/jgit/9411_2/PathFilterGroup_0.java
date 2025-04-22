			final int n = paths.length;
			for (int i = 0;;) {
				final byte[] r = paths[i].pathRaw;
				final int cmp = walker.isPathPrefix(r, r.length);
				if (cmp == 0)
					return true;
				if (++i < n)
					continue;
				if (cmp > 0)
					throw StopWalkException.INSTANCE;
				return false;
			}
