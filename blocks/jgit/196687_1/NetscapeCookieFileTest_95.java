	@Test
	void testWriteWhileSomeoneIsHoldingTheLock() {
		assertThrows(IOException.class
			try (InputStream input = this.getClass()
					.getResourceAsStream("cookies-simple1.txt")) {
				Files.copy(input
			}
			NetscapeCookieFile cookieFile = new NetscapeCookieFile(tmpFile);
			LockFile lockFile = new LockFile(tmpFile.toFile());
			try {
				assertTrue(lockFile.lock()
				cookieFile.write(baseUrl);
			} finally {
				lockFile.unlock();
			}
		});
