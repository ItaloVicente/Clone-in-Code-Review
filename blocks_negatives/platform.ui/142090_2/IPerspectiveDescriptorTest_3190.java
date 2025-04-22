    /**
     * Tests that the ids for all perspective descriptors are non-null and non-empty.
     */
    public void testGetId() {
        for (IPerspectiveDescriptor fPerspective : fPerspectives) {
            String id = fPerspective.getId();
            assertNotNull(id);
            assertTrue(id.length() > 0);
        }
    }
