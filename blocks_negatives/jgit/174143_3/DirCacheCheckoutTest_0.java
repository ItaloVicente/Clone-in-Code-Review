
	@Test
	public void shouldReturnAndNotThrowNPEWhenCheckoutEntryIsCalledWithNullEntry() throws Exception{
		checkoutEntry(new InMemoryRepository(null), null, null, true, new CheckoutMetadata(null, null));
	}


