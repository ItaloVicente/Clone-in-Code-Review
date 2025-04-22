        } else {
            checkParent(radioBox, parent);
        }
        return radioBox;
    }

    /**
     * Sets the indent used for the first column of the radion button matrix.
     *
     * @param indent the indent (in pixels)
     */
    public void setIndent(int indent) {
        if (indent < 0) {
