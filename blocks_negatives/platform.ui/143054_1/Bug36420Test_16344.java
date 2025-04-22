    /**
     * Tests that importing key preferences actually has an effect.
     *
     * @throws CoreException
     *             If the preferences can't be imported.
     * @throws FileNotFoundException
     *             If the temporary file is removed after it is created, but
     *             before it is opened. (Wow)
     * @throws IOException
     *             If something fails during output of the preferences file.
     */
    public void testImportKeyPreferences() throws CoreException,
            FileNotFoundException, IOException {
