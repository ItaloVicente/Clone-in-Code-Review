				List<RebaseTodoLine> doneLines = repo.readRebaseTodo(
						rebaseState.getPath(DONE)
				List<RebaseTodoLine> steps = repo.readRebaseTodo(
						rebaseState.getPath(GIT_REBASE_TODO)
				RebaseTodoLine step = doneLines.get(doneLines.size() - 1);
				if (newHead != null
						&& step.getAction() != Action.PICK) {
					RebaseTodoLine newStep = new RebaseTodoLine(
							step.getAction()
							AbbreviatedObjectId.fromObjectId(newHead)
							step.getShortMessage());
					RebaseResult result = processStep(newStep
					if (result != null) {
						return result;
					}
				}
