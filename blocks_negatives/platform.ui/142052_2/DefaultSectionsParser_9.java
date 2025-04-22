    /**
     * Returns a string containing the contents of the given
     * file.  Returns an empty string if there were any errors
     * reading the file.
     */
    protected String getText(IFile file) {
        try {
            InputStream in = file.getContents();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int read = in.read(buf);
            while (read > 0) {
                out.write(buf, 0, read);
                read = in.read(buf);
            }
            return out.toString();
        } catch (CoreException e) {
        } catch (IOException e) {
        }
    }
