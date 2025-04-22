		final int pOff = 0 < pathOffset ? pathOffset - 1 : pathOffset;
		String pathRel = TreeWalk.pathOf(this.path
		String parentRel = getParentPath(pathRel);

		if (determineDirectoryIgnored(parentRel)) {
			return true;
		}

