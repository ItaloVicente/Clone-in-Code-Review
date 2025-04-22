    /**
     * Tests that the labels for all perspective descriptors are non-null and non-empty.
     */
    public void testGetLabel() {
        for (IPerspectiveDescriptor fPerspective : fPerspectives) {
            String label = fPerspective.getLabel();
            assertNotNull(label);
            assertTrue(label.length() > 0);
        }
    }
