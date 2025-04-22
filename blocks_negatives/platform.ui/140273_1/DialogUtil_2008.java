        return label;
    }

    /**
     * Return the number of rows available in the current display using the
     * current font.
     * @param parent The Composite whose Font will be queried.
     * @return int The result of the display size divided by the font size.
     */
    public static int availableRows(Composite parent) {

        int fontHeight = (parent.getFont().getFontData())[0].getHeight();
        int displayHeight = parent.getDisplay().getClientArea().height;

        return displayHeight / fontHeight;
    }

    /**
     * Return whether or not the font in the parent is the size of a result
     * font (i.e. smaller than the High Contrast Font). This method is used to
     * make layout decisions based on screen space.
     * @param parent The Composite whose Font will be queried.
     * @return boolean. True if there are more than 50 lines of possible
     * text in the display.
     */
    public static boolean inRegularFontMode(Composite parent) {

        return availableRows(parent) > 50;
    }
