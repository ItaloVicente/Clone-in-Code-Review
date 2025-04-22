	@Test
	public void testParse_illegalEncoding() throws Exception {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		b.write("object 9788669ad918b6fcce64af8882fc9a81cb6aba67\n".getBytes(UTF_8));
		b.write("type tree\n".getBytes(UTF_8));
		b.write("tag v1.0\n".getBytes(UTF_8));
		b.write("tagger t <t@example.com> 1218123387 +0700\n".getBytes(UTF_8));
		b.write("encoding utf-8logoutputencoding=gbk\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("message\n".getBytes(UTF_8));

		RevTag t = new RevTag(id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		t.parseCanonical(new RevWalk(db)

		assertEquals("t"
		assertEquals("message"
		assertEquals("message\n"
	}

	@Test
	public void testParse_unsupportedEncoding() throws Exception {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		b.write("object 9788669ad918b6fcce64af8882fc9a81cb6aba67\n".getBytes(UTF_8));
		b.write("type tree\n".getBytes(UTF_8));
		b.write("tag v1.0\n".getBytes(UTF_8));
		b.write("tagger t <t@example.com> 1218123387 +0700\n".getBytes(UTF_8));
		b.write("encoding it_IT.UTF8\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("message\n".getBytes(UTF_8));

		RevTag t = new RevTag(id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		t.parseCanonical(new RevWalk(db)

		assertEquals("t"
		assertEquals("message"
		assertEquals("message\n"
	}

