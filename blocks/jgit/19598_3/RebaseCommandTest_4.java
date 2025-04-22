						try {
							steps.get(0).setAction(Action.PICK);
							steps.remove(1);
							steps.get(1).setAction(Action.FIXUP);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
