	@Test
	public void testParse_gpgSignature() throws Exception {
		final String signature = "-----BEGIN PGP SIGNATURE-----\n\n"
				+ "wsBcBAABCAAQBQJbGB4pCRBK7hj4Ov3rIwAAdHIIAENrvz23867ZgqrmyPemBEZP\n"
				+ "U24B1Tlq/DWvce2buaxmbNQngKZ0pv2s8VMc11916WfTIC9EKvioatmpjduWvhqj\n"
				+ "znQTFyiMor30pyYsfrqFuQZvqBW01o8GEWqLg8zjf9Rf0R3LlOEw86aT8CdHRlm6\n"
				+ "wlb22xb8qoX4RB+LYfz7MhK5F+yLOPXZdJnAVbuyoMGRnDpwdzjL5Hj671+XJxN5\n"
				+ "xXXyUpndEOmT0CIcKHrN/kbYoVL28OJaxoBuva3WYQaRrzEe3X02NMxZe9gkSqA=\n"
				+ "=TClh\n" + "-----END PGP SIGNATURE-----";
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		b.write("object 9788669ad918b6fcce64af8882fc9a81cb6aba67\n"
				.getBytes(UTF_8));
		b.write("type tree\n".getBytes(UTF_8));
		b.write("tag v1.0\n".getBytes(UTF_8));
		b.write("tagger t <t@example.com> 1218123387 +0700\n".getBytes(UTF_8));
		b.write('\n');
		b.write("message\n\n".getBytes(UTF_8));
		b.write(signature.getBytes(US_ASCII));
		b.write('\n');

		RevTag t = new RevTag(id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		try (RevWalk rw = new RevWalk(db)) {
			t.parseCanonical(rw
		}

		assertEquals("t"
		assertEquals("message"
		assertEquals("message\n\n" + signature + '\n'
		String gpgSig = new String(t.getRawGpgSignature()
		assertTrue(gpgSig.startsWith("-----BEGIN"));
		assertTrue(gpgSig.endsWith("END PGP SIGNATURE-----"));
	}

