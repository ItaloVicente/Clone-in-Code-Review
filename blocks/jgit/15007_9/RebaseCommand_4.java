
		ArrayList<RebaseTodoLine> toDoSteps = new ArrayList<RebaseTodoLine>();
		ObjectReader reader = walk.getObjectReader();
		for (RevCommit commit : cherryPickList)
			toDoSteps.add(new RebaseTodoLine(Action.PICK
					.abbreviate(commit)
		repo.writeRebaseTodoFile(rebaseState.getPath(GIT_REBASE_TODO)
				toDoSteps
