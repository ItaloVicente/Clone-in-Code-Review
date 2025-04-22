	@Test
	public void testMultiFileNoNewline() throws Exception {
		try (Git git = new Git(db)) {
			Files.write(db.getWorkTree().toPath().resolve("yello")
					"yello".getBytes(StandardCharsets.US_ASCII));
			git.add().addFilepattern("yello").call();
			git.commit().setMessage("yello").call();
		}
		checkBinary("hello"
	}

