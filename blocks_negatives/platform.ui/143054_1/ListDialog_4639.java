        setResult(selection.toList());
        super.okPressed();
    }

    /**
     * Returns the initial height of the dialog in number of characters.
     *
     * @return the initial height of the dialog in number of characters
     */
    public int getHeightInChars() {
        return heightInChars;
    }

    /**
     * Returns the initial width of the dialog in number of characters.
     *
     * @return the initial width of the dialog in number of characters
     */
    public int getWidthInChars() {
        return widthInChars;
    }

    /**
     * Sets the initial height of the dialog in number of characters.
     *
     * @param heightInChars
     *            the initialheight of the dialog in number of characters
     */
    public void setHeightInChars(int heightInChars) {
        this.heightInChars = heightInChars;
    }

    /**
     * Sets the initial width of the dialog in number of characters.
     *
     * @param widthInChars
     *            the initial width of the dialog in number of characters
     */
    public void setWidthInChars(int widthInChars) {
        this.widthInChars = widthInChars;
    }
