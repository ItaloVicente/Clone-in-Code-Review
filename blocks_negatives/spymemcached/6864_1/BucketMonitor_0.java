    /**
     * Since netty seems to have base64 and have a header for basic auth, but
     * oddly no way to generate a base64 enoded auth heder, this utility function
     * will.
     *
     * @return a value for an HTTP Basic Auth Header
     */
    private String getAuthHeader() {
        StringBuffer clearText = new StringBuffer(getHttpUser());
        clearText.append(':');
        if (getHttpPass() != null) {
            clearText.append(getHttpPass());
        }
        String encodedText = org.apache.commons.codec.binary.Base64.encodeBase64String(clearText.toString().getBytes());
        char[] encodedWoNewline = new char[encodedText.length() - 2];
        encodedText.getChars(0, encodedText.length() - 2, encodedWoNewline, 0);
        String authVal = "Basic " + new String(encodedWoNewline);

        return authVal;
    }

