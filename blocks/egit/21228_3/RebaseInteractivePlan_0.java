		RevWalk walk = new RevWalk(repository.newObjectReader());
		try {
			doneList = parseDone(walk);
			todoList = parseTodo(walk);
		} finally {
			walk.release();
		}
