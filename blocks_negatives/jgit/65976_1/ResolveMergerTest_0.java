		Git git = new Git(db);
		git.add().addFilepattern(folder1.getName()).call();
		RevCommit base = git.commit().setMessage("adding folder").call();

		recursiveDelete(folder1);
		git.rm().addFilepattern("folder1/file1.txt")
				.addFilepattern("folder1/file2.txt").call();
		RevCommit other = git.commit()
				.setMessage("removing folders on 'other'").call();

		git.checkout().setName(base.name()).call();

		file = new File(db.getWorkTree(), "unrelated.txt");
		write(file, "unrelated");

		git.add().addFilepattern("unrelated.txt").call();
		RevCommit head = git.commit().setMessage("Adding another file").call();

		file = new File(folder1, "file3.txt");
		write(file, "folder1--file3.txt");

		ResolveMerger merger = (ResolveMerger) strategy.newMerger(db, false);
		merger.setCommitNames(new String[] { "BASE", "HEAD", "other" });
		merger.setWorkingTreeIterator(new FileTreeIterator(db));
		boolean ok = merger.merge(head.getId(), other.getId());
		assertTrue(ok);
		assertTrue(file.exists());
