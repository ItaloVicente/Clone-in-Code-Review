
	private boolean determineDirectoryIgnored(String pathRel)
			throws IOException {
		final int pOff = 0 < pathOffset ? pathOffset - 1 : pathOffset;
		final String base = TreeWalk.pathOf(this.path
		final String pathAbs = concatPath(base
		return determineDirectoryIgnored(pathRel
	}

	private boolean determineDirectoryIgnored(String pathRel
			throws IOException {
		assert pathRel.length() == 0 || (pathRel.charAt(0) != '/'
				&& pathRel.charAt(pathRel.length() - 1) != '/');
		assert pathAbs.length() == 0 || (pathAbs.charAt(0) != '/'
				&& pathAbs.charAt(pathAbs.length() - 1) != '/');
		assert pathAbs.endsWith(pathRel);

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
		for (String path = pathRel; ignoreNode != null
				&& !"".equals(path); path = getParentPath(path)) {
			ignored = ignoreNode.checkIgnored(path
			if (ignored != null) {
				state.directoryToIgnored.put(pathAbs
				return ignored;
			}
		}

		if (!(this.parent instanceof WorkingTreeIterator)) {
			state.directoryToIgnored.put(pathAbs
			return false;
		}

		final WorkingTreeIterator wtParent = (WorkingTreeIterator) this.parent;
		final String parentRelPath = concatPath(
				TreeWalk.pathOf(this.path
				pathRel);
		assert concatPath(TreeWalk.pathOf(wtParent.path
				Math.max(0
						.equals(pathAbs);
		return wtParent.determineDirectoryIgnored(parentRelPath
	}

	private static String getParentPath(String path) {
		final int slashIndex = path.lastIndexOf('/'
		if (slashIndex > 0) {
			return path.substring(path.charAt(0) == '/' ? 1 : 0
		}
		return path.length() > 0 ? "" : null;
	}

	private static String concatPath(String p1
		return p1 + (p1.length() > 0 && p2.length() > 0 ? "/" : "") + p2;
	}
