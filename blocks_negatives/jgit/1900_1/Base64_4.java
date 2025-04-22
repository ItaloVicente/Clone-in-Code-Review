

    /**
     * Serializes an object and returns the Base64-encoded
     * version of that serialized object. If the object
     * cannot be serialized or there is another error,
     * the method will return <tt>null</tt>.
     * The object is not GZip-compressed before being encoded.
     *
     * @param serializableObject The object to encode
     * @return The Base64-encoded object
     * @since 1.4
     */
    public static String encodeObject( java.io.Serializable serializableObject )
    {
        return encodeObject( serializableObject, NO_OPTIONS );



    /**
     * Serializes an object and returns the Base64-encoded
     * version of that serialized object. If the object
     * cannot be serialized or there is another error,
     * the method will return <tt>null</tt>.
     * <p>
     * Valid options:<pre>
     *   GZIP: gzip-compresses object before encoding it.
     *   DONT_BREAK_LINES: don't break lines at 76 characters
     *     <i>Note: Technically, this makes your encoding non-compliant.</i>
     * </pre>
     * <p>
     * Example: <code>encodeObject( myObj, Base64.GZIP )</code> or
     * <p>
     * Example: <code>encodeObject( myObj, Base64.GZIP | Base64.DONT_BREAK_LINES )</code>
     *
     * @param serializableObject The object to encode
     * @param options Specified options
     * @return The Base64-encoded object
     * @see Base64#GZIP
     * @see Base64#DONT_BREAK_LINES
     * @since 2.0
     */
    public static String encodeObject( java.io.Serializable serializableObject, int options )
    {
        java.io.ByteArrayOutputStream  baos  = null;
        java.io.OutputStream           b64os = null;
        java.io.ObjectOutputStream     oos   = null;
        java.util.zip.GZIPOutputStream gzos  = null;

        int gzip           = (options & GZIP);
        int dontBreakLines = (options & DONT_BREAK_LINES);

        try
        {
            baos  = new java.io.ByteArrayOutputStream();
            b64os = new Base64.OutputStream( baos, ENCODE | dontBreakLines );

            if( gzip == GZIP )
            {
                gzos = new java.util.zip.GZIPOutputStream( b64os );
                oos  = new java.io.ObjectOutputStream( gzos );
            else
                oos   = new java.io.ObjectOutputStream( b64os );

            oos.writeObject( serializableObject );
        catch( java.io.IOException e )
        {
            e.printStackTrace();
            return null;
        finally
        {
            closeStream(oos);
            closeStream(gzos);
            closeStream(b64os);
            closeStream(baos);

        try
        {
            return new String( baos.toByteArray(), PREFERRED_ENCODING );
        catch (java.io.UnsupportedEncodingException uue)
        {
            return new String( baos.toByteArray() );




