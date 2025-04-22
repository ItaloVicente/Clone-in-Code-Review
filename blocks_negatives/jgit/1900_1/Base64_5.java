     * <p>
     * Valid options:<pre>
     *   GZIP: gzip-compresses object before encoding it.
     *   DONT_BREAK_LINES: don't break lines at 76 characters
     *     <i>Note: Technically, this makes your encoding non-compliant.</i>
     * </pre>
     * <p>
     * Example: <code>encodeBytes( myData, Base64.GZIP )</code> or
     * <p>
     * Example: <code>encodeBytes( myData, Base64.GZIP | Base64.DONT_BREAK_LINES )</code>
     *
     *
     * @param source The data to convert
     * @param options Specified options
     * @return encoded base64 representation of source.
     * @see Base64#GZIP
     * @see Base64#DONT_BREAK_LINES
     * @since 2.0
     */
    public static String encodeBytes( byte[] source, int options )
    {
        return encodeBytes( source, 0, source.length, options );


    /**
     * Encodes a byte array into Base64 notation.
     * Does not GZip-compress data.
