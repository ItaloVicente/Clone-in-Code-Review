
		assertEquals(Status.NOTHING_TO_COMMIT
		assertEquals(RepositoryState.REBASING_INTERACTIVE
				db.getRepositoryState());

		git.rebase().setOperation(Operation.SKIP).call();
