	@Test
	public void testParse_incompleteAuthorAndCommitter() throws Exception {
		final StringBuilder b = new StringBuilder();
		b.append("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n");
		b.append("author <a_u_thor@example.com> 1218123387 +0700\n");
		b.append("committer <> 1218123390 -0500\n");

		final RevCommit c;
		c = new RevCommit(id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		c.parseCanonical(new RevWalk(db)

		assertEquals(new PersonIdent(""
		assertEquals(new PersonIdent(""
	}

