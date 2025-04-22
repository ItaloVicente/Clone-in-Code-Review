    /**
     * @return
     */
    private ActionSetRegistry getActionSetRegistry() {
        return WorkbenchPlugin.getDefault().getActionSetRegistry();
    }

    public void testActionSetPartAssociations() {
        assertEquals(0, getActionSetRegistry().getActionSetsFor(PART_ID).length);
        getBundle();
        assertEquals(1, getActionSetRegistry().getActionSetsFor(PART_ID).length);
        removeBundle();
        assertEquals(0, getActionSetRegistry().getActionSetsFor(PART_ID).length);
    }

    @Override
