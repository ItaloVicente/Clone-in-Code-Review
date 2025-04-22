     * Draw the overlays for the receiver.
     * @param overlaysArray
     */
    private void drawOverlays(ImageDescriptor[] overlaysArray) {

        for (int i = 0; i < overlays.length; i++) {
            ImageDescriptor overlay = overlaysArray[i];
            if (overlay == null) {
