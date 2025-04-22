			writeTrashFile(INTERESTING_FILE
			git.add().addFilepattern(INTERESTING_FILE).call();
			RevCommit c2 = git.commit().setMessage("update file").call();

			RevCommit filteredC1 = new FilteredRevCommit(c1);
			RevCommit filteredC2 = new FilteredRevCommit(c2
					Arrays.asList(filteredC1));

			revWalk.parseHeaders(filteredC2);

			try (BlameGenerator generator = new BlameGenerator(db
					INTERESTING_FILE)) {
				generator.push(filteredC2);
