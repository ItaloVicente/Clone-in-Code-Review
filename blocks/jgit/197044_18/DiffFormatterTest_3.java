	@Test
	public void testDiff_BinaryAsText() throws Exception {
		ObjectId adId = blob("a\nd\n");
		ObjectId binId = blob("a\nb\nc\n\0\0\0\0d\n");

		String diffHeader = makeDiffHeaderAsText(PATH_A
				+ "@@ -1

		DiffEntry ad = DiffEntry.delete(PATH_A
		DiffEntry abcd = DiffEntry.add(PATH_B

		DiffEntry mod = DiffEntry.pair(ChangeType.MODIFY

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DiffFormatter fmt = new DiffFormatter(outputStream);
		fmt.setRepository(db);
		fmt.setAsText(true);

		FileHeader fh = fmt.toFileHeader(mod);

		fmt.format(mod);
		assertEquals(diffHeader
		assertEquals(FileHeader.PatchType.BINARY

		assertEquals(1

		HunkHeader hh = fh.getHunks().get(0);
		assertEquals(1
		assertEquals(1
		assertEquals(3
	}

	@Test
	public void testDiff_BinaryAsBinary() throws Exception {
		ObjectId adId = blob("a\nd\n");
		ObjectId binId = blob("a\nb\nc\n\0\0\0\0d\n");

		String diffHeader = makeDiffHeaderAsBinary(PATH_A
				+ "GIT binary patch\n" +
				"delta 15\n" +
				"UcmZSK;Ys95;!5UX0D=@Q01oE?&j0`b\n\n" +
                                "delta 7\n" +
				"Ocmd;KVM*jl;Q{~x`vC3$\n\n";

		DiffEntry ad = DiffEntry.delete(PATH_A
		DiffEntry abcd = DiffEntry.add(PATH_B

		DiffEntry mod = DiffEntry.pair(ChangeType.MODIFY

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DiffFormatter fmt = new DiffFormatter(outputStream);
		fmt.setRepository(db);
		fmt.setAsBinary(true);

		FileHeader fh = fmt.toFileHeader(mod);

		fmt.format(mod);
		assertEquals(diffHeader
		assertEquals(FileHeader.PatchType.BINARY

		assertEquals(1

		HunkHeader hh = fh.getHunks().get(0);
		assertEquals(0
	}

	@Test
	public void testDiff_ADD_BinaryAsBinaryShortData() throws Exception {
		ObjectId binId = blob("a\nb\nc\n\0\0\0\0d\n");
		String diffHeader = makeDiffADDHeaderAsBinary(PATH_B
				+ "GIT binary patch\n" +
				"literal 12\n" +
				"RcmYe~O5#f9VgP~^E&vT=0kZ%A\n" +
				"\n" +
				"literal 0\n" +
				"HcmV?d00001\n\n";

		DiffEntry ad = DiffEntry.add(PATH_A
		DiffEntry abcd = DiffEntry.add(PATH_B

		DiffEntry mod = DiffEntry.pair(ChangeType.ADD

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DiffFormatter fmt = new DiffFormatter(outputStream);
		fmt.setRepository(db);
		fmt.setAsBinary(true);

		FileHeader fh = fmt.toFileHeader(mod);

		fmt.format(mod);
		assertEquals(diffHeader
		assertEquals(FileHeader.PatchType.BINARY

		assertEquals(1

		HunkHeader hh = fh.getHunks().get(0);
		assertEquals(0
	}

	@Test
	public void testDiff_ADD_BinaryAsBinaryLongData() throws Exception {
		ObjectId binId = blob(
				"a\nb\nc\n\0\0\0\0\1\1\1\1\1\1\1\1\1\1\1\1d\n" +
						"e\nf\ng\n\0\0\0\0\2\2\2\2\2\2\2\2\2\2\2\2h\n" +
						"i\nj\nk\n\0\0\0\0\3\3\3\3\3\3\3\3\3\3\3\3l\n" +
						"m\nn\no\n\0\0\0\0\4\4\4\4\4\4\4\4\4\4\4\4p\n" +
						"q\nr\ns\n\0\0\0\0\5\5\5\5\5\5\5\5\5\5\5\5t\n");

		String diffHeader = makeDiffADDHeaderAsBinary(PATH_B
				+ "GIT binary patch\n" +
				"literal 120\n" +
				"zcmYkwHxhs_5ClPNGQUJ1WDrj9|AM+`JM0O^O!I5Sf@dYzcOsI=Z1%k<>>Ql-gSfb<\n" +
				"JJock_`2buv3C{ok\n" +
				"\n" +
				"literal 0\n" +
				"HcmV?d00001\n\n";

		DiffEntry ad = DiffEntry.add(PATH_A
		DiffEntry abcd = DiffEntry.add(PATH_B

		DiffEntry mod = DiffEntry.pair(ChangeType.ADD

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DiffFormatter fmt = new DiffFormatter(outputStream);
		fmt.setRepository(db);
		fmt.setAsBinary(true);

		FileHeader fh = fmt.toFileHeader(mod);

		fmt.format(mod);
		assertEquals(diffHeader
		assertEquals(FileHeader.PatchType.BINARY

		assertEquals(1

		HunkHeader hh = fh.getHunks().get(0);
		assertEquals(0
	}

