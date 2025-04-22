        return null;
    }

    /**
     * Find the first valid fontData in the provided list.
     * If none are valid return the first one regardless.
     * If the list is empty return <code>null</code>.
     *
     * @param fonts list of fonts
     * @param display the display
     * @return font data like described above
     * @deprecated use filterData in order to preserve
     * multiple entry fonts on Motif
     */
    @Deprecated
