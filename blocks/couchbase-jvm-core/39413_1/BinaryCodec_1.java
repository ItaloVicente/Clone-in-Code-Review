
        byte opcode;
        ByteBuf extras;
        if (request.lock()) {
            opcode = (byte) 0x94;
            extras = ctx.alloc().buffer().writeInt(request.expiry());
        } else if (request.touch()) {
            opcode = 0x1d;
            extras = ctx.alloc().buffer().writeInt(request.expiry());
        } else {
            opcode = BinaryMemcacheOpcodes.GET;
            extras = Unpooled.EMPTY_BUFFER;
        }

        msg.setOpcode(opcode);
        msg.setKeyLength((short) keyLength);
        msg.setTotalBodyLength((short) keyLength + extras.readableBytes());
