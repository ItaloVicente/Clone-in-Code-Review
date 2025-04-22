	private void checkoutLineEndings(String inIndex
			String attributes) throws Exception {
		try (Git git = new Git(db);
				TestRepository<Repository> db_t = new TestRepository<>(db)) {
			BranchBuilder master = db_t.branch("master");
			master.commit().add("f"
			if (!StringUtils.isEmptyOrNull(attributes)) {
				master.commit().add(".gitattributes"
						.message("attributes").create();
			}
			File f = new File(db.getWorkTree()
			assertFalse(f.exists());
			git.checkout().setName("master").call();
			assertTrue(f.exists());
			checkFile(f
		}
	}

	@Test
	public void testCheckoutWithCRLF() throws Exception {
		checkoutLineEndings("first line\r\nsecond line\r\n"
				"first line\r\nsecond line\r\n"
	}

	@Test
	public void testCheckoutWithCRLFAuto() throws Exception {
		checkoutLineEndings("first line\r\nsecond line\r\n"
				"first line\r\nsecond line\r\n"
	}

	@Test
	public void testCheckoutWithCRLFAutoEolLf() throws Exception {
		checkoutLineEndings("first line\r\nsecond line\r\n"
				"first line\r\nsecond line\r\n"
	}

	@Test
	public void testCheckoutWithCRLFAutoEolNative() throws Exception {
		checkoutLineEndings("first line\r\nsecond line\r\n"
				"first line\r\nsecond line\r\n"
	}

	@Test
	public void testCheckoutWithCRLFAutoEolCrLf() throws Exception {
		checkoutLineEndings("first line\r\nsecond line\r\n"
				"first line\r\nsecond line\r\n"
	}

	@Test
	public void testCheckoutWithLF() throws Exception {
		checkoutLineEndings("first line\nsecond line\n"
				"first line\nsecond line\n"
	}

	@Test
	public void testCheckoutWithLFAuto() throws Exception {
		checkoutLineEndings("first line\nsecond line\n"
				"first line\nsecond line\n"
	}

	@Test
	public void testCheckoutWithLFAutoEolLf() throws Exception {
		checkoutLineEndings("first line\nsecond line\n"
				"first line\nsecond line\n"
	}

	@Test
	public void testCheckoutWithLFAutoEolNative() throws Exception {
		checkoutLineEndings(
				"first line\nsecond line\n"
						.replaceAll("\n"
				"f text=auto eol=native");
	}

	@Test
	public void testCheckoutWithLFAutoEolCrLf() throws Exception {
		checkoutLineEndings("first line\nsecond line\n"
				"first line\r\nsecond line\r\n"
	}

