
	@Test
	public void commitOnlyShouldCommitUnmergedPathAndNotAffectOthers()
			throws Exception {
		DirCache index = db.lockDirCache();
		DirCacheBuilder builder = index.builder();
		addUnmergedEntry("unmerged1"
		addUnmergedEntry("unmerged2"
		DirCacheEntry other = new DirCacheEntry("other");
		other.setFileMode(FileMode.REGULAR_FILE);
		builder.add(other);
		builder.commit();

		writeTrashFile("unmerged1"
		writeTrashFile("unmerged2"
		writeTrashFile("other"

		assertEquals("[other
				+ "[unmerged1
				+ "[unmerged1
				+ "[unmerged1
				+ "[unmerged2
				+ "[unmerged2
				+ "[unmerged2
				indexState(0));

		Git git = new Git(db);
		RevCommit commit = git.commit().setOnly("unmerged1")
				.setMessage("Only one file").call();

		assertEquals("[other
				+ "[unmerged2
				+ "[unmerged2
				+ "[unmerged2
				indexState(0));

		TreeWalk walk = TreeWalk.forPath(db
		assertEquals(FileMode.REGULAR_FILE
		walk.release();
	}

	private static void addUnmergedEntry(String file
		DirCacheEntry stage1 = new DirCacheEntry(file
		DirCacheEntry stage2 = new DirCacheEntry(file
		DirCacheEntry stage3 = new DirCacheEntry(file
		stage1.setFileMode(FileMode.REGULAR_FILE);
		stage2.setFileMode(FileMode.REGULAR_FILE);
		stage3.setFileMode(FileMode.REGULAR_FILE);
		builder.add(stage1);
		builder.add(stage2);
		builder.add(stage3);
	}
