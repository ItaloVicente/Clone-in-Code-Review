    /**
     * Returns the suite. This is required to use the JUnit Launcher.
     */
    public static Test suite() {
        return new DragTestSuite();
    }

    /**
     * Whether the platform we're running on supports the detaching of views.
     * This is initialized in the following static block.
     *
     * @since 3.2
     */
