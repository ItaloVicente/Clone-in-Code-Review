    private Charset getCharset(String charsetName) {
        if (charsetName != null) {
            return Charset.forName(charsetName);
        }
        return Constants.SYSTEM_CHARSET ;
    }

