
        if (header.getMagic() == 0x18) {
            header.setFramingExtrasLength(in.readByte());
            header.setKeyLength(in.readByte());
        } else {
            header.setKeyLength(in.readShort());
        }

