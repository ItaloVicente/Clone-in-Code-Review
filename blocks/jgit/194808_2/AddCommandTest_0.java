	@Test
	public void testAddLink() throws IOException
		assumeTrue(db.getFS().supportsSymlinks());
		try (Git git = new Git(db)) {
			writeTrashFile("a.txt"
			File link = new File(db.getWorkTree()
			db.getFS().createSymLink(link
			git.add().addFilepattern(".").call();
			assertEquals(
					"[a.txt
					indexState(CONTENT));
			git.commit().setMessage("link").call();
			StoredConfig config = db.getConfig();
			config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SYMLINKS
			config.save();
			Files.delete(link.toPath());
			git.reset().setMode(ResetType.HARD).call();
			assertTrue(Files.isRegularFile(link.toPath()));
			assertEquals(
					"[a.txt
					indexState(CONTENT));
			writeTrashFile("link"
			git.add().addFilepattern("link").call();
			assertEquals(
					"[a.txt
					indexState(CONTENT));
			config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SYMLINKS
			config.save();
			git.add().addFilepattern("link").call();
			assertEquals(
					"[a.txt
					indexState(CONTENT));
		}
	}

