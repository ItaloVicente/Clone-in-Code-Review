
    /** previous bytes (current - 1) inspected by string detection (useful to detect escaped quotes) */
    private byte lastByte = 0;

    /** previous bytes (current - 2) inspected by string detection (useful to detect escaped quotes) */
    private byte beforeLastByte = 0;

    /** flag to indicate that we are currently reading a JSON string */
    private boolean inString = false;

    /**
     * Detects opening and closing of JSON strings and keep track of it in order
     * to mark characters in the string (delimiter quotes included) as escaped.
     *
     * Quotes escaped by a \ are correctly detected and do not mark a closing of
     * a JSON string.
     *
     * @param nextByte the next byte to inspect.
     * @return true if the byte should be ignored as part of a JSON string, false otherwise.
     */
    private boolean isEscaped(byte nextByte) {
        boolean result = false;
        if (inString) {
                boolean escaped = lastByte == '\\' && beforeLastByte != '\\';
                inString = escaped;
            }
            result = true;
        } else {
            if (nextByte == '\"') {
                inString = true;
                result = true;
            }
        }
        beforeLastByte = lastByte;
        lastByte = nextByte;
        return result;
    }
