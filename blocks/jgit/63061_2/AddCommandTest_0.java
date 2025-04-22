	@Test
	public void testAddCanReplaceFileByDirectory()
			throws IOException
		try (Git git = new Git(db)) {
			writeTrashFile("df"
			git.add().addFilepattern("df").call();
			assertEquals("[df
					indexState(CONTENT));
			FileUtils.delete(new File(db.getWorkTree()
			writeTrashFile("df/f"
			git.add().addFilepattern("df").call();
			assertEquals("[df/f
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddCanReplaceDirectoryByFile()
			throws IOException
		try (Git git = new Git(db)) {
			writeTrashFile("df/f"
			git.add().addFilepattern("df").call();
			assertEquals("[df/f
					indexState(CONTENT));
			FileUtils.delete(new File(db.getWorkTree()
					FileUtils.RECURSIVE);
			writeTrashFile("df"
			git.add().addFilepattern("df").call();
			assertEquals("[df
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddCanReplaceFileByPartOfDirectory()
			throws IOException
		try (Git git = new Git(db)) {
			writeTrashFile("df"
			writeTrashFile("z"
			git.add().addFilepattern("df").addFilepattern("z").call();
			assertEquals("[df
					"[z
					indexState(CONTENT));
			FileUtils.delete(new File(db.getWorkTree()
			writeTrashFile("df/a"
			writeTrashFile("df/b"
			git.add().addFilepattern("df/a").call();
			assertEquals("[df/a
					"[z
					indexState(CONTENT));
		}
	}

