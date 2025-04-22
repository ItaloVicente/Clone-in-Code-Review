    /**
     * Returns the file resource underlying this editor input.
     * <p>
     * The <code>IFile</code> returned can be a handle to a resource
     * that does not exist in the workspace. As such, an editor should
     * provide appropriate feedback to the user instead of simply failing
     * during input validation. For example, a text editor could open
     * in read-only mode with a message in the text area to inform the
     * user that the file does not exist.
     * </p>
     *
     * @return the underlying file
     */
    public IFile getFile();
