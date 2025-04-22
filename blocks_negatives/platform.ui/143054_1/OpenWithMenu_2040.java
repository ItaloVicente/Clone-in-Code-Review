        return true;
    }

    /**
     * Opens the given editor on the selected file.
     *
     * @param editorDescriptor the editor descriptor, or null for the system editor
     * @param openUsingDescriptor use the descriptor's editor ID for opening if false (normal case),
     * or use the descriptor itself if true (needed to fix bug 178235).
     *
     * @since 3.5
     */
