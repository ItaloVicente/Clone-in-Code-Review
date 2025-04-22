    /**
     * Returns true if a link is present at the given character location
     */
    public boolean isLinkAt(int offset) {
        for (int i = 0; i < linkRanges.length; i++) {
            if (offset >= linkRanges[i][0]
                    && offset < linkRanges[i][0] + linkRanges[i][1]) {
                return true;
            }
        }
        return false;
    }
