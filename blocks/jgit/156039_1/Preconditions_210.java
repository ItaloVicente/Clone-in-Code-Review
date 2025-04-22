
package org.eclipse.jgit.niofs.internal.util;

import java.io.UnsupportedEncodingException;
import java.util.BitSet;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;

public class EncodingUtil {

    public static final BitSet allowed_abs_path = new BitSet(256);

    protected static final BitSet percent = new BitSet(256);
    protected static final BitSet digit = new BitSet(256);
    protected static final BitSet alpha = new BitSet(256);
    protected static final BitSet alphanum = new BitSet(256);
    protected static final BitSet hex = new BitSet(256);
    protected static final BitSet escaped = new BitSet(256);
    protected static final BitSet mark = new BitSet(256);
    protected static final BitSet unreserved = new BitSet(256);
    protected static final BitSet segment = new BitSet(256);
    protected static final BitSet path_segments = new BitSet(256);
    protected static final BitSet abs_path = new BitSet(256);

    static {
        percent.set('%');
    }

    static {
        for (int i = '0'; i <= '9'; i++) {
            digit.set(i);
        }
    }

    static {
        for (int i = 'a'; i <= 'z'; i++) {
            alpha.set(i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            alpha.set(i);
        }
    }

    static {
        alphanum.or(alpha);
        alphanum.or(digit);
    }

    static {
        hex.or(digit);
        for (int i = 'a'; i <= 'f'; i++) {
            hex.set(i);
        }
        for (int i = 'A'; i <= 'F'; i++) {
            hex.set(i);
        }
    }

    static {
        escaped.or(percent);
        escaped.or(hex);
    }

    static {
        mark.set('-');
        mark.set('_');
        mark.set('.');
        mark.set('!');
        mark.set('~');
        mark.set('*');
        mark.set('\'');
        mark.set('(');
        mark.set(')');
    }

    static {
        unreserved.or(alphanum);
        unreserved.or(mark);
    }

    static {
        pchar.or(unreserved);
        pchar.or(escaped);
        pchar.set(':');
        pchar.set('@');
        pchar.set('&');
        pchar.set('=');
        pchar.set('+');
        pchar.set('$');
        pchar.set('
    }

    static {
        segment.or(pchar);
        segment.set(';');
        segment.or(param);
    }

    static {
        path_segments.set('/');
        path_segments.or(segment);
    }

    static {
        abs_path.set('/');
        abs_path.or(path_segments);
    }

    static {
        allowed_abs_path.or(abs_path);
        allowed_abs_path.andNot(percent);
        allowed_abs_path.clear('+');
    }

    private EncodingUtil() {
    }


    public static String encodePath(String unescaped) {
        byte[] rawdata = URLCodec.encodeUrl(allowed_abs_path
                                            getBytes(unescaped
                                                     "UTF-8"));
        return getAsciiString(rawdata);
    }

    public static byte[] getBytes(final String data
                                  String charset) {
        if (data == null) {
            throw new IllegalArgumentException("data may not be null");
        }

        if (charset == null || charset.length() == 0) {
            throw new IllegalArgumentException("charset may not be null or empty");
        }

        try {
            return data.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            return data.getBytes();
        }
    }

    public static String getAsciiString(final byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Parameter may not be null");
        }

        try {
            return new String(data
                              0
                              data.length
                              "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(EncodingUtil.class.getSimpleName() + " requires ASCII support");
        }
    }

    public static byte[] getAsciiBytes(final String data) {
        if (data == null) {
            throw new IllegalArgumentException("Parameter may not be null");
        }

        try {
            return data.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(EncodingUtil.class.getSimpleName() + " requires ASCII support");
        }
    }

    public static String getString(final byte[] data
                                   String charset) {
        if (data == null) {
            throw new IllegalArgumentException("Parameter may not be null");
        }

        if (charset == null || charset.length() == 0) {
            throw new IllegalArgumentException("charset may not be null or empty");
        }

        try {
            return new String(data
                              0
                              data.length
                              charset);
        } catch (UnsupportedEncodingException e) {
            return new String(data
                              0
                              data.length);
        }
    }

    public static String decode(String escaped) {
        byte[] asciiData = getAsciiBytes(escaped);
        byte[] rawdata;
        try {
            rawdata = URLCodec.decodeUrl(asciiData);
        } catch (DecoderException e) {
            throw new IllegalStateException(e.getMessage());
        }
        return getString(rawdata
                         "UTF-8");
    }
}
