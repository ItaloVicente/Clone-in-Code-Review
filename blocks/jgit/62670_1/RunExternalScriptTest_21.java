	@Test
	public void testCopyStdInExecute()
			throws IOException
		String inputStr = "a\nb\rc\r\nd";
		File script = writeTempFile("cat -");
		ProcessBuilder pb = new ProcessBuilder("sh"
		ExecutionResult res = FS.DETECTED.execute(pb
				new ByteArrayInputStream(inputStr.getBytes()));
		assertEquals(0
		assertEquals(inputStr
		assertEquals(""
	}

	@Test
	public void testStdErrExecute() throws IOException
		File script = writeTempFile("echo hi >&2");
		ProcessBuilder pb = new ProcessBuilder("sh"
		ExecutionResult res = FS.DETECTED.execute(pb
		assertEquals(0
		assertEquals(""
		assertEquals("hi" + LF
	}

	@Test
	public void testAllTogetherBinExecute()
			throws IOException
		String inputStr = "a\nb\rc\r\nd";
		File script = writeTempFile(
				"echo $#
		ProcessBuilder pb = new ProcessBuilder("sh"
				"b"
		ExecutionResult res = FS.DETECTED.execute(pb
				new ByteArrayInputStream(inputStr.getBytes()));
		assertEquals(5
		assertEquals(inputStr
		assertEquals("3
