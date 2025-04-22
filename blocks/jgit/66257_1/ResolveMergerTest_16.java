		try (Git git = new Git(db)) {
			git.add().addFilepattern(folder1.getName()).call();
			RevCommit base = git.commit().setMessage("adding folder").call();

			recursiveDelete(folder1);
			git.rm().addFilepattern("folder1/file1.txt")
					.addFilepattern("folder1/file2.txt").call();
			RevCommit other = git.commit()
					.setMessage("removing folders on 'other'").call();

			git.checkout().setName(base.name()).call();

			file = new File(db.getWorkTree()
			write(file

			git.add().addFilepattern("unrelated.txt").call();
			RevCommit head = git.commit().setMessage("Adding another file").call();

			file = new File(folder1
			write(file

			ResolveMerger merger = (ResolveMerger) strategy.newMerger(db
			merger.setCommitNames(new String[] { "BASE"
			merger.setWorkingTreeIterator(new FileTreeIterator(db));
			boolean ok = merger.merge(head.getId()
			assertTrue(ok);
			assertTrue(file.exists());
		}
