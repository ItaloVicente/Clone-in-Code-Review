    /**
     * Computes the preferred size of the nth control
     *
     * @param controlIndex index of the control whose size will be computed
     * @param widthHint width of the control (or SWT.DEFAULT if unknown)
     * @param heightHint height of the control (or SWT.DEFAULT if unknown)
     * @return the preferred size of the control
     */
    public Point computeSize(int controlIndex, int widthHint, int heightHint) {
        return caches[controlIndex].computeSize(widthHint, heightHint);
    }
