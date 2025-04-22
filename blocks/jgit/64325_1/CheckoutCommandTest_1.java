		try (Git git2 = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(smudge_filter.getPath()));
			config.setString("filter"
					"sh " + slashify(clean_filter.getPath()));
			config.save();
			writeTrashFile(".gitattributes"
			git2.add().addFilepattern(".gitattributes").call();
			git2.commit().setMessage("add attributes").call();

			writeTrashFile("filterTest.txt"
			git2.add().addFilepattern("filterTest.txt").call();
			git2.commit().setMessage("add filterText.txt").call();
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));

			git2.checkout().setCreateBranch(true).setName("test2").call();
			writeTrashFile("filterTest.txt"
			git2.add().addFilepattern("filterTest.txt").call();
			git2.commit().setMessage("modified filterText.txt").call();

			assertTrue(git2.status().call().isClean());
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));

			git2.checkout().setName("refs/heads/test").call();
			assertTrue(git2.status().call().isClean());
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));
			assertEquals("hello world
		}
