    protected static String buildAuthHeader(String username, String password) {
        StringBuilder clearText = new StringBuilder(username);
        clearText.append(':');
        if (password != null) {
            clearText.append(password);
        }
        String encodedText = org.apache.commons.codec.binary.Base64.encodeBase64String(clearText.toString().getBytes());
        char[] encodedWoNewline = new char[encodedText.length() - 2];
        encodedText.getChars(0, encodedText.length() - 2, encodedWoNewline, 0);
        String authVal = "Basic " + new String(encodedWoNewline);

        return authVal;
    }

