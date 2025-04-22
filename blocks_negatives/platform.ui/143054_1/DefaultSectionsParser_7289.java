    /**
     * Returns the mark element that is the logical parent
     * of the given mark number.  Each dot in a mark number
     * represents a parent-child separation.  For example,
     * the parent of 1.2 is 1, the parent of 1.4.1 is 1.4.
     * Returns null if there is no appropriate parent.
     */
    protected IAdaptable getParent(Hashtable<String, MarkElement> toc, String number) {
        int lastDot = number.lastIndexOf('.');
        if (lastDot < 0)
            return null;
        String parentNumber = number.substring(0, lastDot);
        return toc.get(parentNumber);
    }
