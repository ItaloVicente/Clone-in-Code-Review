	public static int pathCompare(byte[] aPath
			byte[] bPath
		while (aPos < aEnd && bPos < bEnd) {
			int cmp = (aPath[aPos++] & 0xff) - (bPath[bPos++] & 0xff);
			if (cmp != 0) {
				return cmp;
			}
		}

		int a = lastPathChar(aPath
		int b = lastPathChar(bPath
		return a - b;
	}

	private static int lastPathChar(byte[] path
		if (pos < end) {
			return path[pos] & 0xff;
		} else if ((mode & TYPE_MASK) == TYPE_TREE) {
			return '/';
		}
		return 0;
	}

