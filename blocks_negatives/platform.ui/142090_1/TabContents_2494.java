        return -1;
    }

    /**
     * Retrieve the section at a numbered index.
     * @param i a numbered index.
     * @return the section.
     */
    public ISection getSectionAtIndex(int i) {
        if (i >= 0 && i < sections.length) {
