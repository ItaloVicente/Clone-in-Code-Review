	public List<RebaseTodoLine> readRebaseTodo(File f
			throws IOException {
		byte[] buf = IO.readFully(f);
		int ptr = 0;
		int tokenBegin = 0;
		List<RebaseTodoLine> r = new LinkedList<RebaseTodoLine>();
		while (ptr < buf.length) {
			RebaseTodoLine.Action action = null;
			AbbreviatedObjectId commit = null;
			tokenBegin = ptr;
			ptr = RawParseUtils.nextLF(buf
			int lineStart = tokenBegin;
			int lineEnd = ptr - 2;
			if (lineEnd >= 0 && buf[lineEnd] == '\r')
				lineEnd--;
			int tokenCount = 0;
			if (buf[tokenBegin] == '#') {
				if (includeComments)
					r.add(new RebaseTodoLine(buf
							- tokenBegin));
				continue;
			}
			while (tokenBegin <= lineEnd
					&& (buf[tokenBegin] == ' ' || buf[tokenBegin] == '\t' || buf[tokenBegin] == '\r'))
				tokenBegin++;
			if (tokenBegin > lineEnd) {
				if (includeComments)
					r.add(new RebaseTodoLine(buf
							- lineStart));
				continue;
			}
			int nextSpace = RawParseUtils.next(buf
			while (tokenCount < 3 && nextSpace < ptr) {
				switch (tokenCount) {
				case 0:
					String actionToken = new String(buf
							- tokenBegin - 1);
					tokenBegin = nextSpace;
					action = RebaseTodoLine.Action.parse(actionToken);
					break;
				case 1:
					if (action == null)
						break;
					nextSpace = RawParseUtils.next(buf
					String commitToken = new String(buf
							- tokenBegin - 1);
					tokenBegin = nextSpace;
					commit = AbbreviatedObjectId.fromString(commitToken);
					break;
				case 2:
					if (action == null)
						break;
					r.add(new RebaseTodoLine(action
							+ lineEnd - tokenBegin));
					break;
				}
				tokenCount++;
			}
		}
		return r;
	}

	public void writeRebaseTodoFile(File f
			boolean append)
			throws IOException {
		BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(f
		try {
			StringBuilder sb = new StringBuilder();
			for (RebaseTodoLine step : steps) {
				sb.setLength(0);
				if (RebaseTodoLine.Action.COMMENT.equals(step.action))
					sb.append(RawParseUtils.decode(step.getComment()));
				else {
					sb.append(step.getAction().toToken());
					sb.append(step.getCommit().name());
					sb.append(RawParseUtils.decode(step.getShortMessage())
							.trim());
				}
				fw.write(sb.toString());
				fw.newLine();
			}
		} finally {
			fw.close();
		}
	}
