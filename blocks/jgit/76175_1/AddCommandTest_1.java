	@Test
	public void testAttributesWithTreeWalkFilter()
			throws IOException
		writeTrashFile(".gitattributes"
		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		File script = writeTempFile("sed s/o/e/g");

		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(script.getPath()));
			config.save();

			git.add().addFilepattern(".gitattributes").call();
			git.commit().setMessage("attr").call();
			git.add().addFilepattern("src/a.txt").addFilepattern("src/a.tmp")
					.addFilepattern(".gitattributes").call();
			git.commit().setMessage("c1").call();
			assertTrue(git.status().call().isClean());
		}
	}

