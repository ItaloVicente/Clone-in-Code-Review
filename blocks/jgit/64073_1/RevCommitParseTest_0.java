	@Test
	public void testParse_incorrectUtf8Name() throws Exception {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		b.write("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n"
				.getBytes(UTF_8));
		b.write("author au <a@example.com> 1218123387 +0700\n".getBytes(UTF_8));
		b.write("committer co <c@example.com> 1218123390 -0500\n"
				.getBytes(UTF_8));
		b.write("encoding 'utf8'\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("Sm\u00f6rg\u00e5sbord\n".getBytes(UTF_8));

		RevCommit c = new RevCommit(
				id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		c.parseCanonical(new RevWalk(db)
		assertEquals("'utf8'"
		assertEquals("Sm\u00f6rg\u00e5sbord\n"

		try {
			c.getEncoding();
			fail("Expected " + IllegalCharsetNameException.class);
		} catch (IllegalCharsetNameException badName) {
			assertEquals("'utf8'"
		}
	}

	@Test
	public void testParse_illegalEncoding() throws Exception {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		b.write("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n".getBytes(UTF_8));
		b.write("author au <a@example.com> 1218123387 +0700\n".getBytes(UTF_8));
		b.write("committer co <c@example.com> 1218123390 -0500\n".getBytes(UTF_8));
		b.write("encoding utf-8logoutputencoding=gbk\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("message\n".getBytes(UTF_8));

		RevCommit c = new RevCommit(
				id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		c.parseCanonical(new RevWalk(db)
		assertEquals("utf-8logoutputencoding=gbk"
		assertEquals("message\n"
		assertEquals("message"
		assertTrue(c.getFooterLines().isEmpty());
		assertEquals("au"

		try {
			c.getEncoding();
			fail("Expected " + IllegalCharsetNameException.class);
		} catch (IllegalCharsetNameException badName) {
			assertEquals("utf-8logoutputencoding=gbk"
		}
	}

	@Test
	public void testParse_unsupportedEncoding() throws Exception {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		b.write("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n".getBytes(UTF_8));
		b.write("author au <a@example.com> 1218123387 +0700\n".getBytes(UTF_8));
		b.write("committer co <c@example.com> 1218123390 -0500\n".getBytes(UTF_8));
		b.write("encoding it_IT.UTF8\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("message\n".getBytes(UTF_8));

		RevCommit c = new RevCommit(
				id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		c.parseCanonical(new RevWalk(db)
		assertEquals("it_IT.UTF8"
		assertEquals("message\n"
		assertEquals("message"
		assertTrue(c.getFooterLines().isEmpty());
		assertEquals("au"

		try {
			c.getEncoding();
			fail("Expected " + UnsupportedCharsetException.class);
		} catch (UnsupportedCharsetException badName) {
			assertEquals("it_IT.UTF8"
		}
	}

