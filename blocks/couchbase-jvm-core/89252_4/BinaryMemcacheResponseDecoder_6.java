
        if (header.getMagic() == FRAMING_MAGIC) {
            header.setFramingExtrasLength(in.readByte());
            header.setKeyLength(in.readByte());
        } else {
            header.setKeyLength(in.readShort());
        }

