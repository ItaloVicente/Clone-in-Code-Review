        return StringConverter.asRectangle(value, dr);
    }

    /**
     * Returns the current value of the color-valued preference with the
     * given name in the given preference store.
     * Returns the default-default value (<code>COLOR_DEFAULT_DEFAULT</code>)
     * if there is no preference with the given name, or if the current value
     * cannot be treated as a color.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @return the color-valued preference
     */
    public static RGB getColor(IPreferenceStore store, String name) {
        return basicGetColor(store.getString(name));
    }

    /**
     * Returns the default value for the color-valued preference
     * with the given name in the given preference store.
     * Returns the default-default value (<code>COLOR_DEFAULT_DEFAULT</code>)
     * is no default preference with the given name, or if the default
     * value cannot be treated as a color.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @return the default value of the preference
     */
    public static RGB getDefaultColor(IPreferenceStore store, String name) {
        return basicGetColor(store.getDefaultString(name));
    }

    /**
     * Returns the default value array for the font-valued preference
     * with the given name in the given preference store.
     * Returns the default-default value (<code>FONTDATA_ARRAY_DEFAULT_DEFAULT</code>)
     * is no default preference with the given name, or if the default
     * value cannot be treated as font data.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @return the default value of the preference
     */
    public static FontData[] getDefaultFontDataArray(IPreferenceStore store,
            String name) {
        return basicGetFontData(store.getDefaultString(name));
    }

    /**
     * Returns a single default value for the font-valued preference
     * with the given name in the given preference store.
     * Returns the default-default value (<code>FONTDATA_DEFAULT_DEFAULT</code>)
     * is no default preference with the given name, or if the default
     * value cannot be treated as font data.
     * This method is provided for backwards compatibility. It is
     * recommended that <code>getDefaultFontDataArray</code> is
     * used instead.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @return the default value of the preference
     */
    public static FontData getDefaultFontData(IPreferenceStore store,
            String name) {
        return getDefaultFontDataArray(store, name)[0];
    }

    /**
     * Returns the default value for the point-valued preference
     * with the given name in the given preference store.
     * Returns the default-default value (<code>POINT_DEFAULT_DEFAULT</code>)
     * is no default preference with the given name, or if the default
     * value cannot be treated as a point.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @return the default value of the preference
     */
    public static Point getDefaultPoint(IPreferenceStore store, String name) {
        return basicGetPoint(store.getDefaultString(name));
    }

    /**
     * Returns the default value for the rectangle-valued preference
     * with the given name in the given preference store.
     * Returns the default-default value (<code>RECTANGLE_DEFAULT_DEFAULT</code>)
     * is no default preference with the given name, or if the default
     * value cannot be treated as a rectangle.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @return the default value of the preference
     */
    public static Rectangle getDefaultRectangle(IPreferenceStore store,
            String name) {
        return basicGetRectangle(store.getDefaultString(name));
    }

    /**
     * Returns the current value of the font-valued preference with the
     * given name in the given preference store.
     * Returns the default-default value (<code>FONTDATA_ARRAY_DEFAULT_DEFAULT</code>)
     * if there is no preference with the given name, or if the current value
     * cannot be treated as font data.
     *
     * @param store the preference store
     * @param name the name of the preference
     * @return the font-valued preference
     */
    public static FontData[] getFontDataArray(IPreferenceStore store,
            String name) {
        return basicGetFontData(store.getString(name));
    }
