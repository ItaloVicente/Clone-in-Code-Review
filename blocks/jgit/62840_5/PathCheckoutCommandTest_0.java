	@Test
	public void testUpdateSymLink() throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());

		Path path = writeLink(LINK
		git.add().addFilepattern(LINK).call();
		git.commit().setMessage("Added link").call();
		assertEquals("3"

		writeLink(LINK
		assertEquals("c"

		CheckoutCommand co = git.checkout();
		co.addPath(LINK).call();

		assertEquals("3"
	}

	@Test
	public void testUpdateBrokenSymLinkToDirectory() throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());

		Path path = writeLink(LINK
		git.add().addFilepattern(LINK).call();
		git.commit().setMessage("Added link").call();
		assertEquals("f"
		assertTrue(path.toFile().exists());

		writeLink(LINK
		assertFalse(path.toFile().exists());
		assertEquals("link_to_nowhere"

		CheckoutCommand co = git.checkout();
		co.addPath(LINK).call();

		assertEquals("f"
	}

	@Test
	public void testUpdateBrokenSymLink() throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());

		Path path = writeLink(LINK
		git.add().addFilepattern(LINK).call();
		git.commit().setMessage("Added link").call();
		assertEquals("3"
		assertEquals(FILE1

		writeLink(LINK
		assertFalse(path.toFile().exists());
		assertEquals("link_to_nowhere"

		CheckoutCommand co = git.checkout();
		co.addPath(LINK).call();

		assertEquals("3"
	}

