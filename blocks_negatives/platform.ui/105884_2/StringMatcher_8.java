            }
            tCurPos = currentMatch + current.length();
            i++;
        }

        /* process final segment */
        if (!fHasTrailingStar && tCurPos != end) {
            int clen = current.length();
            return regExpRegionMatches(text, end - clen, current, 0, clen);
        }
        return i == segCount;
    }

    /**
     * This method parses the given pattern into segments seperated by wildcard '*' characters.
     * Since wildcards are not being used in this case, the pattern consists of a single segment.
     */
    private void parseNoWildCards() {
        fSegments = new String[1];
        fSegments[0] = fPattern;
        fBound = fLength;
    }

    /**
     * Parses the given pattern into segments seperated by wildcard '*' characters.
     * @param p, a String object that is a simple regular expression with '*' and/or '?'
     */
    private void parseWildCards() {
			fHasLeadingStar = true;
		}
            /* make sure it's not an escaped wildcard */
            if (fLength > 1 && fPattern.charAt(fLength - 2) != '\\') {
                fHasTrailingStar = true;
            }
        }

        Vector temp = new Vector();

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
     * @param text a string which contains no wildcard
     * @param start the starting index in the text for search, inclusive
     * @param end the stopping point of search, exclusive
     * @return the starting index in the text of the pattern , or -1 if not found
     */
    protected int posIn(String text, int start, int end) {//no wild card in pattern
        int max = end - fLength;

        if (!fIgnoreCase) {
            int i = text.indexOf(fPattern, start);
            if (i == -1 || i > max) {
