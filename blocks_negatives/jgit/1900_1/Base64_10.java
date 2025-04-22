
        bytes = decode( bytes, 0, bytes.length );


        if( bytes != null && bytes.length >= 4 )
        {

            int head = (bytes[0] & 0xff) | ((bytes[1] << 8) & 0xff00);
            if( java.util.zip.GZIPInputStream.GZIP_MAGIC == head )
            {
                java.io.ByteArrayInputStream  bais = null;
                java.util.zip.GZIPInputStream gzis = null;
                java.io.ByteArrayOutputStream baos = null;
                byte[] buffer = new byte[2048];
                int    length = 0;
