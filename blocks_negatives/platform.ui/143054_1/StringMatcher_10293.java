            /* make sure it's not an escaped wildcard */
            if (fLength > 1 && fPattern.charAt(fLength - 2) != '\\') {
                fHasTrailingStar = true;
            }
        }

		Vector<String> temp = new Vector<>();

        int pos = 0;
        StringBuilder buf = new StringBuilder();
        while (pos < fLength) {
            char c = fPattern.charAt(pos++);
            switch (c) {
            case '\\':
                if (pos >= fLength) {
                    buf.append(c);
                } else {
                    char next = fPattern.charAt(pos++);
                    /* if it's an escape sequence */
                    if (next == '*' || next == '?' || next == '\\') {
                        buf.append(next);
                    } else {
                        /* not an escape sequence, just insert literally */
                        buf.append(c);
                        buf.append(next);
                    }
                }
                break;
            case '*':
                if (buf.length() > 0) {
                    /* new segment */
                    temp.addElement(buf.toString());
                    fBound += buf.length();
                    buf.setLength(0);
                }
                break;
            case '?':
                /* append special character representing single match wildcard */
                buf.append(fSingleWildCard);
                break;
            default:
                buf.append(c);
            }
        }

        /* add last buffer to segment list */
        if (buf.length() > 0) {
            temp.addElement(buf.toString());
            fBound += buf.length();
        }

        fSegments = new String[temp.size()];
        temp.copyInto(fSegments);
    }

    /**
     * @param text  a string which contains no wildcard
     * @param start  the starting index in the text for search, inclusive
     * @param end  the stopping point of search, exclusive
     * @return the starting index in the text of the pattern , or -1 if not found
     */
    protected int posIn(String text, int start, int end) {//no wild card in pattern
        int max = end - fLength;

        if (!fIgnoreCase) {
            int i = text.indexOf(fPattern, start);
            if (i == -1 || i > max) {
