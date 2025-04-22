



    /**
     * Attempts to decode Base64 data and deserialize a Java
     * Object within. Returns <tt>null</tt> if there was an error.
     *
     * @param encodedObject The Base64 data to decode
     * @return The decoded and deserialized object
     * @since 1.5
     */
    public static Object decodeToObject( String encodedObject )
    {
        byte[] objBytes = decode( encodedObject );

        java.io.ByteArrayInputStream  bais = null;
        java.io.ObjectInputStream     ois  = null;
        Object obj = null;

        try
        {
            bais = new java.io.ByteArrayInputStream( objBytes );
            ois  = new java.io.ObjectInputStream( bais );

            obj = ois.readObject();
        catch( java.io.IOException e )
        {
            e.printStackTrace();
        catch( java.lang.ClassNotFoundException e )
        {
            e.printStackTrace();
        finally
        {
            closeStream(bais);
            closeStream(ois);

        return obj;



    /**
     * Convenience method for encoding data to a file.
     *
     * @param dataToEncode byte array of data to encode in base64 form
     * @param filename Filename for saving encoded data
     * @return <tt>true</tt> if successful, <tt>false</tt> otherwise
     *
     * @since 2.1
     */
    public static boolean encodeToFile( byte[] dataToEncode, String filename )
    {
        boolean success = false;
        Base64.OutputStream bos = null;
        try
        {
            bos = new Base64.OutputStream(
                      new java.io.FileOutputStream( filename ), Base64.ENCODE );
            bos.write( dataToEncode );
            success = true;
        catch( java.io.IOException e )
        {

            success = false;
        finally
        {
            closeStream(bos);

        return success;


    /**
     * Convenience method for decoding data to a file.
     *
     * @param dataToDecode Base64-encoded data as a string
     * @param filename Filename for saving decoded data
     * @return <tt>true</tt> if successful, <tt>false</tt> otherwise
     *
     * @since 2.1
     */
    public static boolean decodeToFile( String dataToDecode, String filename )
    {
        boolean success = false;
        Base64.OutputStream bos = null;
        try
        {
                bos = new Base64.OutputStream(
                          new java.io.FileOutputStream( filename ), Base64.DECODE );
                bos.write( dataToDecode.getBytes( PREFERRED_ENCODING ) );
                success = true;
        catch( java.io.IOException e )
        {
            success = false;
        finally
        {
            closeStream(bos);

        return success;




    /**
     * Convenience method for reading a base64-encoded
     * file and decoding it.
     *
     * @param filename Filename for reading encoded data
     * @return decoded byte array or null if unsuccessful
     *
     * @since 2.1
     */
    public static byte[] decodeFromFile( String filename )
    {
        byte[] decodedData = null;
        Base64.InputStream bis = null;
        try
        {
            java.io.File file = new java.io.File( filename );
            byte[] buffer = null;
            int length   = 0;
            int numBytes = 0;

            if( file.length() > Integer.MAX_VALUE )
            {
                System.err.println(MessageFormat.format(JGitText.get().fileIsTooBigForThisConvenienceMethod, file.length()));
                return null;
            buffer = new byte[ (int)file.length() ];

            bis = new Base64.InputStream(
                      new java.io.BufferedInputStream(
                      new java.io.FileInputStream( file ) ), Base64.DECODE );

            while( ( numBytes = bis.read( buffer, length, 4096 ) ) >= 0 )
                length += numBytes;

            decodedData = new byte[ length ];
            System.arraycopy( buffer, 0, decodedData, 0, length );

        catch( java.io.IOException e )
        {
            System.err.println(MessageFormat.format(JGitText.get().errorDecodingFromFile, filename));
        finally
        {
            closeStream(bis);

        return decodedData;



    /**
     * Convenience method for reading a binary file
     * and base64-encoding it.
     *
     * @param filename Filename for reading binary data
     * @return base64-encoded string or null if unsuccessful
     *
     * @since 2.1
     */
    public static String encodeFromFile( String filename )
    {
        String encodedData = null;
        Base64.InputStream bis = null;
        try
        {
            java.io.File file = new java.io.File( filename );
            byte[] buffer = new byte[ (int)(file.length() * 1.4) ];
            int length   = 0;
            int numBytes = 0;

            bis = new Base64.InputStream(
                      new java.io.BufferedInputStream(
                      new java.io.FileInputStream( file ) ), Base64.ENCODE );

            while( ( numBytes = bis.read( buffer, length, 4096 ) ) >= 0 )
                length += numBytes;

            encodedData = new String( buffer, 0, length, Base64.PREFERRED_ENCODING );

        catch( java.io.IOException e )
        {
            System.err.println(MessageFormat.format(JGitText.get().errorEncodingFromFile, filename));
        finally
        {
            closeStream(bis);

        return encodedData;




    /* ********  I N N E R   C L A S S   I N P U T S T R E A M  ******** */



    /**
     * A {@link Base64.InputStream} will read data from another
     * <tt>java.io.InputStream</tt>, given in the constructor,
     * and encode/decode to/from Base64 notation on the fly.
     *
     * @see Base64
     * @since 1.3
     */
    public static class InputStream extends java.io.FilterInputStream
    {
        private int     lineLength;


        /**
         * Constructs a {@link Base64.InputStream} in DECODE mode.
         *
         * @param in the <tt>java.io.InputStream</tt> from which to read data.
         * @since 1.3
         */
        public InputStream( java.io.InputStream in )
        {
            this( in, DECODE );


        /**
         * Constructs a {@link Base64.InputStream} in
         * either ENCODE or DECODE mode.
         * <p>
         * Valid options:<pre>
         *   ENCODE or DECODE: Encode or Decode as data is read.
         *   DONT_BREAK_LINES: don't break lines at 76 characters
         *     (only meaningful when encoding)
         *     <i>Note: Technically, this makes your encoding non-compliant.</i>
         * </pre>
         * <p>
         * Example: <code>new Base64.InputStream( in, Base64.DECODE )</code>
         *
         *
         * @param in the <tt>java.io.InputStream</tt> from which to read data.
         * @param options Specified options
         * @see Base64#ENCODE
         * @see Base64#DECODE
         * @see Base64#DONT_BREAK_LINES
         * @since 2.0
         */
        public InputStream( java.io.InputStream in, int options )
        {
            super( in );
            this.breakLines   = (options & DONT_BREAK_LINES) != DONT_BREAK_LINES;
            this.encode       = (options & ENCODE) == ENCODE;
            this.bufferLength = encode ? 4 : 3;
            this.buffer   = new byte[ bufferLength ];
            this.position = -1;
            this.lineLength = 0;

        /**
         * Reads enough of the input stream to convert
         * to/from Base64 and returns the next byte.
         *
         * @return next byte
         * @since 1.3
         */
        public int read() throws java.io.IOException
        {
            if( position < 0 )
            {
                if( encode )
                {
                    byte[] b3 = new byte[3];
                    int numBinaryBytes = 0;
                    for( int i = 0; i < 3; i++ )
                    {
                        try
                        {
                            int b = in.read();

                            if( b >= 0 )
                            {
                                b3[i] = (byte)b;
                                numBinaryBytes++;

                        catch( java.io.IOException e )
                        {
                            if( i == 0 )
                                throw e;


                    if( numBinaryBytes > 0 )
                    {
                        encode3to4( b3, 0, numBinaryBytes, buffer, 0 );
                        position = 0;
                        numSigBytes = 4;
                    else
                    {
                        return -1;

                else
                {
                    byte[] b4 = new byte[4];
                    int i = 0;
                    for( i = 0; i < 4; i++ )
                    {
                        int b = 0;
                        do{ b = in.read(); }
                        while( b >= 0 && DECODABET[ b & 0x7f ] <= WHITE_SPACE_ENC );

                        if( b < 0 )

                        b4[i] = (byte)b;

                    if( i == 4 )
                    {
                        numSigBytes = decode4to3( b4, 0, buffer, 0 );
                        position = 0;
                    else if( i == 0 ){
                        return -1;
                    else
                    {
                        throw new java.io.IOException(JGitText.get().improperlyPaddedBase64Input);


            if( position >= 0 )
            {
                if( /*!encode &&*/ position >= numSigBytes )
                    return -1;

                if( encode && breakLines && lineLength >= MAX_LINE_LENGTH )
                {
                    lineLength = 0;
                    return '\n';
                else
                {

                    int b = buffer[ position++ ];

                    if( position >= bufferLength )
                        position = -1;


            else
            {
                throw new java.io.IOException(JGitText.get().errorInBase64CodeReadingStream);


        /**
         * Calls {@link #read()} repeatedly until the end of stream
         * is reached or <var>len</var> bytes are read.
         * Returns number of bytes read into array or -1 if
         * end of stream is encountered.
         *
         * @param dest array to hold values
         * @param off offset for array
         * @param len max number of bytes to read into array
         * @return bytes read into array or -1 if end of stream is encountered.
         * @since 1.3
         */
        public int read( byte[] dest, int off, int len ) throws java.io.IOException
        {
            int i;
            int b;
            for( i = 0; i < len; i++ )
            {
                b = read();


                if( b >= 0 )
                    dest[off + i] = (byte)b;
                else if( i == 0 )
                    return -1;
                else
            return i;







    /* ********  I N N E R   C L A S S   O U T P U T S T R E A M  ******** */



    /**
     * A {@link Base64.OutputStream} will write data to another
     * <tt>java.io.OutputStream</tt>, given in the constructor,
     * and encode/decode to/from Base64 notation on the fly.
     *
     * @see Base64
     * @since 1.3
     */
    public static class OutputStream extends java.io.FilterOutputStream
    {
        private boolean encode;
        private int     position;
        private byte[]  buffer;
        private int     bufferLength;
        private int     lineLength;
        private boolean breakLines;
        private boolean suspendEncoding;

        /**
         * Constructs a {@link Base64.OutputStream} in ENCODE mode.
         *
         * @param out the <tt>java.io.OutputStream</tt> to which data will be written.
         * @since 1.3
         */
        public OutputStream( java.io.OutputStream out )
        {
            this( out, ENCODE );


        /**
         * Constructs a {@link Base64.OutputStream} in
         * either ENCODE or DECODE mode.
         * <p>
         * Valid options:<pre>
         *   ENCODE or DECODE: Encode or Decode as data is read.
         *   DONT_BREAK_LINES: don't break lines at 76 characters
         *     (only meaningful when encoding)
         *     <i>Note: Technically, this makes your encoding non-compliant.</i>
         * </pre>
         * <p>
         * Example: <code>new Base64.OutputStream( out, Base64.ENCODE )</code>
         *
         * @param out the <tt>java.io.OutputStream</tt> to which data will be written.
         * @param options Specified options.
         * @see Base64#ENCODE
         * @see Base64#DECODE
         * @see Base64#DONT_BREAK_LINES
         * @since 1.3
         */
        public OutputStream( java.io.OutputStream out, int options )
        {
            super( out );
            this.breakLines   = (options & DONT_BREAK_LINES) != DONT_BREAK_LINES;
            this.encode       = (options & ENCODE) == ENCODE;
            this.bufferLength = encode ? 3 : 4;
            this.buffer       = new byte[ bufferLength ];
            this.position     = 0;
            this.lineLength   = 0;
            this.suspendEncoding = false;
            this.b4           = new byte[4];


        /**
         * Writes the byte to the output stream after
         * converting to/from Base64 notation.
         * When encoding, bytes are buffered three
         * at a time before the output stream actually
         * gets a write() call.
         * When decoding, bytes are buffered four
         * at a time.
         *
         * @param theByte the byte to write
         * @since 1.3
         */
        public void write(int theByte) throws java.io.IOException
        {
            if( suspendEncoding )
            {
                super.out.write( theByte );
                return;

            if( encode )
            {
                buffer[ position++ ] = (byte)theByte;
                {
                    out.write( encode3to4( b4, buffer, bufferLength ) );

                    lineLength += 4;
                    if( breakLines && lineLength >= MAX_LINE_LENGTH )
                    {
                        out.write( NEW_LINE );
                        lineLength = 0;

                    position = 0;

            else
            {
                if( DECODABET[ theByte & 0x7f ] > WHITE_SPACE_ENC )
                {
                    buffer[ position++ ] = (byte)theByte;
                    {
                        int len = Base64.decode4to3( buffer, 0, b4, 0 );
                        out.write( b4, 0, len );
                        position = 0;
                else if( DECODABET[ theByte & 0x7f ] != WHITE_SPACE_ENC )
                {
                    throw new java.io.IOException(JGitText.get().invalidCharacterInBase64Data);



        /**
         * Calls {@link #write(int)} repeatedly until <var>len</var>
         * bytes are written.
         *
         * @param theBytes array from which to read bytes
         * @param off offset for array
         * @param len max number of bytes to read into array
         * @since 1.3
         */
        public void write( byte[] theBytes, int off, int len ) throws java.io.IOException
        {
            if( suspendEncoding )
            {
                super.out.write( theBytes, off, len );
                return;

            for( int i = 0; i < len; i++ )
            {
                write( theBytes[ off + i ] );




        /**
         * Method added by PHIL. [Thanks, PHIL. -Rob]
         * This pads the buffer without closing the stream.
         * @throws java.io.IOException input was not properly padded.
         */
        public void flushBase64() throws java.io.IOException
        {
            if( position > 0 )
            {
                if( encode )
                {
                    out.write( encode3to4( b4, buffer, position ) );
                    position = 0;
                else
                {
                    throw new java.io.IOException(JGitText.get().base64InputNotProperlyPadded);



        /**
         * Flushes and closes (I think, in the superclass) the stream.
         *
         * @since 1.3
         */
        public void close() throws java.io.IOException
        {
            flushBase64();

            super.close();

            buffer = null;
            out    = null;



        /**
         * Suspends encoding of the stream.
         * May be helpful if you need to embed a piece of
         * base640-encoded data in a stream.
         *
         * @throws java.io.IOException input was not properly padded.
         * @since 1.5.1
         */
        public void suspendEncoding() throws java.io.IOException
        {
            flushBase64();
            this.suspendEncoding = true;


        /**
         * Resumes encoding of the stream.
         * May be helpful if you need to embed a piece of
         * base640-encoded data in a stream.
         *
         * @since 1.5.1
         */
        public void resumeEncoding()
        {
            this.suspendEncoding = false;





