
	@Test
	public void testCheckoutOrphanBranch() throws Exception {
		CheckoutCommand co = newOrphanBranchCommand();
		assertCheckoutRef(co.call());

		File HEAD = new File(trash
		String headRef = read(HEAD);
		assertEquals("ref: refs/heads/orphanbranch\n"
		assertEquals(2

		File heads = new File(trash
		assertEquals(2

		this.assertNoHead();
		this.assertRepositoryCondition(1);
		assertEquals(CheckoutResult.NOT_TRIED_RESULT
	}

	private CheckoutCommand newOrphanBranchCommand() {
		return git.checkout().setOrphan(true)
				.setName("orphanbranch");
	}

	private static void assertCheckoutRef(Ref ref) {
		assertNotNull(ref);
		assertEquals("refs/heads/orphanbranch"
	}

	private void assertNoHead() throws IOException {
		assertNull(db.resolve("HEAD"));
	}

	private void assertRepositoryCondition(int files) throws GitAPIException {
		org.eclipse.jgit.api.Status status = this.git.status().call();
		assertFalse(status.isClean());
		assertEquals(files
	}

	@Test
	public void testCreateOrphanBranchWithStartCommit() throws Exception {
		CheckoutCommand co = newOrphanBranchCommand();
		Ref ref = co.setStartPoint(initialCommit).call();
		assertCheckoutRef(ref);
		assertEquals(2
		this.assertNoHead();
		this.assertRepositoryCondition(1);
	}

	@Test
	public void testCreateOrphanBranchWithStartPoint() throws Exception {
		CheckoutCommand co = newOrphanBranchCommand();
		Ref ref = co.setStartPoint("HEAD^").call();
		assertCheckoutRef(ref);

		assertEquals(2
		this.assertNoHead();
		this.assertRepositoryCondition(1);
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
			newOrphanBranchCommand().call();
			fail("Should have failed");
		} catch (RefAlreadyExistsException e) {
		}
	}

