    /**
     * Tests that check box items on the menu are checked when activated from
     * the keyboard.
     *
     * @throws CommandException
     *             If execution of the handler fails.
     * @throws CoreException
     *             If the exported preferences file is invalid for some reason.
     * @throws FileNotFoundException
     *             If the temporary file is removed before it can be read in.
     *             (Wow)
     * @throws IOException
     *             If the creation of or the writing to the temporary file
     *             fails for some reason.
     * @throws ParseException
     *             If the key binding cannot be parsed.
     */
    public void testCheckOnCheckbox() throws CoreException, CommandException,
            FileNotFoundException, IOException, ParseException {
        IWorkbenchWindow window = openTestWindow();
        Workbench workbench = (Workbench) window.getWorkbench();
