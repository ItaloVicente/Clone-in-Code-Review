    /*
     * Wait until a child is added to initialize the receiver's lists. Doing so
     * minimizes the amount of memory that is allocated when a large directory
     * structure is being processed.
     */
    private AdaptableList folders = null;
