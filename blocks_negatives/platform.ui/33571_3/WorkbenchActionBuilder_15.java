    }

    void updateModeLine(final String text) {
        statusLineItem.setText(text);
    }

    /**
     * Returns true if the menu with the given ID should
     * be considered as an OLE container menu. Container menus
     * are preserved in OLE menu merging.
     */
    @Override
