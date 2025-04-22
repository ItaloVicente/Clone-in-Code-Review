    /**
     * Returns whether this adapter may have children. This is an optimized method
     * used by content providers to allow showing the [+] expand icon without having
     * yet fetched the children for the element.
     * <p>
     * If <code>false</code> is returned, then the content provider may assume
     * that this adapter has no children. If <code>true</code> is returned,
     * then the job manager may assume that this adapter may have children.
     * <p>
     *
     * @return <code>true</code>if the adapter may have childen, and <code>false</code>
     * 	otherwise.
     */
    boolean isContainer();
