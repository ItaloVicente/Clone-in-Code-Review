			writeTrashFile(OTHER_FILE, join("a", "b", "c"));
			git.add().addFilepattern(OTHER_FILE).call();
			git.commit().setMessage("amend file").call();

			writeTrashFile(INTERESTING_FILE, join("1", "2", "3"));
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit c2 = git.commit().setMessage("amend file").call();

			RevCommit filteredC1 = new FilteredRevCommit(c1);
			RevCommit filteredC2 = new FilteredRevCommit(c2, filteredC1);

			revWalk.parseHeaders(filteredC2);
