    /**
     * Returns the link at the given offset (if there is one),
     * otherwise returns <code>null</code>.
     */
    public String getLinkAt(int offset) {
        for (int i = 0; i < linkRanges.length; i++) {
            if (offset >= linkRanges[i][0]
                    && offset < linkRanges[i][0] + linkRanges[i][1]) {
                return hrefs[i];
            }
        }
        return null;
    }
