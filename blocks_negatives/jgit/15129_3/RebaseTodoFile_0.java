				if (includeComments)
					r.add(new RebaseTodoLine(RawParseUtils.decode(buf,
							tokenBegin, lineEnd + 1)));
				continue;
			}
			while (tokenBegin <= lineEnd
					&& (buf[tokenBegin] == ' ' || buf[tokenBegin] == '\t' || buf[tokenBegin] == '\r'))
				tokenBegin++;
			if (tokenBegin > lineEnd) {
				if (includeComments)
					r.add(new RebaseTodoLine(RawParseUtils.decode(buf,
							lineStart, 1 + lineEnd)));
				continue;
			}
			int nextSpace = RawParseUtils.next(buf, tokenBegin, ' ');
			int tokenCount = 0;
			while (tokenCount < 3 && nextSpace < ptr) {
				switch (tokenCount) {
				case 0:
					String actionToken = new String(buf, tokenBegin, nextSpace
							- tokenBegin - 1);
					tokenBegin = nextSpace;
					action = RebaseTodoLine.Action.parse(actionToken);
					break;
				case 1:
					if (action == null)
						break;
					nextSpace = RawParseUtils.next(buf, tokenBegin, ' ');
					String commitToken = new String(buf, tokenBegin, nextSpace
							- tokenBegin - 1);
					tokenBegin = nextSpace;
					commit = AbbreviatedObjectId.fromString(commitToken);
					break;
				case 2:
					if (action == null)
						break;
					r.add(new RebaseTodoLine(action, commit, RawParseUtils
							.decode(buf, tokenBegin, 1 + lineEnd)));
					break;
