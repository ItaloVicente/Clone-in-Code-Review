            }
        });
    }

    /**
     * Adds a property change listener to this <code>ColorSelector</code>.
     * Events are fired when the color in the control changes via the user
     * clicking an selecting a new one in the color dialog. No event is fired in
     * the case where <code>setColorValue(RGB)</code> is invoked.
     *
     * @param listener
     *            a property change listener
     * @since 3.0
     */
    public void addListener(IPropertyChangeListener listener) {
        addListenerObject(listener);
    }

    /**
     * Compute the size of the image to be displayed.
     *
     * @param window -
     *            the window used to calculate
     * @return <code>Point</code>
     */
    private Point computeImageSize(Control window) {
        GC gc = new GC(window);
        Font f = JFaceResources.getFontRegistry().get(
                JFaceResources.DIALOG_FONT);
        gc.setFont(f);
        int height = gc.getFontMetrics().getHeight();
        gc.dispose();
        Point p = new Point(height * 3 - 6, height);
        return p;
    }

    /**
     * Get the button control being wrappered by the selector.
     *
     * @return <code>Button</code>
     */
    public Button getButton() {
        return fButton;
    }

    /**
     * Return the currently displayed color.
     *
     * @return <code>RGB</code>
     */
    public RGB getColorValue() {
        return fColorValue;
    }

    /**
     * Removes the given listener from this <code>ColorSelector</code>. Has
     * no effect if the listener is not registered.
     *
     * @param listener
     *            a property change listener
     * @since 3.0
     */
    public void removeListener(IPropertyChangeListener listener) {
        removeListenerObject(listener);
    }

    /**
     * Set the current color value and update the control.
     *
     * @param rgb
     *            The new color.
     */
    public void setColorValue(RGB rgb) {
        fColorValue = rgb;
        updateColorImage();
    }

    /**
     * Set whether or not the button is enabled.
     *
     * @param state
     *            the enabled state.
     */
    public void setEnabled(boolean state) {
        getButton().setEnabled(state);
    }

    /**
     * Update the image being displayed on the button using the current color
     * setting.
     */
    protected void updateColorImage() {
        Display display = fButton.getDisplay();
        GC gc = new GC(fImage);
        gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
        gc.drawRectangle(0, 2, fExtent.x - 1, fExtent.y - 4);
        if (fColor != null) {
