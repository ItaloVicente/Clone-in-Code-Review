    public static byte[] decode( String s )
    {
        byte[] bytes;
        try
        {
            bytes = s.getBytes( PREFERRED_ENCODING );
        catch( java.io.UnsupportedEncodingException uee )
        {
            bytes = s.getBytes();

