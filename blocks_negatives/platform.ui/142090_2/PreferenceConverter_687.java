    public static void setValue(IPreferenceStore store, String name,
            FontData value) {
        setValue(store, name, new FontData[] { value });
    }

    /**
     * Sets the current value of the preference with the given name
     * in the given preference store. This method also sets the corresponding
     * key in the JFace font registry to the value and fires a
     * property change event to listeners on the preference store.
     *
     * <p>
     * Note that this API does not update any other settings that may
     * be dependant upon it. Only the value in the preference store
     * and in the font registry is updated.
     * </p>
     * @param store the preference store
     * @param name the name of the preference
     * @param value the new current value of the preference
     *
     * @see #putValue(IPreferenceStore, String, FontData[])
     */
    public static void setValue(IPreferenceStore store, String name,
            FontData[] value) {
        FontData[] oldValue = getFontDataArray(store, name);
        if (!Arrays.equals(oldValue, value)) {
            store.putValue(name, getStoredRepresentation(value));
            JFaceResources.getFontRegistry().put(name, value);
            store.firePropertyChangeEvent(name, oldValue, value);
        }
    }

    /**
     * Sets the current value of the preference with the given name
     * in the given preference store. This method does not update
     * the font registry or fire a property change event.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @param value the new current value of the preference
     *
     * @see PreferenceConverter#setValue(IPreferenceStore, String, FontData[])
     */
    public static void putValue(IPreferenceStore store, String name,
            FontData[] value) {
        FontData[] oldValue = getFontDataArray(store, name);
        if (!Arrays.equals(oldValue, value)) {
            store.putValue(name, getStoredRepresentation(value));
        }
    }

    /**
     * Returns the stored representation of the given array of FontData objects.
     * The stored representation has the form FontData;FontData;
     * Only includes the non-null entries.
     *
     * @param fontData the array of FontData objects
     * @return the stored representation of the FontData objects
     * @since 3.0
     */
    public static String getStoredRepresentation(FontData[] fontData) {
        StringBuilder buffer = new StringBuilder();
        for (FontData element : fontData) {
            if (element != null) {
                buffer.append(element.toString());
                buffer.append(ENTRY_SEPARATOR);
            }
        }
        return buffer.toString();
    }

    /**
     * Sets the current value of the preference with the given name
     * in the given preference store.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @param value the new current value of the preference
     */
    public static void setValue(IPreferenceStore store, String name, Point value) {
        Point oldValue = getPoint(store, name);
        if (oldValue == null || !oldValue.equals(value)) {
            store.putValue(name, StringConverter.asString(value));
            store.firePropertyChangeEvent(name, oldValue, value);
        }
    }

    /**
     * Sets the current value of the preference with the given name
     * in the given preference store.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @param value the new current value of the preference
     */
    public static void setValue(IPreferenceStore store, String name,
            Rectangle value) {
        Rectangle oldValue = getRectangle(store, name);
        if (oldValue == null || !oldValue.equals(value)) {
            store.putValue(name, StringConverter.asString(value));
            store.firePropertyChangeEvent(name, oldValue, value);
        }
    }

    /**
     * Sets the current value of the preference with the given name
     * in the given preference store.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @param value the new current value of the preference
     */
    public static void setValue(IPreferenceStore store, String name, RGB value) {
        RGB oldValue = getColor(store, name);
        if (oldValue == null || !oldValue.equals(value)) {
            store.putValue(name, StringConverter.asString(value));
            store.firePropertyChangeEvent(name, oldValue, value);
        }
    }
