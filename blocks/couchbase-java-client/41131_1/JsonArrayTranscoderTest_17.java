package com.couchbase.client.java.transcoder;

import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;

import java.io.*;

public class TranscoderUtils {

    public static final int PRIVATE_COMMON_FLAGS = createCommonFlags(CommonFlags.PRIVATE.ordinal());
    public static final int JSON_COMMON_FLAGS = createCommonFlags(CommonFlags.JSON.ordinal());
    public static final int BINARY_COMMON_FLAGS = createCommonFlags(CommonFlags.BINARY.ordinal());
    public static final int STRING_COMMON_FLAGS = createCommonFlags(CommonFlags.STRING.ordinal());

    public static final int SERIALIZED_COMPAT_FLAGS = PRIVATE_COMMON_FLAGS | 1;
    public static final int JSON_COMPAT_FLAGS = JSON_COMMON_FLAGS;
    public static final int BINARY_COMPAT_FLAGS = BINARY_COMMON_FLAGS | (8 << 8);
    public static final int BOOLEAN_COMPAT_FLAGS = JSON_COMMON_FLAGS;
    public static final int LONG_COMPAT_FLAGS = JSON_COMMON_FLAGS;
    public static final int DOUBLE_COMPAT_FLAGS = JSON_COMPAT_FLAGS;

    public static boolean hasCommonFlags(final int flags) {
        return (flags >> 24) > 0;
    }

    public static int extractCommonFlags(final int flags) {
        return flags >> 24;
    }

    public static int createCommonFlags(final int flags) {
        return flags << 24;
    }

    public static boolean hasJsonFlags(final int flags) {
        return hasCommonFlags(flags) ? flags == JSON_COMMON_FLAGS : flags == 0;
    }

    public static boolean hasStringFlags(final int flags) {
        return hasCommonFlags(flags) ? flags == STRING_COMMON_FLAGS : flags == 0;
    }

    public static boolean hasLongFlags(final int flags) {
        return hasCommonFlags(flags) ? flags == JSON_COMMON_FLAGS : flags == (3 << 8);
    }

    public static boolean hasSerializableFlags(final int flags) {
        return hasCommonFlags(flags) ? flags == PRIVATE_COMMON_FLAGS : flags == 1;
    }

    public static boolean hasBinaryFlags(final int flags) {
        return hasCommonFlags(flags) ? flags == BINARY_COMMON_FLAGS : flags == (8 << 8);
    }

    public static Serializable deserialize(final ByteBuf content) throws Exception {
        byte[] serialized = new byte[content.readableBytes()];
        content.getBytes(0, serialized);
        ByteArrayInputStream bis = new ByteArrayInputStream(serialized);
        ObjectInputStream is = new ObjectInputStream(bis);
        Serializable deserialized = (Serializable) is.readObject();
        is.close();
        bis.close();
        return deserialized;
    }

    public static ByteBuf serialize(final Serializable serializable) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();;
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(serializable);

        byte[] serialized = bos.toByteArray();
        os.close();
        bos.close();
        return Unpooled.buffer().writeBytes(serialized);
    }

    public static enum CommonFlags {
        RESERVED,
        PRIVATE,
        JSON,
        BINARY,
        STRING
    }

}
