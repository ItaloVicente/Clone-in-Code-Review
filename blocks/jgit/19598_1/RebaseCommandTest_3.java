				try {
					steps.get(0).setAction(Action.PICK);
					steps.remove(1);
					steps.get(1).setAction(Action.SQUASH);
				} catch (IllegalTodoFileModification e) {
					fail("unexpected exception: " + e);
				}
