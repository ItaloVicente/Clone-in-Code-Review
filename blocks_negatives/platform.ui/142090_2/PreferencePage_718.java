        this.container = container;
    }

    /**
     * Sets the preference store for this preference page.
     * <p>
     * If preferenceStore is set to null, getPreferenceStore
     * will invoke doGetPreferenceStore the next time it is called.
     * </p>
     *
     * @param store the preference store, or <code>null</code>
     * @see #getPreferenceStore
     */
    public void setPreferenceStore(IPreferenceStore store) {
        preferenceStore = store;
    }


    @Override
