	List<Step> loadSteps() throws IOException {
		byte[] buf = IO.readFully(rebaseState.getFile(GIT_REBASE_TODO));
		int ptr = 0;
		int tokenBegin = 0;
		ArrayList<Step> r = new ArrayList<Step>();
		while (ptr < buf.length) {
			tokenBegin = ptr;
			ptr = RawParseUtils.nextLF(buf, ptr);
			int nextSpace = RawParseUtils.next(buf, tokenBegin, ' ');
			int tokenCount = 0;
			Step current = null;
			while (tokenCount < 3 && nextSpace < ptr) {
				switch (tokenCount) {
				case 0:
					String actionToken = new String(buf, tokenBegin, nextSpace
							- tokenBegin - 1);
					tokenBegin = nextSpace;
					if (actionToken.charAt(0) == '#') {
						tokenCount = 3;
						break;
					}
					Action action = Action.parse(actionToken);
					if (action != null)
						current = new Step(Action.parse(actionToken));
					break;
				case 1:
					if (current == null)
						break;
					nextSpace = RawParseUtils.next(buf, tokenBegin, ' ');
					String commitToken = new String(buf, tokenBegin, nextSpace
							- tokenBegin - 1);
					tokenBegin = nextSpace;
					current.commit = AbbreviatedObjectId
							.fromString(commitToken);
					break;
				case 2:
					if (current == null)
						break;
					nextSpace = ptr;
					int length = ptr - tokenBegin;
					current.shortMessage = new byte[length];
					System.arraycopy(buf, tokenBegin, current.shortMessage, 0,
							length);
					r.add(current);
					break;
				}
				tokenCount++;
			}
		}
		return r;
	}
