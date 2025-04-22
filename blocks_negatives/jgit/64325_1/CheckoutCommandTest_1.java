		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter", "tstFilter", "smudge",
				"sh " + slashify(smudge_filter.getPath()));
		config.setString("filter", "tstFilter", "clean",
				"sh " + slashify(clean_filter.getPath()));
		config.save();
		writeTrashFile(".gitattributes", "*.txt filter=tstFilter");
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add attributes").call();

		writeTrashFile("filterTest.txt", "hello world, V1");
		git.add().addFilepattern("filterTest.txt").call();
		git.commit().setMessage("add filterText.txt").call();
		assertEquals(
				"[.gitattributes, mode:100644, content:*.txt filter=tstFilter][Test.txt, mode:100644, content:Some other change][filterTest.txt, mode:100644, content:hello world, @version]",
				indexState(CONTENT));

		git.checkout().setCreateBranch(true).setName("test2").call();
		writeTrashFile("filterTest.txt", "bon giorno world, V1");
		git.add().addFilepattern("filterTest.txt").call();
		git.commit().setMessage("modified filterText.txt").call();

		assertTrue(git.status().call().isClean());
		assertEquals(
				"[.gitattributes, mode:100644, content:*.txt filter=tstFilter][Test.txt, mode:100644, content:Some other change][filterTest.txt, mode:100644, content:bon giorno world, @version]",
				indexState(CONTENT));

		git.checkout().setName("refs/heads/test").call();
		assertTrue(git.status().call().isClean());
		assertEquals(
				"[.gitattributes, mode:100644, content:*.txt filter=tstFilter][Test.txt, mode:100644, content:Some other change][filterTest.txt, mode:100644, content:hello world, @version]",
				indexState(CONTENT));
		assertEquals("hello world, V1", read("filterTest.txt"));
