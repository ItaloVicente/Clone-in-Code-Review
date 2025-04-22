		switch (mode.getBits() & FileMode.TYPE_MASK) {
		case FileMode.TYPE_MISSING:
		case FileMode.TYPE_TREE:
			throw new IllegalArgumentException("Invalid mode " + mode
					+ " for path " + getPathString());
		}
