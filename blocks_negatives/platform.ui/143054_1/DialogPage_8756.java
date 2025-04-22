        return errorMessage;
    }

    /**
     * Returns the default font to use for this dialog page.
     *
     * @return the font
     */
    protected Font getFont() {
        return JFaceResources.getFontRegistry().get(getDialogFontName());
    }

    @Override
