    }

    /**
     * Updates the change font button and the previewer to reflect the
     * newly selected font.
     * @param font The FontData[] to update with.
     */
    private void updateFont(FontData font[]) {
        FontData[] bestFont = JFaceResources.getFontRegistry().filterData(
                font, valueControl.getDisplay());

        if (bestFont == null) {
