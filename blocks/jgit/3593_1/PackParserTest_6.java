	@Test
	public void testPackWithTrailingGarbage() throws Exception {
		TestRepository d = new TestRepository(db);
		RevBlob a = d.blob("a");

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);
		packHeader(pack
		pack.write((Constants.OBJ_REF_DELTA) << 4 | 4);
		a.copyRawTo(pack);
		deflate(pack
		digest(pack);

		PackParser p = index(new UnionInputStream(
				new ByteArrayInputStream(pack.toByteArray())
				new ByteArrayInputStream(new byte[] { 0x7e })));
		p.setAllowThin(true);
		p.setCheckEofAfterPackFooter(true);
		try {
			p.parse(NullProgressMonitor.INSTANCE);
			fail("Pack with trailing garbage was accepted");
		} catch (IOException err) {
			assertEquals(
					MessageFormat.format(JGitText.get().expectedEOFReceived
					err.getMessage());
		}
	}

