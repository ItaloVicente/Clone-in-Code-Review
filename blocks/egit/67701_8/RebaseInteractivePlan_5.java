	private void reparsePlan(Repository repository) {
		if (repository != null) {
			try (RevWalk walk = new RevWalk(repository.newObjectReader())) {
				doneList = parseDone(repository, walk);
				todoList = parseTodo(repository, walk);
			}
			planList = JoinedList.wrap(doneList, todoList);
			notifyPlanWasUpdatedFromRepository();
