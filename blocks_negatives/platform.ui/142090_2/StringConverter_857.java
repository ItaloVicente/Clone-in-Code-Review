    /**
     * Convert the given value into an array of SWT font data objects.
     *
     * @param value the font list string
     * @return the value as a font list
     * @since 3.0
     */
    public static FontData[] asFontDataArray(String value) {
        String[] strings = getArrayFromList(value, FONT_SEPARATOR);
        ArrayList<FontData> data = new ArrayList<>(strings.length);
        for (String string : strings) {
            try {
                data.add(StringConverter.asFontData(string));
            } catch (DataFormatException e) {
            }
        }
        return data.toArray(new FontData[data.size()]);
    }

    /**
     * Converts the given value into an SWT font data object.
     * Returns the given default value if the
     * value does not represent a font data object.
     *
     * @param value the value to be converted
     * @param dflt the default value
     * @return the value as a font data object, or the default value
     */
    public static FontData asFontData(String value, FontData dflt) {
        try {
            return asFontData(value);
        } catch (DataFormatException e) {
            return dflt;
        }
    }

    /**
     * Converts the given value into an int.
     * This method fails if the value does not represent an int.
     *
     * @param value the value to be converted
     * @return the value as an int
     * @exception DataFormatException if the given value does not represent
     *	an int
     */
    public static int asInt(String value) throws DataFormatException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new DataFormatException(e.getMessage());
        }
    }

    /**
     * Converts the given value into an int.
     * Returns the given default value if the
     * value does not represent an int.
     *
     * @param value the value to be converted
     * @param dflt the default value
     * @return the value as an int, or the default value
     */
    public static int asInt(String value, int dflt) {
        try {
            return asInt(value);
        } catch (DataFormatException e) {
            return dflt;
        }
    }

    /**
     * Converts the given value into a long.
     * This method fails if the value does not represent a long.
     *
     * @param value the value to be converted
     * @return the value as a long
     * @exception DataFormatException if the given value does not represent
     *	a long
     */
    public static long asLong(String value) throws DataFormatException {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new DataFormatException(e.getMessage());
        }
    }

    /**
     * Converts the given value into a long.
     * Returns the given default value if the
     * value does not represent a long.
     *
     * @param value the value to be converted
     * @param dflt the default value
     * @return the value as a long, or the default value
     */
    public static long asLong(String value, long dflt) {
        try {
            return asLong(value);
        } catch (DataFormatException e) {
            return dflt;
        }
    }

    /**
