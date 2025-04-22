     * Converts the given value into an SWT font data object.
     * This method fails if the value does not represent font data.
     * <p>
     * A valid font data representation is a string of the form
     * <code><it>fontname</it>-<it>style</it>-<it>height</it></code> where
     * <code><it>fontname</it></code> is the name of a font,
     * <code><it>style</it></code> is a font style (one of
     * <code>"regular"</code>, <code>"bold"</code>,
     * <code>"italic"</code>, or <code>"bold italic"</code>)
     * and <code><it>height</it></code> is an integer representing the
     * font height. Example: <code>Times New Roman-bold-36</code>.
     * </p>
     *
     * @param value the value to be converted
     * @return the value as font data
     * @exception DataFormatException if the given value does not represent
     *	font data
     */
