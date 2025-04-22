	private static void parseComments(byte[] buf
			List<RebaseTodoLine> r
		RebaseTodoLine line = null;
		String commentString = RawParseUtils.decode(buf
				tokenBegin
		try {
			skip = nextParsableToken(buf
			if (skip != -1) {
				line = parseLine(buf
				line.setAction(Action.COMMENT);
				line.setComment(commentString);
			}
		} catch (Exception e) {
			line = null;
		} finally {
			if (line == null)
				line = new RebaseTodoLine(commentString);
			r.add(line);
		}
	}

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

