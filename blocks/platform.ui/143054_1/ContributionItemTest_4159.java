	public void testParentLink() {
		IContributionManager mgr = new DummyContributionManager();
		ContributionItem item = new ActionContributionItem(new DummyAction());
		assertNull(item.getParent());
		mgr.add(item);
		assertEquals(mgr, item.getParent());
		mgr.remove(item);
		assertNull(item.getParent());
	}
