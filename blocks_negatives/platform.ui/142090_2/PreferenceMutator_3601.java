    /**
     * Sets a key binding in the currently running Eclipse application. It
     * accomplishes this by writing out an exported preferences file by hand,
     * and then importing it back into the application.
     *
     * @param commandId
     *           The command identifier to which the key binding should be
     *           associated; should not be <code>null</code>.
     * @param keySequenceText
     *           The text of the key sequence for this key binding; must not be
     *           <code>null</code>.
     * @throws CoreException
     *            If the exported preferences file is invalid for some reason.
     * @throws FileNotFoundException
     *            If the temporary file is removed before it can be read in.
     *            (Wow)
     * @throws IOException
     *            If the creation of or the writing to the temporary file fails
     *            for some reason.
     */
    static final void setKeyBinding(String commandId, String keySequenceText)
            throws CoreException, FileNotFoundException, IOException {
        Properties preferences = new Properties();
        preferences.put(key, value);
