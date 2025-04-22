
	@Test
	public void testCheckoutOrphanBranch() throws Exception {
		CheckoutCommand co = git.checkout().setOrphan(true)
				.setName("orphanbranch");
		Ref ref = co.call();
		assertNotNull(ref);
		assertEquals("refs/heads/orphanbranch"

		File HEAD = new File(trash
		String headRef = read(HEAD);
		assertEquals("ref: refs/heads/orphanbranch\n"
		assertEquals(2

		File heads = new File(trash
		assertEquals(2

		this.assertNoHead();
		this.assertStatus(1);
		assertEquals(CheckoutResult.NOT_TRIED_RESULT
	}

	private void assertNoHead() throws GitAPIException {
		try {
			this.git.log().call();
			fail();
		} catch (NoHeadException e) {
		}
	}

	private void assertStatus(int files) throws GitAPIException {
		org.eclipse.jgit.api.Status status = this.git.status().call();
		assertFalse(status.isClean());
		assertEquals(files
	}

	@Test
	public void testCreateOrphanBranchWithStartCommit() throws Exception {
		CheckoutCommand co = git.checkout().setOrphan(true)
				.setName("orphanbranch");
		co.setStartPoint(initialCommit).call();
		assertEquals(2
		this.assertNoHead();
		this.assertStatus(1);
	}

	@Test
	public void testCreateOrphanBranchWithStartPoint() throws Exception {
		CheckoutCommand co = git.checkout().setOrphan(true)
				.setName("orphanbranch");
		co.setStartPoint("HEAD^").call();
		assertEquals(2
		this.assertNoHead();
		this.assertStatus(1);
	}

	@Test
	public void testInvalidRefName() throws Exception {
		try {
			git.checkout().setOrphan(true).setName("../invalidname").call();
			fail("Should have failed");
		} catch (InvalidRefNameException e) {
		}
	}

	@Test
	public void testNullRefName() throws Exception {
		try {
			git.checkout().setOrphan(true).setName(null).call();
			fail("Should have failed");
		} catch (InvalidRefNameException e) {
		}
	}

	@Test
	public void testAlreadyExists() throws Exception {
		this.git.checkout().setCreateBranch(true).setName("orphanbranch")
				.call();
		this.git.checkout().setName("master").call();

		try {
			git.checkout().setOrphan(true).setName("orphanbranch").call();
			fail("Should have failed");
		} catch (RefAlreadyExistsException e) {
		}
	}

