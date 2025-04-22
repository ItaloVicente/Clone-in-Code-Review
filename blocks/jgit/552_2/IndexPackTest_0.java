
	public void testTinyThinPack() throws Exception {
		TestRepository d = new TestRepository(db);
		RevBlob a = d.blob("a");

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);

		packHeader(pack

		pack.write((Constants.OBJ_REF_DELTA) << 4 | 4);
		a.copyRawTo(pack);
		deflate(pack

		digest(pack);

		final byte[] raw = pack.toByteArray();
		IndexPack ip = IndexPack.create(db
		ip.setFixThin(true);
		ip.index(NullProgressMonitor.INSTANCE);
		ip.renameAndOpenPack();
	}

	private void packHeader(TemporaryBuffer.Heap tinyPack
			throws IOException {
		final byte[] hdr = new byte[8];
		NB.encodeInt32(hdr
		NB.encodeInt32(hdr

		tinyPack.write(Constants.PACK_SIGNATURE);
		tinyPack.write(hdr
	}

	private void deflate(TemporaryBuffer.Heap tinyPack
			throws IOException {
		final Deflater deflater = new Deflater();
		final byte[] buf = new byte[128];
		deflater.setInput(content
		deflater.finish();
		do {
			final int n = deflater.deflate(buf
			if (n > 0)
				tinyPack.write(buf
		} while (!deflater.finished());
	}

	private void digest(TemporaryBuffer.Heap buf) throws IOException {
		MessageDigest md = Constants.newMessageDigest();
		md.update(buf.toByteArray());
		buf.write(md.digest());
	}
