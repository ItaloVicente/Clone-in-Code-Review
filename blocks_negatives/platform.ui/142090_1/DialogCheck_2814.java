    /*
     * Counts the number of lines in a given String.
     * For example, if a string contains one (1) newline character,
     * a value of two (2) would be returned.
     * @param text The string to look through.
     * @return int the number of lines in text.
     */
    private static int countLines(String text) {
        int newLines = 1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n') {
                newLines++;
            }
        }
        return newLines;
    }
