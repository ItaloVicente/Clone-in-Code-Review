	@Test
	public void invalidNames() throws AmbiguousObjectException
		assertTrue(Repository.isValidRefName("x/a"));
		assertTrue(Repository.isValidRefName("x/a.b"));
		assertTrue(Repository.isValidRefName("x/a@b"));
		assertTrue(Repository.isValidRefName("x/a@b{x}"));
		assertTrue(Repository.isValidRefName("x/a/b"));
		assertFalse(Repository.isValidRefName("x/.a"));
		assertFalse(Repository.isValidRefName("x/a."));
		assertFalse(Repository.isValidRefName("x/a..b"));
		assertFalse(Repository.isValidRefName("x/a/"));
		assertFalse(Repository.isValidRefName("x/a[b"));
		assertFalse(Repository.isValidRefName("x/a^b"));
		assertFalse(Repository.isValidRefName("x/a*b"));
		assertFalse(Repository.isValidRefName("x/a?b"));
		assertFalse(Repository.isValidRefName("x/a~1"));
		assertFalse(Repository.isValidRefName("x/a\\b"));
		assertFalse(Repository.isValidRefName("x/a\u0000"));

		assertUnparseable(".");
		assertUnparseable("x@{3");
		assertUnparseable("x[b");
		assertUnparseable("x y");
		assertUnparseable("x.");
		assertUnparseable(".x");
		assertUnparseable("a..b");
		assertUnparseable("x\\b");
		assertUnparseable("a~b");
		assertUnparseable("a^b");
		assertUnparseable("a\u0000");
	}

	private void assertUnparseable(String s) throws AmbiguousObjectException
			IOException {
		try {
			db.resolve(s);
			fail("'" + s + "' should be unparseable");
		} catch (RevisionSyntaxException e) {
		}
	}

