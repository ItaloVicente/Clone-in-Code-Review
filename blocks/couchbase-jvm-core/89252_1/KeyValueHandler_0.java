    private static long parseServerDurationFromFrame(final ByteBuf frame) {
        while(frame.readableBytes() > 0) {
            byte control = frame.readByte();
            byte id = (byte) (control & 0xF0);
            byte len = (byte) (control & 0x0F);
            if (id == FRAMING_EXTRAS_TRACING) {
                return Math.round(Math.pow(frame.readShort(), 1.74) / 2);
            } else {
                frame.skipBytes(len);
            }
        }
        return 0;
    }

