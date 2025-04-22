						try {
							steps.get(1).setAction(Action.FIXUP);
							steps.get(2).setAction(Action.SQUASH);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
