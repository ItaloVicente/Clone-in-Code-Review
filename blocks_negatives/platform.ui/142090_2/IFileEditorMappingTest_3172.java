    public void testGetLabel() throws Throwable {
        String label;
        for (IFileEditorMapping fMapping : fMappings) {
            label = fMapping.getLabel();
            assertNotNull(label);
            assertEquals(label, fMapping.getName() + "."
                    + fMapping.getExtension());
        }
    }
