			writeTrashFile("file2"
			git.add().addFilepattern("file2").call();
			git.commit().setMessage("Add file2\nnew line").call();
			assertTrue(new File(db.getWorkTree()

			git.rebase().setUpstream("HEAD~1")
					.runInteractively(new InteractiveHandler() {

						@Override
						public void prepareSteps(List<RebaseTodoLine> steps) {
							try {
								steps.get(0).setAction(Action.SQUASH);
							} catch (IllegalTodoFileModification e) {
								fail("unexpected exception: " + e);
							}
