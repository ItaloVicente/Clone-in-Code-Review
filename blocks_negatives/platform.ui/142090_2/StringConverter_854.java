        throw new DataFormatException(
    }

    /**
     * Converts the given value into a boolean.
     * Returns the given default value if the
     * value does not represent a boolean.
     *
     * @param value the value to be converted
     * @param dflt the default value
     * @return the value as a boolean, or the default value
     */
    public static boolean asBoolean(String value, boolean dflt) {
        try {
            return asBoolean(value);
        } catch (DataFormatException e) {
            return dflt;
        }
    }

    /**
     * Converts the given value into a double.
     * This method fails if the value does not represent a double.
     *
     * @param value the value to be converted
     * @return the value as a double
     * @exception DataFormatException if the given value does not represent
     *	a double
     */
    public static double asDouble(String value) throws DataFormatException {
        try {
            return (Double.valueOf(value)).doubleValue();
        } catch (NumberFormatException e) {
            throw new DataFormatException(e.getMessage());
        }
    }

    /**
     * Converts the given value into a double.
     * Returns the given default value if the
     * value does not represent a double.
     *
     * @param value the value to be converted
     * @param dflt the default value
     * @return the value as a double, or the default value
     */
    public static double asDouble(String value, double dflt) {
        try {
            return asDouble(value);
        } catch (DataFormatException e) {
            return dflt;
        }
    }

    /**
     * Converts the given value into a float.
     * This method fails if the value does not represent a float.
     *
     * @param value the value to be converted
     * @return the value as a float
     * @exception DataFormatException if the given value does not represent
     *	a float
     */
    public static float asFloat(String value) throws DataFormatException {
        try {
            return (Float.valueOf(value)).floatValue();
        } catch (NumberFormatException e) {
            throw new DataFormatException(e.getMessage());
        }
    }

    /**
     * Converts the given value into a float.
     * Returns the given default value if the
     * value does not represent a float.
     *
     * @param value the value to be converted
     * @param dflt the default value
     * @return the value as a float, or the default value
     */
    public static float asFloat(String value, float dflt) {
        try {
            return asFloat(value);
        } catch (DataFormatException e) {
            return dflt;
        }
    }

    /**
