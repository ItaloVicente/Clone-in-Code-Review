    }

    /**
     * Create a new <code>Color</code> on the receivers <code>Display</code>.
     *
     * @param rgb the <code>RGB</code> data for the color.
     * @return the new <code>Color</code> object.
     *
     * @since 3.1
     */
    private Color createColor(RGB rgb) {
    	if (this.display == null) {
    		Display display = Display.getCurrent();
    		if (display == null) {
    			throw new IllegalStateException();
    		}
    		this.display = display;
    		if (cleanOnDisplayDisposal) {
    			hookDisplayDispose();
    		}
    	}
        return new Color(display, rgb);
    }

    /**
     * Dispose of all of the <code>Color</code>s in this iterator.
     *
     * @param iterator over <code>Collection</code> of <code>Color</code>
     */
    private void disposeColors(Iterator<Color> iterator) {
        while (iterator.hasNext()) {
            Object next = iterator.next();
            ((Color) next).dispose();
        }
    }

    /**
     * Returns the <code>color</code> associated with the given symbolic color
     * name, or <code>null</code> if no such definition exists.
     *
     * @param symbolicName symbolic color name
     * @return the <code>Color</code> or <code>null</code>
     */
    public Color get(String symbolicName) {

        Assert.isNotNull(symbolicName);
        Object result = stringToColor.get(symbolicName);
        if (result != null) {
