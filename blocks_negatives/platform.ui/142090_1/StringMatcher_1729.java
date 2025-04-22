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
     * Parses the given pattern into segments seperated by wildcard &#39;*&#39; characters.
     * @param p, a String object that is a simple regular expression with ?*? and/or &#39;&#63;&#39;
     */
    private void parseWildCards() {
