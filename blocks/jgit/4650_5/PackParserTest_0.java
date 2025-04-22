	@Test
	public void testMaxObjectSizeFullBlob() throws Exception {
		TestRepository d = new TestRepository(db);
		final byte[] data = Constants.encode("0123456789");
		d.blob(data);

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);

		packHeader(pack
		pack.write((Constants.OBJ_BLOB) << 4 | 10);
		deflate(pack
		digest(pack);

		PackParser p = index(new ByteArrayInputStream(pack.toByteArray()));
		p.setMaxObjectSizeLimit(11);
		p.parse(NullProgressMonitor.INSTANCE);

		p = index(new ByteArrayInputStream(pack.toByteArray()));
		p.setMaxObjectSizeLimit(10);
		p.parse(NullProgressMonitor.INSTANCE);

		p = index(new ByteArrayInputStream(pack.toByteArray()));
		p.setMaxObjectSizeLimit(9);
		try {
			p.parse(NullProgressMonitor.INSTANCE);
			fail("PackParser should have failed");
		} catch (TooLargeObjectInPackException e) {
		}
	}

	@Test
	public void testMaxObjectSizeDeltaBlock() throws Exception {
		TestRepository d = new TestRepository(db);
		RevBlob a = d.blob("a");

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);

		packHeader(pack
		pack.write((Constants.OBJ_REF_DELTA) << 4 | 14);
		a.copyRawTo(pack);
		deflate(pack
				'5'
		digest(pack);

		PackParser p = index(new ByteArrayInputStream(pack.toByteArray()));
		p.setAllowThin(true);
		p.setMaxObjectSizeLimit(14);
		p.parse(NullProgressMonitor.INSTANCE);

		p = index(new ByteArrayInputStream(pack.toByteArray()));
		p.setAllowThin(true);
		p.setMaxObjectSizeLimit(13);
		try {
			p.parse(NullProgressMonitor.INSTANCE);
			fail("PackParser should have failed");
		} catch (TooLargeObjectInPackException e) {
		}
	}

	@Test
	public void testMaxObjectSizeDeltaResultSize() throws Exception {
		TestRepository d = new TestRepository(db);
		RevBlob a = d.blob("0123456789");

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);

		packHeader(pack
		pack.write((Constants.OBJ_REF_DELTA) << 4 | 4);
		a.copyRawTo(pack);
		deflate(pack
		digest(pack);

		PackParser p = index(new ByteArrayInputStream(pack.toByteArray()));
		p.setAllowThin(true);
		p.setMaxObjectSizeLimit(11);
		p.parse(NullProgressMonitor.INSTANCE);

		p = index(new ByteArrayInputStream(pack.toByteArray()));
		p.setAllowThin(true);
		p.setMaxObjectSizeLimit(10);
		try {
			p.parse(NullProgressMonitor.INSTANCE);
			fail("PackParser should have failed");
		} catch (TooLargeObjectInPackException e) {
		}
	}

