    /**
     * Returns the suite. This is required to use the JUnit Launcher.
     * @return A new test suite; never <code>null</code>.;
     */
    public static Test suite() {
        return new MultiPageEditorTestSuite();
    }
