		assertEquals(RepositoryState.SAFE
				.getRepositoryState());

		res = target.pull().call();
		assertEquals(res.getMergeResult().getMergeStatus()
				MergeStatus.ALREADY_UP_TO_DATE);
