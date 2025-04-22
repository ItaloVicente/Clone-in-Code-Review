    /**
     * Tests to ensure that the image of the descriptor is the same as the part.
     *
     * @throws Throwable
     */
    public void testImage() throws Throwable {
        MockPart part = openPart(fPage);
        ImageDescriptor imageDescriptor = getIntroDesc().getImageDescriptor();
        assertNotNull(imageDescriptor);
