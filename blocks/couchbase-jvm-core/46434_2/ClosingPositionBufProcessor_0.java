
    private byte lastByte = 0;

    private boolean inString = false;

    private boolean isEscaped(byte nextByte) {
        boolean result = false;
        if (inString) {
            if (nextByte == '\"'
                    && lastByte != '\\') {
                inString = false;
            }
            result = true;
        } else {
            if (nextByte == '\"') {
                inString = true;
                result = true;
            }
        }
        lastByte = nextByte;
        return result;
    }
