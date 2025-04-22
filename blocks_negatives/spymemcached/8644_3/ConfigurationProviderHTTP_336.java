    /**
     * Oddly, lots of things that do HTTP seem to not know how to do this and
     * Authenticator caches for the process.  Since we only need Basic at the
     * moment simply, add the header.
     *
     * @return a value for an HTTP Basic Auth Header
     */
    protected static String buildAuthHeader(String username, String password) throws UnsupportedEncodingException {
        StringBuilder clearText = new StringBuilder(username);
        clearText.append(':');
        if (password != null) {
            clearText.append(password);
        }
        String headerResult;
        headerResult = "Basic " + Base64.encodeBase64String(clearText.toString().getBytes("UTF-8"));

        return headerResult;
    }
