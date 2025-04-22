    /**
     * Tests that a contribution item's parent link is set when added to a
     * contribution manager, and cleared when the item is removed.
     * This is a regression test for:
     * Bug 80569 [Contributions] Parent of contribution item not cleared when item removed from manager
     */
    public void testParentLink() {
        IContributionManager mgr = new DummyContributionManager();
        ContributionItem item = new ActionContributionItem(new DummyAction());
        assertNull(item.getParent());
        mgr.add(item);
        assertEquals(mgr, item.getParent());
        mgr.remove(item);
        assertNull(item.getParent());
    }
