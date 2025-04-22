					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(0).setAction(Action.FIXUP);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
