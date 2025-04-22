	private void convertMergingRebase() throws IOException {
		File file = rebaseState.getDir();
		if (REBASE_APPLY.equals(file.getName())) {
			return;
		}
		file = rebaseState.getFile(GIT_REBASE_TODO);
		if (file.exists()) {
			return;
		}
		File done = rebaseState.getFile(DONE);
		if (done.exists()) {
			return;
		}
		int msgNum = -1;
		int end = -1;
		try {
			msgNum = Integer.parseInt(rebaseState.readFile(MSGNUM));
			end = Integer.parseInt(rebaseState.readFile(END));
		} catch (IOException | NumberFormatException e) {
		}
		if (msgNum <= 0 || end <= 0) {
			return;
		}
		List<RebaseTodoLine> lines = new ArrayList<>();
		try {
			for (int i = 1; i <= msgNum; i++) {
				AbbreviatedObjectId id = AbbreviatedObjectId
						.fromString(rebaseState.readFile(CMT_PREFIX + i));
				lines.add(new RebaseTodoLine(Action.PICK
			}
			repo.writeRebaseTodoFile(rebaseState.getPath(DONE)
			lines.clear();
			for (int i = msgNum + 1; i <= end; i++) {
				AbbreviatedObjectId id = AbbreviatedObjectId
						.fromString(rebaseState.readFile(CMT_PREFIX + i));
				lines.add(new RebaseTodoLine(Action.PICK
			}
			repo.writeRebaseTodoFile(rebaseState.getPath(GIT_REBASE_TODO)
					lines
		} catch (IOException e) {
			FileUtils.delete(file
			FileUtils.delete(done
		}
	}

	private RebaseTodoLine catchUpWithMsgNum(int doneCount) throws IOException {
		int msgNum = -1;
		try {
			msgNum = Integer.parseInt(rebaseState.readFile(MSGNUM));
		} catch (IOException | NumberFormatException e) {
		}
		if (msgNum <= doneCount) {
			return null;
		}
		return popSteps(msgNum - doneCount);
	}

