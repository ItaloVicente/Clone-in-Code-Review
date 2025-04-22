	@SuppressWarnings("boxing")
	@Test
	public void largeVirtualTableFromRefs() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 5670; i++) {
			refs.add(ref(String.format("refs/heads/%04d"
		}
		Reftable t = Reftable.from(refs);
		assertScan(refs
		assertSeek(refs
	}

