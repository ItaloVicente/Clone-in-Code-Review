		RevCommit second = git.commit().setMessage("second commit")
				.setAllowEmpty(true).call();
		MergeResult result = git.merge()
				.include(db.getRef("refs/heads/branch1")).call();
		assertEquals(MergeResult.MergeStatus.ALREADY_UP_TO_DATE
				result.getMergeStatus());
