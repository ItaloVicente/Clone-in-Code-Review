
		ArrayList<RebaseTodoLine> toDoSteps = new ArrayList<RebaseTodoLine>();
		toDoSteps.add(new RebaseTodoLine(commentBytes
		ObjectReader reader = walk.getObjectReader();
		for (RevCommit commit : cherryPickList) {
			byte[] shortMessageBytes = commit.getShortMessage().getBytes(
						Constants.CHARACTER_ENCODING);
			toDoSteps.add(new RebaseTodoLine(Action.PICK
					.abbreviate(commit)
					shortMessageBytes.length));
