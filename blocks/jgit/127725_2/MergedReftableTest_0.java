	@Test
	public void overlappingUpdateIndeces() throws IOException {
		byte[] table1 = write(Arrays.asList(
				ref("refs/head/master"
				ref("refs/head/next"
		byte[] table2 = write(Arrays.asList(ref("refs/head/master"
				ref("refs/head/next"

		MergedReftable mr = merge(table1
		assertEquals(30
		assertEquals(110
	}

	private static MergedReftable merge(byte[]... table) throws IOException {
