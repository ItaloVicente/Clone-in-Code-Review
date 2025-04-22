        }
        settings.put(STORE_EXPANDED_CATEGORIES_ID,
                (String[]) expandedElementPaths
                        .toArray(new String[expandedElementPaths.size()]));
    }

    /**
     * Stores the currently-selected element in this page's dialog store, in
     * order to recreate this page's state in the next instance of this page.
     */
    protected void storeSelectedCategoryAndWizard() {
