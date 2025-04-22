						try {
							steps.get(1).setAction(Action.SQUASH);
							steps.get(2).setAction(Action.SQUASH);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
