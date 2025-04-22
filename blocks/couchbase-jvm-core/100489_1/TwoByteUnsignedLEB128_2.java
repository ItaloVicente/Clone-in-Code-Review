package com.couchbase.client.core.utils;

public class TwoByteUnsignedLEB128 {
    public static byte[] encode(int in) {
        byte[] res = new byte[2];

        for (int i=0;i<res.length;i++) {
            if (in==0) break;
            res[i] = (byte)(i != 0 ? in : (in | 0x80));
            in = in>>>7;
        }

        res[0] = (byte)(res[0] ^ res[1]);
        res[1] = (byte)(res[0] ^ res[1]);
        res[0] = (byte)(res[0] ^ res[1]);
        return res;
    }

    public static int decode(byte[] in) {
        int res = 0;
        for (int i = 0; i < in.length; i++) {
            res = res | ((in[i] & 0x7F) << (7 * i));
        }
        return res;
    }
}
