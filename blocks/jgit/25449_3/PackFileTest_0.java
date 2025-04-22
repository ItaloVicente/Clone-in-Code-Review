		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		DeltaEncoder de = new DeltaEncoder(tmp
		de.copy(0
		byte[] delta = tmp.toByteArray();
		b.setOffset(pack.length());
		objectHeader(pack
		idA.copyRawTo(pack);
		deflate(pack
		byte[] footer = digest(pack);

		File packName = new File(new File(
				((FileObjectDatabase) repo.getObjectDatabase()).getDirectory()
				"pack")
		File idxName = new File(new File(
				((FileObjectDatabase) repo.getObjectDatabase()).getDirectory()
				"pack")

		FileOutputStream f = new FileOutputStream(packName);
		try {
			f.write(pack.toByteArray());
		} finally {
			f.close();
		}
