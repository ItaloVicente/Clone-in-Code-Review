
	@Test
	public void testDiffCommitModifiedFileRaw() throws Exception {
		writeTrashFile("a"
		writeTrashFile("b"
		String result = toString(execute("git diff --raw"));
		assertEquals(toString(
						":100644 100644 2e65efe2a 59ef8d134 M\ta"
						":000000 100644 000000000 63d8dbd40 A\tb")
				result);
	}

	@Test
	public void testDiffCommitModifiedFileRawNoAbbrev() throws Exception {
		writeTrashFile("a"
		writeTrashFile("b"
		String result = toString(execute("git diff --raw --no-abbrev"));
		assertEquals(toString(
				":100644 100644 2e65efe2a145dda7ee51d1741299f848e5bf752e " +
						"59ef8d134f97de87ebcac8e3a0c32d78c81e842e M\ta"
				":000000 100644 0000000000000000000000000000000000000000 " +
						"63d8dbd40c23542e740659a7168a0ce3138ea748 A\tb")
				result);
	}
