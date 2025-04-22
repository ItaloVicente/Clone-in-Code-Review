				InteractiveHandler handler = new InteractiveHandler() {
					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						for (RebaseTodoLine step : steps) {
							if (step.getCommit().prefixCompare(commit) == 0) {
								try {
									step.setAction(RebaseTodoLine.Action.REWORD);
								} catch (IllegalTodoFileModification e) {
								}
