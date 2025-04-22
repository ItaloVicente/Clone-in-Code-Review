    /**
     *	Write the contents of the file to the tar archive.
     *
     *	@param entry
     *	@param contents
     *  @exception java.io.IOException
     *  @exception org.eclipse.core.runtime.CoreException
     */
    private void write(ZipEntry entry, IFile contents) throws IOException, CoreException {
        byte[] readBuffer = new byte[4096];
