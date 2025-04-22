	private RefLeaseSpec(final RefLeaseSpec p) {
		ref = p.getRef();
		expected = p.getExpected();
	}

	public RefLeaseSpec(final String ref, final String expected) {
