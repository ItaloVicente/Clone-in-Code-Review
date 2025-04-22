	@Test
	public void testMaxObjectSizeFullBlob() throws Exception {
		TestRepository d = new TestRepository(db);
		final byte[] data = Constants.encode("0123456789");
		RevBlob a = d.blob(data);

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
		} catch (IOException e) {
			assertTrue(e.getMessage().contains(a.getName()));
		}
	}

	@Test
	public void testMaxObjectSizeThinPack() throws Exception {
		TestRepository d = new TestRepository(db);
		final byte[] data = new byte[] { '0'
				'7'
		RevBlob a = d.blob(data);

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);

		packHeader(pack
		pack.write((Constants.OBJ_REF_DELTA) << 4 | 14);
		a.copyRawTo(pack);
		deflate(pack
				'5'
		digest(pack);

		PackParser p = index(new ByteArrayInputStream(pack.toByteArray()));
		p.setAllowThin(true);
		p.setMaxObjectSizeLimit(12);
		p.parse(NullProgressMonitor.INSTANCE);

		p = index(new ByteArrayInputStream(pack.toByteArray()));
		p.setAllowThin(true);
		p.setMaxObjectSizeLimit(11);
		p.parse(NullProgressMonitor.INSTANCE);

		p = index(new ByteArrayInputStream(pack.toByteArray()));
		p.setAllowThin(true);
		p.setMaxObjectSizeLimit(10);
		try {
			p.parse(NullProgressMonitor.INSTANCE);
			fail("PackParser should have failed");
		} catch (IOException e) {
			MessageDigest md = Constants.newMessageDigest();
			md.update(Constants.encode("blob 11"));
			md.update((byte) 0);
			md.update(Constants.encode("a0123456789"));
			assertTrue(e.getMessage().contains(
					ObjectId.fromRaw(md.digest()).name()));
		}
	}

