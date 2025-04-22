	private static int nextParsableToken(byte[] buf
		while (tokenBegin <= lineEnd
				&& (buf[tokenBegin] == ' ' || buf[tokenBegin] == '\t' || buf[tokenBegin] == '\r'))
			tokenBegin++;
		if (tokenBegin > lineEnd)
			return -1;
		return tokenBegin;
	}

	private static RebaseTodoLine parseLine(byte[] buf
			int lineEnd) {
		RebaseTodoLine.Action action = null;
		AbbreviatedObjectId commit = null;

		int nextSpace = RawParseUtils.next(buf
		int tokenCount = 0;
		while (tokenCount < 3 && nextSpace < lineEnd) {
			switch (tokenCount) {
			case 0:
				String actionToken = new String(buf
						- tokenBegin - 1);
				tokenBegin = nextSpace;
				action = RebaseTodoLine.Action.parse(actionToken);
				if (action == null)
				break;
			case 1:
				nextSpace = RawParseUtils.next(buf
				String commitToken = new String(buf
						- tokenBegin - 1);
				tokenBegin = nextSpace;
				commit = AbbreviatedObjectId.fromString(commitToken);
				break;
			case 2:
				return new RebaseTodoLine(action
						buf
			}
			tokenCount++;
		}
		return null;
	}

