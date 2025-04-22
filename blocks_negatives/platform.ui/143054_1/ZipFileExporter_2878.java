    /**
     * Create an instance of this class.
     *
     * @param filename
     *            java.lang.String
     * @param compress
     *            boolean
     * @param resolveLinks
     *            boolean
     * @exception java.io.IOException
     */
    public ZipFileExporter(String filename, boolean compress, boolean resolveLinks) throws IOException {
        this.resolveLinks = resolveLinks;
        outputStream = new ZipOutputStream(new FileOutputStream(filename));
        useCompression = compress;
    }
