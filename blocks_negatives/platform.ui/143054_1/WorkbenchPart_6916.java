        this.titleImage = titleImage;
        firePropertyChange(IWorkbenchPart.PROP_TITLE);
        if (imageDescriptor != null) {
            JFaceResources.getResources().destroyImage(imageDescriptor);
            imageDescriptor = null;
        }
    }

    /**
     * Sets or clears the title tool tip text of this part. Clients should
     * call this method instead of overriding <code>getTitleToolTip</code>
     *
     * @param toolTip the new tool tip text, or <code>null</code> to clear
     */
    protected void setTitleToolTip(String toolTip) {
        toolTip = Util.safeString(toolTip);
        if (Util.equals(this.toolTip, toolTip)) {
