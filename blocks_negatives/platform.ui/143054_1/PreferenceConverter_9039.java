    /**
     * Returns the current value of the first entry of the
     * font-valued preference with the
     * given name in the given preference store.
     * Returns the default-default value (<code>FONTDATA_ARRAY_DEFAULT_DEFAULT</code>)
     * if there is no preference with the given name, or if the current value
     * cannot be treated as font data.
     * This API is provided for backwards compatibility. It is
     * recommended that <code>getFontDataArray</code> is used instead.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @return the font-valued preference
     */
    public static FontData getFontData(IPreferenceStore store, String name) {
        return getFontDataArray(store, name)[0];
    }

    /**
     * Returns the current value of the point-valued preference with the
     * given name in the given preference store.
     * Returns the default-default value (<code>POINT_DEFAULT_DEFAULT</code>)
     * if there is no preference with the given name, or if the current value
     * cannot be treated as a point.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @return the point-valued preference
     */
    public static Point getPoint(IPreferenceStore store, String name) {
        return basicGetPoint(store.getString(name));
    }

    /**
     * Returns the current value of the rectangle-valued preference with the
     * given name in the given preference store.
     * Returns the default-default value (<code>RECTANGLE_DEFAULT_DEFAULT</code>)
     * if there is no preference with the given name, or if the current value
     * cannot be treated as a rectangle.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @return the rectangle-valued preference
     */
    public static Rectangle getRectangle(IPreferenceStore store, String name) {
        return basicGetRectangle(store.getString(name));
    }

    /**
     * Sets the default value of the preference with the given name
     * in the given preference store. As FontDatas are stored as
     * arrays this method is only provided for backwards compatibility.
     * Use <code>setDefault(IPreferenceStore, String, FontData[])</code>
     * instead.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @param value the new default value of the preference
     */
    public static void setDefault(IPreferenceStore store, String name,
            FontData value) {
        FontData[] fontDatas = new FontData[1];
        fontDatas[0] = value;
        setDefault(store, name, fontDatas);
    }

    /**
     * Sets the default value of the preference with the given name
     * in the given preference store.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @param value the new default value of the preference
     */
    public static void setDefault(IPreferenceStore store, String name,
            FontData[] value) {
        store.setDefault(name, getStoredRepresentation(value));
    }

    /**
     * Sets the default value of the preference with the given name
     * in the given preference store.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @param value the new default value of the preference
     */
    public static void setDefault(IPreferenceStore store, String name,
            Point value) {
        store.setDefault(name, StringConverter.asString(value));
    }

    /**
     * Sets the default value of the preference with the given name
     * in the given preference store.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @param value the new default value of the preference
     */
    public static void setDefault(IPreferenceStore store, String name,
            Rectangle value) {
        store.setDefault(name, StringConverter.asString(value));
    }

    /**
     * Sets the default value of the preference with the given name
     * in the given preference store.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @param value the new default value of the preference
     */
    public static void setDefault(IPreferenceStore store, String name, RGB value) {
        store.setDefault(name, StringConverter.asString(value));
    }

    /**
     * Sets the current value of the preference with the given name
     * in the given preference store.
     * <p>
     * Included for backwards compatibility.  This method is equivalent to
     * </code>setValue(store, name, new FontData[]{value})</code>.
     * </p>
     *
     * @param store the preference store
     * @param name the name of the preference
     * @param value the new current value of the preference
     */
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
