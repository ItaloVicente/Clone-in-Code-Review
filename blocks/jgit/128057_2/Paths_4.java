	private static int modeCompare(int aMode
		if ((aMode & TYPE_MASK) == TYPE_GITLINK
				|| (bMode & TYPE_MASK) == TYPE_GITLINK) {
			return 0;
		}
		return lastPathChar(aMode) - lastPathChar(bMode);
	}

