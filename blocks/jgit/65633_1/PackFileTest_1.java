		try (ObjectInserter.Formatter fmt = new ObjectInserter.Formatter()) {
			byte[] base = new byte[] { 'a' };
			ObjectId idA = fmt.idFor(Constants.OBJ_BLOB
			ObjectId idB = fmt.idFor(Constants.OBJ_BLOB

			PackedObjectInfo a = new PackedObjectInfo(idA);
			PackedObjectInfo b = new PackedObjectInfo(idB);

			TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64 * 1024);
			packHeader(pack
			a.setOffset(pack.length());
			objectHeader(pack
			deflate(pack

			ByteArrayOutputStream tmp = new ByteArrayOutputStream();
			DeltaEncoder de = new DeltaEncoder(tmp
			de.copy(0
			byte[] delta = tmp.toByteArray();
			b.setOffset(pack.length());
			objectHeader(pack
			idA.copyRawTo(pack);
			deflate(pack
			byte[] footer = digest(pack);

			File dir = new File(repo.getObjectDatabase().getDirectory()
					"pack");
			File packName = new File(dir
			File idxName = new File(dir

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
				new PackIndexWriterV1(f).write(list
			} finally {
				f.close();
			}

			PackFile packFile = new PackFile(packName
			try {
				packFile.get(wc
				fail("expected LargeObjectException.ExceedsByteArrayLimit");
			} catch (LargeObjectException.ExceedsByteArrayLimit bad) {
				assertNull(bad.getObjectId());
			} finally {
				packFile.close();
			}
