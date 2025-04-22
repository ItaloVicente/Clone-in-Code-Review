    /**
     * Tests that the image descriptors for all perspective descriptors are non-null.
     * <p>
     * Note that some perspective extensions in the test suite do not specify an icon
     * attribute.  getImageDescriptor should return a default image descriptor in this
     * case.  This is a regression test for bug 68325.
     * </p>
     */
    public void testGetImageDescriptor() {
        for (IPerspectiveDescriptor fPerspective : fPerspectives) {
            ImageDescriptor image = fPerspective.getImageDescriptor();
            assertNotNull(image);
        }
    }
