				List<RebaseTodoLine> doneLines = repo.readRebaseTodo(
						rebaseState.getPath(DONE)
				if (newHead != null
						&& doneLines.get(doneLines.size() - 1).getAction() == Action.EDIT) {
					rebaseState.createFile(AMEND
					return stop(newHead
				}
