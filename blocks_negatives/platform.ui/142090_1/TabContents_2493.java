    private ISection[] sections;

    private boolean controlsCreated;

    /**
     *
     */
    public TabContents() {
        controlsCreated = false;
    }

    /**
     * Retrieve a numbered index for the section.
     * @param section the section.
     * @return the section index.
     */
    public int getSectionIndex(ISection section) {
        for (int i = 0; i < sections.length; i++) {
