		new Git(db).branchCreate().setName("initial").call();
		RevCommit second = new Git(db).commit().setMessage("second commit")
				.call();
		assertArrayOfLinesEquals(new String[] { "  initial", "* master", "" },
				execute("git branch --contains 6fd41be"));
		assertArrayOfLinesEquals(new String[] { "* master", "" },
				execute("git branch --contains " + second.name()));
