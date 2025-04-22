	@Test
	public void testNonMarkingInputStream() throws Exception {
		TestRepository d = new TestRepository(db);
		RevBlob a = d.blob("a");

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);
		packHeader(pack
		pack.write((Constants.OBJ_REF_DELTA) << 4 | 4);
		a.copyRawTo(pack);
		deflate(pack
		digest(pack);

		InputStream in = new ByteArrayInputStream(pack.toByteArray()) {
			@Override
			public boolean markSupported() {
				return false;
			}

			@Override
			public void mark(int maxlength) {
				fail("Mark should not be called");
			}
		};

		PackParser p = index(in);
		p.setAllowThin(true);
		p.setCheckEofAfterPackFooter(false);
		p.setExpectDataAfterPackFooter(true);

		try {
			p.parse(NullProgressMonitor.INSTANCE);
			fail("PackParser should have failed");
		} catch (IOException e) {
			assertEquals(e.getMessage()
					JGitText.get().inputStreamMustSupportMark);
		}
	}

	@Test
	public void testDataAfterPackFooterSingleRead() throws Exception {
		TestRepository d = new TestRepository(db);
		RevBlob a = d.blob("a");

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(32*1024);
		packHeader(pack
		pack.write((Constants.OBJ_REF_DELTA) << 4 | 4);
		a.copyRawTo(pack);
		deflate(pack
		digest(pack);

		byte packData[] = pack.toByteArray();
		byte streamData[] = new byte[packData.length + 1];
		System.arraycopy(packData
		streamData[packData.length] = 0x7e;

		InputStream in = new ByteArrayInputStream(streamData);
		PackParser p = index(in);
		p.setAllowThin(true);
		p.setCheckEofAfterPackFooter(false);
		p.setExpectDataAfterPackFooter(true);

		p.parse(NullProgressMonitor.INSTANCE);

		assertEquals(0x7e
	}

	@Test
	public void testDataAfterPackFooterSplitObjectRead() throws Exception {
		final byte[] data = Constants.encode("0123456789");

		int objects = 900;
		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(32 * 1024);
		packHeader(pack

		for (int i = 0; i < objects; i++) {
			pack.write((Constants.OBJ_BLOB) << 4 | 10);
			deflate(pack
		}
		digest(pack);

		byte packData[] = pack.toByteArray();
		byte streamData[] = new byte[packData.length + 1];
		System.arraycopy(packData
		streamData[packData.length] = 0x7e;
		InputStream in = new ByteArrayInputStream(streamData);
		PackParser p = index(in);
		p.setAllowThin(true);
		p.setCheckEofAfterPackFooter(false);
		p.setExpectDataAfterPackFooter(true);

		p.parse(NullProgressMonitor.INSTANCE);

		assertEquals(0x7e
	}

	@Test
	public void testDataAfterPackFooterSplitHeaderRead() throws Exception {
		TestRepository d = new TestRepository(db);
		final byte[] data = Constants.encode("a");
		RevBlob b = d.blob(data);

		int objects = 248;
		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(32 * 1024);
		packHeader(pack
		int offset = 13;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < offset; i++)
			sb.append(i);
		offset = sb.toString().length();
		int lenByte = (Constants.OBJ_BLOB) << 4 | (offset & 0x0F);
		offset >>= 4;
		if (offset > 0)
			lenByte |= 1 << 7;
		pack.write(lenByte);
		while (offset > 0) {
			lenByte = offset & 0x7F;
			offset >>= 6;
			if (offset > 0)
				lenByte |= 1 << 7;
			pack.write(lenByte);
		}
		deflate(pack

		for (int i = 0; i < objects; i++) {
			pack.write((Constants.OBJ_REF_DELTA) << 4 | 4);
			b.copyRawTo(pack);
			deflate(pack
		}
		digest(pack);

		byte packData[] = pack.toByteArray();
		byte streamData[] = new byte[packData.length + 1];
		System.arraycopy(packData
		streamData[packData.length] = 0x7e;
		InputStream in = new ByteArrayInputStream(streamData);
		PackParser p = index(in);
		p.setAllowThin(true);
		p.setCheckEofAfterPackFooter(false);
		p.setExpectDataAfterPackFooter(true);

		p.parse(NullProgressMonitor.INSTANCE);

		assertEquals(0x7e
	}

