	public void testRemove() {
		try {
			iterator.remove();
			fail("Expected UnsupportedOperationException on a remove.");
		}
		catch (UnsupportedOperationException e) {
		}
	}
