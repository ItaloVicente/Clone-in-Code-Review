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
