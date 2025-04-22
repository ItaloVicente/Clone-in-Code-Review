		ObjectInserter.Formatter fmt = new ObjectInserter.Formatter();
		byte[] base = new byte[] { 'a' };
		ObjectId idA = fmt.idFor(Constants.OBJ_BLOB, base);
		ObjectId idB = fmt.idFor(Constants.OBJ_BLOB, new byte[] { 'b' });

		PackedObjectInfo a = new PackedObjectInfo(idA);
		PackedObjectInfo b = new PackedObjectInfo(idB);

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64 * 1024);
		packHeader(pack, 2);
		a.setOffset(pack.length());
		objectHeader(pack, Constants.OBJ_BLOB, base.length);
		deflate(pack, base);

		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		DeltaEncoder de = new DeltaEncoder(tmp, base.length, 3L << 30);
		de.copy(0, 1);
		byte[] delta = tmp.toByteArray();
		b.setOffset(pack.length());
		objectHeader(pack, Constants.OBJ_REF_DELTA, delta.length);
		idA.copyRawTo(pack);
		deflate(pack, delta);
		byte[] footer = digest(pack);

		File dir = new File(repo.getObjectDatabase().getDirectory(), "pack");
		File packName = new File(dir, idA.name() + ".pack");
		File idxName = new File(dir, idA.name() + ".idx");

		FileOutputStream f = new FileOutputStream(packName);
		try {
			f.write(pack.toByteArray());
		} finally {
			f.close();
		}

		f = new FileOutputStream(idxName);
		try {
			List<PackedObjectInfo> list = new ArrayList<PackedObjectInfo>();
			list.add(a);
			list.add(b);
			Collections.sort(list);
			new PackIndexWriterV1(f).write(list, footer);
		} finally {
			f.close();
		}

		PackFile packFile = new PackFile(packName, PackExt.INDEX.getBit());
		try {
			packFile.get(wc, b);
			fail("expected LargeObjectException.ExceedsByteArrayLimit");
		} catch (LargeObjectException.ExceedsByteArrayLimit bad) {
			assertNull(bad.getObjectId());
		} finally {
			packFile.close();
