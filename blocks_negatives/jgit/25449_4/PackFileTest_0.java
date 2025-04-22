		byte[] data1 = clone(0x01, data0);
		byte[] delta1 = delta(data0, data1);
		ObjectId id1 = fmt.idFor(Constants.OBJ_BLOB, data1);
		objectHeader(pack, Constants.OBJ_REF_DELTA, delta1.length);
		id0.copyRawTo(pack);
		deflate(pack, delta1);

		byte[] data2 = clone(0x02, data1);
		byte[] delta2 = delta(data1, data2);
		ObjectId id2 = fmt.idFor(Constants.OBJ_BLOB, data2);
		objectHeader(pack, Constants.OBJ_REF_DELTA, delta2.length);
		id1.copyRawTo(pack);
		deflate(pack, delta2);

		byte[] data3 = clone(0x03, data2);
		byte[] delta3 = delta(data2, data3);
		ObjectId id3 = fmt.idFor(Constants.OBJ_BLOB, data3);
		objectHeader(pack, Constants.OBJ_REF_DELTA, delta3.length);
		id2.copyRawTo(pack);
		deflate(pack, delta3);

		digest(pack);
		PackParser ip = index(pack.toByteArray());
		ip.setAllowThin(true);
		ip.parse(NullProgressMonitor.INSTANCE);
