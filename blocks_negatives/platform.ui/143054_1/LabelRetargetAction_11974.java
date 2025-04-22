        super.setText(text);
        acceleratorText = LegacyActionTools.extractAcceleratorText(text);
        defaultText = text;
    }

    /**
     * Sets the tooltip text to the given text.
     * The value <code>null</code> clears the tooltip text.
     */
    @Override
