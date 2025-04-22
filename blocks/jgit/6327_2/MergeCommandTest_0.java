	@Test
	public void testMergeRemovingFoldersWithoutFastForward() throws Exception {
		File folder1 = new File(db.getWorkTree()
		File folder2 = new File(db.getWorkTree()
		FileUtils.mkdir(folder1);
		FileUtils.mkdir(folder2);
		File file = new File(folder1
		write(file
		file = new File(folder1
		write(file
		file = new File(folder2
		write(file
		file = new File(folder2
		write(file

		Git git = new Git(db);
		git.add().addFilepattern(folder1.getName())
				.addFilepattern(folder2.getName()).call();
		RevCommit base = git.commit().setMessage("adding folders").call();

		recursiveDelete(folder1);
		recursiveDelete(folder2);
		git.rm().addFilepattern("folder1/file1.txt")
				.addFilepattern("folder1/file2.txt")
				.addFilepattern("folder2/file1.txt")
				.addFilepattern("folder2/file2.txt").call();
		RevCommit other = git.commit()
				.setMessage("removing folders on 'branch'").call();

		git.checkout().setName(base.name()).call();

		file = new File(folder2
		write(file

		git.add().addFilepattern(folder2.getName()).call();
		git.commit().setMessage("adding another file").call();

		MergeResult result = git.merge().include(other.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();

		assertEquals(MergeResult.MergeStatus.MERGED
				result.getMergeStatus());
		assertFalse(folder1.exists());
	}

