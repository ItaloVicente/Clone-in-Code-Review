	@Test(expected = IOException.class)
	public void testWriteWhileSomeoneIsHoldingTheLock()
			throws IllegalArgumentException, IOException, InterruptedException {
		try (InputStream input = this.getClass()
				.getResourceAsStream("cookies-simple1.txt")) {
			Files.copy(input, tmpFile, StandardCopyOption.REPLACE_EXISTING);
		}
		NetscapeCookieFile cookieFile = new NetscapeCookieFile(tmpFile);
		LockFile lockFile = new LockFile(tmpFile.toFile());
		try {
			assertTrue("Could not acquire lock", lockFile.lock());
			cookieFile.write(baseUrl);
		} finally {
			lockFile.unlock();
		}
