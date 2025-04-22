		try (Git git = new Git(db)) {
			git.add().addFilepattern(folder1.getName())
					.addFilepattern(folder2.getName()).call();
			RevCommit commit1 = git.commit().setMessage("adding folders").call();

			recursiveDelete(folder1);
			recursiveDelete(folder2);
			git.rm().addFilepattern("folder1/file1.txt")
					.addFilepattern("folder1/file2.txt")
					.addFilepattern("folder2/file1.txt")
					.addFilepattern("folder2/file2.txt").call();
			RevCommit commit2 = git.commit()
					.setMessage("removing folders on 'branch'").call();

			git.checkout().setName(commit1.name()).call();

			MergeResult result = git.merge().include(commit2.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeResult.MergeStatus.FAST_FORWARD
					result.getMergeStatus());
			assertEquals(commit2
			assertFalse(folder1.exists());
			assertFalse(folder2.exists());
		}
