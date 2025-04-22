    /**
     * Tests that pressing delete in a styled text widget (in a running
     * Eclipse) does not cause a double delete.
     *
     * @throws AWTException
     *             If the creation of robot
     * @throws CommandException
     *             If execution of the handler fails.
     * @throws CoreException
     *             If the test project cannot be created and opened.
     * @throws IOException
     *             If the file cannot be read.
     */
    public void testDoubleDelete() throws CommandException,
            CoreException, IOException {
        IWorkbenchWindow window = openTestWindow();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IProject testProject = workspace.getRoot().getProject(
        testProject.create(null);
        testProject.open(null);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                originalContents.getBytes());
        textFile.create(inputStream, true, null);
        IDE.openEditor(window.getActivePage(), textFile,
                true);
