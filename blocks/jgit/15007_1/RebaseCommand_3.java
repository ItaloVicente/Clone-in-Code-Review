
		ArrayList<RebaseTodoLine> toDoSteps = new ArrayList<RebaseTodoLine>();
		byte[] commentBytes = ("# Created by EGit: rebasing " + upstreamCommit.name()
				+ " onto " + headId.name()).getBytes(Constants.CHARACTER_ENCODING);
		toDoSteps.add(new RebaseTodoLine(commentBytes
		ObjectReader reader = walk.getObjectReader();
		for (RevCommit commit : cherryPickList) {
			byte[] shortMessageBytes = commit.getShortMessage().getBytes(
						Constants.CHARACTER_ENCODING);
			toDoSteps.add(new RebaseTodoLine(Action.PICK
					.abbreviate(commit)
					shortMessageBytes.length));
