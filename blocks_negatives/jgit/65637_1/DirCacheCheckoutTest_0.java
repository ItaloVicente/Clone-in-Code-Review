		Git git = new Git(db);
		writeTrashFile("f", "f()");
		writeTrashFile("D/g", "g()");
		git.add().addFilepattern(".").call();
		git.commit().setMessage("inital").call();
		assertIndex(mkmap("f", "f()", "D/g", "g()"));

		git.branchCreate().setName("topic").call();

		writeTrashFile("f", "f()\nmaster");
		writeTrashFile("D/g", "g()\ng2()");
		writeTrashFile("E/h", "h()");
		git.add().addFilepattern(".").call();
		RevCommit master = git.commit().setMessage("master-1").call();
		assertIndex(mkmap("f", "f()\nmaster", "D/g", "g()\ng2()", "E/h", "h()"));

		checkoutBranch("refs/heads/topic");
		assertIndex(mkmap("f", "f()", "D/g", "g()"));

		writeTrashFile("f", "f()\nside");
		assertTrue(new File(db.getWorkTree(), "D/g").delete());
		writeTrashFile("G/i", "i()");
		git.add().addFilepattern(".").call();
		git.add().addFilepattern(".").setUpdate(true).call();
		RevCommit topic = git.commit().setMessage("topic-1").call();
		assertIndex(mkmap("f", "f()\nside", "G/i", "i()"));

		writeTrashFile("untracked", "untracked");

		resetHard(master);
		assertIndex(mkmap("f", "f()\nmaster", "D/g", "g()\ng2()", "E/h", "h()"));
		resetHard(topic);
		assertIndex(mkmap("f", "f()\nside", "G/i", "i()"));
		assertWorkDir(mkmap("f", "f()\nside", "G/i", "i()", "untracked",
				"untracked"));

		assertEquals(MergeStatus.CONFLICTING, git.merge().include(master)
				.call().getMergeStatus());
		assertEquals(
				"[D/g, mode:100644, stage:1][D/g, mode:100644, stage:3][E/h, mode:100644][G/i, mode:100644][f, mode:100644, stage:1][f, mode:100644, stage:2][f, mode:100644, stage:3]",
				indexState(0));

		resetHard(master);
		assertIndex(mkmap("f", "f()\nmaster", "D/g", "g()\ng2()", "E/h", "h()"));
		assertWorkDir(mkmap("f", "f()\nmaster", "D/g", "g()\ng2()", "E/h",
				"h()", "untracked", "untracked"));
