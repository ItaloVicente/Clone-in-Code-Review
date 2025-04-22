            encoded.writeBytes(((String) content).getBytes(CharsetUtil.UTF_8));
        } else if (content instanceof Long) {
            flags |= SPECIAL_LONG;
            encoded.writeBytes(encodeNum((Long) content, 8));
        } else if (content instanceof Integer) {
            flags |= SPECIAL_INT;
            encoded.writeBytes(encodeNum((Integer) content, 4));
        } else if (content instanceof Boolean) {
            flags |= SPECIAL_BOOLEAN;
            boolean b = (Boolean) content;
            encoded = Unpooled.buffer().writeByte(b ? '1' : '0');
        } else if (content instanceof Date) {
            flags |= SPECIAL_DATE;
            encoded.writeBytes(encodeNum(((Date) content).getTime(), 8));
        } else if (content instanceof Byte) {
            flags |= SPECIAL_BYTE;
            encoded.writeByte((Byte) content);
        } else if (content instanceof Float) {
            flags |= SPECIAL_FLOAT;
            encoded.writeBytes(encodeNum(Float.floatToRawIntBits((Float) content), 4));
        } else if (content instanceof Double) {
            flags |= SPECIAL_DOUBLE;
            encoded.writeBytes(encodeNum(Double.doubleToRawLongBits((Double) content), 8));
        } else if (content instanceof byte[]) {
            flags |= SPECIAL_BYTEARRAY;
            encoded.writeBytes((byte[]) content);
