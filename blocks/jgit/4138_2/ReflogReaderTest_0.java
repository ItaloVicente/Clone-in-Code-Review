	@Test
	public void testCheckout() throws Exception {
		setupReflog("logs/HEAD"
		List<ReflogEntry> entries = db.getReflogReader(Constants.HEAD)
				.getReverseEntries();
		assertEquals(1
		ReflogEntry entry = entries.get(0);
		CheckoutEntry checkout = entry.parseCheckout();
		assertNotNull(checkout);
		assertEquals("master"
		assertEquals("new/work"
	}

