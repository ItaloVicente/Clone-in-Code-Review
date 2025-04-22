		testGetRefsByPrefix(
				db.getRefDatabase().getRefsByPrefix("refs/heads/g"));
	}

	@Test
	public void testGetRefsStreamByPrefix() throws IOException {
		testGetRefsByPrefix(
				db.getRefDatabase().getRefsStreamByPrefix("refs/heads/g")
						.collect(Collectors.toUnmodifiableList()));
	}

	private void testGetRefsByPrefix(List<Ref> refs)
			throws IOException {
