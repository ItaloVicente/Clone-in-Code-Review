	@Test
	void testWrongSh() {
		assertThrows(IOException.class
			File script = writeTempFile("cat -");
			FS.DETECTED.runProcess(
					new ProcessBuilder("/bin/sh-foo"
							"c")
		});
