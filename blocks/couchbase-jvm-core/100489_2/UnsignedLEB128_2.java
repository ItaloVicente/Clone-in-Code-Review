package com.couchbase.client.core.utils;

import java.util.ArrayList;
import java.util.List;

public class UnsignedLEB128 {
    public static byte[] encode(int in) {
        List<Byte> encoded = new ArrayList<Byte>();
        encoded.add((byte) (in & 0x7F));
        in = in>>>7;

        while(in != 0) {
            encoded.add((byte)(in & 0x7F));
            in = in>>>7;
        }

        byte[] res = new byte[encoded.size()];
        for(int j=encoded.size()-1,i=0;j>=0;j--,i++) {
            res[i] = i == 0 ? encoded.get(j) : ((byte)(encoded.get(j) | 0x80));
        }
        return res;
    }

    public static int decode(byte[] in) {
        int res = 0;
        for (int i=in.length-1,j=0; i>=0; i--,j++) {
            res = res | ((in[i] & 0x7F) << (7 * j));
        }
        return res;
    }
}
