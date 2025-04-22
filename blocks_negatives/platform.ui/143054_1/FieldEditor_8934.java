    }

    /**
     * Sets the name of the preference this field editor operates on.
     * <p>
     * The ability to change this allows the same field editor object
     * to be reused for different preferences.
     * </p>
     * <p>
     * For example: <p>
     * <pre>
     * 	...
     *  editor.setPreferenceName("font");
     * 	editor.load();
     * </pre>
     * </p>
     *
     * @param name the name of the preference
     */
    public void setPreferenceName(String name) {
        preferenceName = name;
    }

    /**
     * Sets the preference page in which this field editor
     * appears.
     *
     * @param preferencePage the preference page, or <code>null</code> if none
     * @deprecated use #setPage(DialogPage)
     */
    @Deprecated
