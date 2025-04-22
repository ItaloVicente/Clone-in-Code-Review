    /**
     * Returns true if backward navigation is possible.  This is only true
     * if the stack size is greater than 0.
     *
     * @return true if backward navigation is possible
     */
    public boolean canGoBack() {
        return (fStack.size() > 0);
    }
