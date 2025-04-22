
	private boolean determineDirectoryIgnored(String pathRel)
			throws IOException {
		final int pOff = 0 < pathOffset ? pathOffset - 1 : pathOffset;
		String base = TreeWalk.pathOf(this.path
		String pathAbs = base + pathRel;
		Boolean ignored = state.directoryToIgnored.get(pathAbs);
		if (ignored != null) {
			return ignored;
		}

		final String parentRel = getParentPath(pathRel);
		if (parentRel != null && determineDirectoryIgnored(parentRel)) {
			state.directoryToIgnored.put(pathAbs
			return true;
		}

		final IgnoreNode ignoreNode = getIgnoreNode();
		while (ignoreNode != null && !"".equals(pathRel)) {
			ignored = ignoreNode.checkIgnored(pathRel
			if (ignored != null) {
				state.directoryToIgnored.put(pathAbs
				return ignored;
			}

			pathRel = getParentPath(pathRel);
		}

		if (!(this.parent instanceof WorkingTreeIterator)) {
			state.directoryToIgnored.put(pathAbs
			return false;
		}

		final WorkingTreeIterator wtParent = (WorkingTreeIterator) this.parent;
		String relPath = TreeWalk.pathOf(this.path
		return wtParent.determineDirectoryIgnored(relPath);
	}

	private static String getParentPath(String path) {
		final int slashIndex = path.lastIndexOf('/'
		if (slashIndex > 0) {
			return path.substring(0
		}
		return path.length() > 0 ? "" : null;
	}
