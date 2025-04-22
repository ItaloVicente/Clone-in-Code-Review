		new Git(db).branchCreate().setName("initial").call();
		RevCommit second = new Git(db).commit().setMessage("second commit")
				.call();
		assertEquals(toString("  initial", "* master"),
				toString(execute("git branch --contains 6fd41be")));
		assertEquals("* master",
				toString(execute("git branch --contains " + second.name())));
