        this.chosenFont = bestFont;

        if (valueControl != null) {
            valueControl.setText(StringConverter.asString(chosenFont[0]));
        }
        if (previewer != null) {
            previewer.setFont(bestFont);
        }
    }

    /**
     * Store the default preference for the field
     * being edited
     */
    protected void setToDefault() {
        FontData[] defaultFontData = PreferenceConverter
                .getDefaultFontDataArray(getPreferenceStore(),
                        getPreferenceName());
        PreferenceConverter.setValue(getPreferenceStore(), getPreferenceName(),
                defaultFontData);
    }

    /**
     * Get the system default font data.
     * @return FontData[]
     */
    private FontData[] getDefaultFontData() {
        return valueControl.getDisplay().getSystemFont().getFontData();
    }

    /*
     * @see FieldEditor.setEnabled(boolean,Composite).
     */
    @Override
