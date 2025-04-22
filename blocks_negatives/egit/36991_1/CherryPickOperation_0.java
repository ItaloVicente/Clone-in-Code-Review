				InteractiveHandler handler = new InteractiveHandler() {
					public void prepareSteps(List<RebaseTodoLine> steps) {
						for (RebaseTodoLine step : steps) {
							try {
								step.setAction(RebaseTodoLine.Action.PICK);
							} catch (IllegalTodoFileModification e) {
							}
