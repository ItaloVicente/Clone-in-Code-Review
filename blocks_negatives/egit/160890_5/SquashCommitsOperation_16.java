				InteractiveHandler handler = new InteractiveHandler() {
					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						RevCommit firstCommit = commits.get(0);
						for (RebaseTodoLine step : steps) {
							if (isRelevant(step.getCommit())) {
								try {
									if (step.getCommit().prefixCompare(
											firstCommit) == 0)
										step.setAction(RebaseTodoLine.Action.PICK);
									else
										step.setAction(RebaseTodoLine.Action.SQUASH);
								} catch (IllegalTodoFileModification e) {
								}
