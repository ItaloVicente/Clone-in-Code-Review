	public void testParseOldStyleNoTagger() throws Exception {
		final ObjectId treeId = id("9788669ad918b6fcce64af8882fc9a81cb6aba67");
		final String name = "v1.2.3.4.5";
				+ "-----END PGP SIGNATURE------n";

		final StringBuilder body = new StringBuilder();

		body.append("object ");
		body.append(treeId.name());
		body.append("\n");

		body.append("type tree\n");

		body.append("tag ");
		body.append(name);
		body.append("\n");
		body.append("\n");
		body.append(message);

		final RevWalk rw = new RevWalk(db);
		final RevTag c;

		c = new RevTag(id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		assertNull(c.getObject());
		assertNull(c.getTagName());

		c.parseCanonical(rw
		assertNotNull(c.getObject());
		assertEquals(treeId
		assertSame(rw.lookupTree(treeId)

		assertNotNull(c.getTagName());
		assertEquals(name
		assertEquals("test"
		assertEquals(message

		assertNull(c.getTaggerIdent());
	}

