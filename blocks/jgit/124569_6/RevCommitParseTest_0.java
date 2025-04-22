
	@Test
	public void testParse_gpgSig() throws Exception {
		String commit = "tree e3a1035abd2b319bb01e57d69b0ba6cab289297e\n" +
		"parent 54e895b87c0768d2317a2b17062e3ad9f76a8105\n" +
		"committer A U Thor <author@xample.com 1528968566 +0200\n" +
		"gpgsig -----BEGIN PGP SIGNATURE-----\n" +
		" \n" +
		" wsBcBAABCAAQBQJbGB4pCRBK7hj4Ov3rIwAAdHIIAENrvz23867ZgqrmyPemBEZP\n" +
		" U24B1Tlq/DWvce2buaxmbNQngKZ0pv2s8VMc11916WfTIC9EKvioatmpjduWvhqj\n" +
		" znQTFyiMor30pyYsfrqFuQZvqBW01o8GEWqLg8zjf9Rf0R3LlOEw86aT8CdHRlm6\n" +
		" wlb22xb8qoX4RB+LYfz7MhK5F+yLOPXZdJnAVbuyoMGRnDpwdzjL5Hj671+XJxN5\n" +
		" xXXyUpndEOmT0CIcKHrN/kbYoVL28OJaxoBuva3WYQaRrzEe3X02NMxZe9gkSqA=\n" +
		" =TClh\n" +
		" -----END PGP SIGNATURE-----\n" +
		"some other header\n\n" +
		"commit message";

		final RevCommit c;
		c = new RevCommit(id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		c.parseCanonical(new RevWalk(db)
		String gpgSig = new String(c.getRawGpgSignature()
		assertTrue(gpgSig.startsWith("-----BEGIN"));
		assertTrue(gpgSig.endsWith("END PGP SIGNATURE-----"));
	}

	@Test
	public void testParse_NoGpgSig() throws Exception {
		final RevCommit c = create("a message");
		assertNull(c.getRawGpgSignature());
	}
