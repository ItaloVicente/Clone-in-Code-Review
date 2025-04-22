        fSegments = new String[1];
        fSegments[0] = fPattern;
        fBound = fLength;
        words = new Word[1];
        words[0].pattern = fPattern;
        words[0].bound = fLength;
        words[0].fragments = wordsSplitted;
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
