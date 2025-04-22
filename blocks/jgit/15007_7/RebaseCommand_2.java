		List<RebaseTodoLine> todoLines = new LinkedList<RebaseTodoLine>();
		List<RebaseTodoLine> poppedLines = new LinkedList<RebaseTodoLine>();

		for (RebaseTodoLine line : repo.readRebaseTodo(
				rebaseState.getPath(GIT_REBASE_TODO)
			if (poppedLines.size() >= numSteps
					|| RebaseTodoLine.Action.COMMENT.equals(line.getAction()))
				todoLines.add(line);
			else
				poppedLines.add(line);
