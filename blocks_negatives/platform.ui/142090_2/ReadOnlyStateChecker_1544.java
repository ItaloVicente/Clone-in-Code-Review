     * Open a message dialog with Yes No, Yes To All and Cancel buttons. Return the
     * code that indicates the selection.
     * @return int
     *	one of
     *		YES_TO_ALL_ID
     *		YES_ID
     *		NO_ID
     *		CANCEL_ID
     *
     * @param resource - the resource being queried.
     */
    private int queryYesToAllNoCancel(IResource resource) {
