	public static ObjectId findObject(AnyObjectId tree
			ObjectReader or)
			throws MissingObjectException
		TreeWalk tw = new TreeWalk(or);
		tw.addTree(tree);

		PathTokenizer tokenizer = tw.new PathTokenizer(
				path.getBytes(Constants.CHARSET));

		if (!tokenizer.findNextToken())
			return null;

		for (;;) {

			if (!tw.next())
				return null;

			if (tokenizer.compareCurrentToken()) {

				if (!tokenizer.findNextToken()) {
					return tw.getObjectId(0);
				} else {
					try {
						tw.enterSubtree();
					} catch (IncorrectObjectTypeException e) {
						return null;
					}
				}
			}

		}

	}

	private class PathTokenizer {

		private byte[] path;

		int pathOffset;

		int pathEnd;

		boolean hasCurrent;

		private PathTokenizer(byte[] byteArray) {
			this.path = byteArray;
		}

		private boolean findNextToken() {

			int totalLen = path.length;

			for (pathOffset = pathEnd; pathOffset < totalLen
					&& path[pathOffset] == '/'; pathOffset++) {
			}

			if (pathOffset == totalLen)
				return hasCurrent = false;

			for (pathEnd = pathOffset + 1; pathEnd < totalLen
					&& path[pathEnd] != '/'; pathEnd++) {
			}

			return hasCurrent = true;
		}

		private boolean compareCurrentToken() {

			byte[] path2 = currentHead.path;
			int pathOffset2 = currentHead.pathOffset;
			int pathLen2 = currentHead.pathLen;

			if (!hasCurrent)
				throw new IllegalStateException("No current token");

			if (pathEnd - pathOffset != pathLen2 - pathOffset2)
				return false;

			int len = pathEnd - pathOffset;
			for (int offset = 0; offset < len; offset++) {
				if (path[pathOffset + offset] != path2[pathOffset2 + offset]) {
					return false;
				}
			}

			return true;
		}
	}

