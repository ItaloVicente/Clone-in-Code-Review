		writeTrashFile("b", "1\nb(main)\n3\n");
		git.add().addFilepattern("b").call();
		git.commit().setMessage("main").call();

		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.CONFLICTING, result.getMergeStatus());
