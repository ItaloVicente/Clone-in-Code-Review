	@Test
	public void testReplaceFileWithDirectory()
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
	public void testReplaceDirectoryWithFile()
			throws IOException
		try (Git git = new Git(db)) {
			writeTrashFile("df/f"
			git.add().addFilepattern("df").call();
			assertEquals("[df/f
					indexState(CONTENT));
			FileUtils.delete(new File(db.getWorkTree()
			writeTrashFile("df"
			git.add().addFilepattern("df").call();
			assertEquals("[df
					indexState(CONTENT));
		}
	}

	@Test
	public void testReplaceFileByPartOfDirectory()
			throws IOException
		try (Git git = new Git(db)) {
			writeTrashFile("src/main"
			writeTrashFile("src/main"
			writeTrashFile("z"
			git.add().addFilepattern("src/main/df")
				.addFilepattern("src/main/z")
				.addFilepattern("z")
				.call();
			assertEquals(
					"[src/main/df
					"[src/main/z
					"[z
					indexState(CONTENT));
			FileUtils.delete(new File(db.getWorkTree()
			writeTrashFile("src/main/df"
			writeTrashFile("src/main/df"
			git.add().addFilepattern("src/main/df/a").call();
			assertEquals(
					"[src/main/df/a
					"[src/main/z
					"[z
					indexState(CONTENT));
		}
	}

	@Test
	public void testReplaceDirectoryConflictsWithFile()
			throws IOException
		DirCache dc = db.lockDirCache();
		try (ObjectInserter oi = db.newObjectInserter()) {
			DirCacheBuilder builder = dc.builder();
			File f = writeTrashFile("a"
			addEntryToBuilder("a"

			f = writeTrashFile("a"
			addEntryToBuilder("a/df"

			f = writeTrashFile("a"
			addEntryToBuilder("a/df"

			f = writeTrashFile("z"
			addEntryToBuilder("z"
			builder.commit();
		}
		assertEquals(
				"[a
				"[a/df
				"[a/df
				"[z
				indexState(CONTENT));

		try (Git git = new Git(db)) {
			FileUtils.delete(new File(db.getWorkTree()
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			assertEquals("[a
					"[z
					indexState(CONTENT));
		}
	}

