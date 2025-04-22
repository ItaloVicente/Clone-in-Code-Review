    /**
     *	Write the contents of the file to the tar archive.
     *
     *	@param entry
     *	@param contents
     *  @exception java.io.IOException
     *  @exception org.eclipse.core.runtime.CoreException
     */
    private void write(TarEntry entry, IFile contents) throws IOException, CoreException {
