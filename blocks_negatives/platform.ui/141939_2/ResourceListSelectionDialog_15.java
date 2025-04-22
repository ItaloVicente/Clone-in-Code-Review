        updateFilterThread.stop = true;
        stringMatcher = new StringMatcher(patternString, true, false);
        UpdateFilterThread oldThread = updateFilterThread;
        updateFilterThread = new UpdateFilterThread();
            updateFilterThread.firstMatch = 0;
            updateFilterThread.lastMatch = -1;
            updateFilterThread.start();
            return;
        }

        if (oldPattern != null && (oldPattern.length() != 0)
            int matchLength = oldPattern.length() - 1;
            if (patternString.regionMatches(0, oldPattern, 0, matchLength)) {
                updateFilterThread.firstMatch = oldThread.firstMatch;
                updateFilterThread.lastMatch = oldThread.lastMatch;
                updateFilterThread.start();
                return;
            }
        }

        updateFilterThread.firstMatch = 0;
        updateFilterThread.lastMatch = descriptorsSize - 1;
        updateFilterThread.start();
    }

    /**
     * Use a binary search to get the first match for the patternString.
     * This method assumes the patternString does not contain any '?'
     * characters and that it contains only one '*' character at the end
     * of the string.
     */
    private int getFirstMatch() {
        int high = descriptorsSize;
        int low = -1;
        boolean match = false;
        ResourceDescriptor desc = new ResourceDescriptor();
        desc.label = patternString.substring(0, patternString.length() - 1);
        while (high - low > 1) {
            int index = (high + low) / 2;
            String label = descriptors[index].label;
            if (match(label)) {
                high = index;
                match = true;
            } else {
                int compare = descriptors[index].compareTo(desc);
                if (compare == -1) {
                    low = index;
                } else {
                    high = index;
                }
            }
        }
        if (match) {
