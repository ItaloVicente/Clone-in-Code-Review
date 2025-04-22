    /**
     * Creates a section name from the buffer and trims trailing
     * space characters.
     *
     * @param buffer  the string from which to create the section name
     * @param start  the start index
     * @param end  the end index
     * @return a section name
     */
    private String parseHeading(String buffer, int start, int end) {
        while (Character.isWhitespace(buffer.charAt(end - 1)) && end > start) {
            end--;
        }
        return buffer.substring(start, end);
    }
