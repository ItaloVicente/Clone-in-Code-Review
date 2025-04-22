     *	Create an instance of this class.
     *
     *	@param filename java.lang.String
     *	@param compress boolean
     *	@exception java.io.IOException
     */
    public TarFileExporter(String filename, boolean compress) throws IOException {
    	if(compress) {
