	@Test
	public void testIgnoredDirectory() throws Exception {
		writeTrashFile(".gitignore"
		writeTrashFile("src/ignored/sub/foo.txt"
		try (Git git = new Git(db)) {
			git.add().addFilepattern(".").call();
			RevCommit commit = git.commit().setMessage("adding .gitignore")
					.call();
			writeTrashFile("foo.txt"
			writeTrashFile("zzz.txt"
			git.add().addFilepattern("foo.txt").call();
			git.commit().setMessage("add file").call();
			assertEquals("Should not have entered ignored directory"
					resetHardAndCount(commit));
		}
	}

	@Test
	public void testIgnoredDirectoryWithTrackedContent() throws Exception {
		writeTrashFile("src/ignored/sub/foo.txt"
		try (Git git = new Git(db)) {
			git.add().addFilepattern(".").call();
			git.commit().setMessage("adding foo.txt").call();
			writeTrashFile(".gitignore"
			writeTrashFile("src/ignored/sub/foo.txt"
			writeTrashFile("src/ignored/other/bar.txt"
			git.add().addFilepattern(".").call();
			RevCommit commit = git.commit().setMessage("adding .gitignore")
					.call();
			writeTrashFile("foo.txt"
			writeTrashFile("zzz.txt"
			git.add().addFilepattern("foo.txt").call();
			git.commit().setMessage("add file").call();
			File file = writeTrashFile("src/ignored/sub/foo.txt"
			assertEquals("Should have entered ignored directory"
					resetHardAndCount(commit));
			checkFile(file
		}
	}

	@Test
	public void testResetWithChangeInGitignore() throws Exception {
		writeTrashFile(".gitignore"
		writeTrashFile("src/ignored/sub/foo.txt"
		try (Git git = new Git(db)) {
			git.add().addFilepattern(".").call();
			RevCommit initial = git.commit().setMessage("initial").call();
			writeTrashFile("src/newignored/foo.txt"
			writeTrashFile("src/.gitignore"
			git.add().addFilepattern(".").call();
			RevCommit commit = git.commit().setMessage("newignored").call();
			assertEquals("Should not have entered src/newignored directory"
					resetHardAndCount(initial));
			assertEquals("Should have entered src/newignored directory"
					resetHardAndCount(commit));
			deleteTrashFile("src/.gitignore");
			git.rm().addFilepattern("src/.gitignore").call();
			RevCommit top = git.commit().setMessage("Unignore newignore")
					.call();
			assertEquals("Should have entered src/newignored directory"
					resetHardAndCount(initial));
			assertEquals("Should have entered src/newignored directory"
					resetHardAndCount(commit));
			assertEquals("Should not have entered src/newignored directory"
					resetHardAndCount(top));

		}
	}

	private static class TestFileTreeIterator extends FileTreeIterator {

		private final int[] count;

		public TestFileTreeIterator(Repository repo
			super(repo);
			this.count = count;
		}

		protected TestFileTreeIterator(final WorkingTreeIterator p
				final File root
				int[] count) {
			super(p
			this.count = count;
		}

		@Override
		protected AbstractTreeIterator enterSubtree() {
			count[0] += 1;
			return new TestFileTreeIterator(this
					((FileEntry) current()).getFile()
					count);
		}
	}

	private int resetHardAndCount(RevCommit commit) throws Exception {
		int[] callCount = { 0 };
		DirCache cache = db.lockDirCache();
		FileTreeIterator workingTreeIterator = new TestFileTreeIterator(db
				callCount);
		try {
			DirCacheCheckout checkout = new DirCacheCheckout(db
					commit.getTree().getId()
			checkout.setFailOnConflict(false);
			checkout.checkout();
		} finally {
			cache.unlock();
		}
		return callCount[0];
	}

