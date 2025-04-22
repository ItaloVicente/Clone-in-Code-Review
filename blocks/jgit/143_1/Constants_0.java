    public static byte[] encode(final String str
        final ByteBuffer bb = cs.encode(str);
        final int len = bb.limit();
        if (bb.hasArray() && bb.arrayOffset() == 0) {
            final byte[] arr = bb.array();
            if (arr.length == len)
                return arr;
        }

        final byte[] arr = new byte[len];
