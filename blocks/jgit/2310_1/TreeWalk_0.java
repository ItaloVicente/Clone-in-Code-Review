	public static ObjectId findObject(AnyObjectId tree
			ObjectReader or)
			throws MissingObjectException
		TreeWalk tw = new TreeWalk(or);
		tw.addTree(tree);

		PathTokenizer tokenizer = tw.new PathTokenizer(
				path.getBytes(Constants.CHARSET));

		if (!tokenizer.findNextToken()) {
			return null;
		}

		while (true) {

			if (!tw.next()) {
				return null;
			}

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

		private byte[] bytes;

		int pos;

		int end;

		boolean hasCurrent;

		private PathTokenizer(byte[] byteArray) {
			this.bytes = byteArray;
		}

		private boolean findNextToken() {

			int totalLen = bytes.length;

			for (pos = end; pos < totalLen && bytes[pos] == '/'; pos++) {
			}

			if (pos == totalLen) {
				return hasCurrent = false;
			}

			for (end = pos + 1; end < totalLen && bytes[end] != '/'; end++) {
			}

			return hasCurrent = true;
		}

		private boolean compareCurrentToken() {

			byte[] arr2 = currentHead.path;
			int off2 = currentHead.pathOffset;
			int end2 = currentHead.pathLen;

			if (!hasCurrent) {
				throw new IllegalStateException("No current token");
			}

			if (end - pos != end2 - off2) {
				return false;
			}

			int len = end - pos;
			for (int curPos = 0; curPos < len; curPos++) {
				if (bytes[pos + curPos] != arr2[off2 + curPos]) {
					return false;
				}
			}

			return true;
		}

	}

