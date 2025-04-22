					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(0).setAction(Action.SQUASH);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
