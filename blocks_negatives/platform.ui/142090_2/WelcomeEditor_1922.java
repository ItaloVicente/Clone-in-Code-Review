        return false;
    }

    /**
     * Read the contents of the welcome page
     *
     * @param is the <code>InputStream</code> to parse
     * @throws IOException if there is a problem parsing the stream.
     */
    public void read(InputStream is) throws IOException {
        try {
            parser = new WelcomeParser();
        } catch (ParserConfigurationException e) {
            throw (IOException) (new IOException().initCause(e));
        } catch (SAXException e) {
            throw (IOException) (new IOException().initCause(e));
        }
        parser.parse(is);
    }

    /**
     * Reads the welcome file
     */
    public void readFile() {
        URL url = ((WelcomeEditorInput) getEditorInput()).getAboutInfo()
                .getWelcomePageURL();

        if (url == null) {
