						try {
							steps.get(0).setAction(Action.EDIT);
							steps.get(1).setAction(Action.PICK);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
