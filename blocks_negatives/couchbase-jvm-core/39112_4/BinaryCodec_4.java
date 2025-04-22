        ByteBuf extras = ctx.alloc().buffer(8);
        extras.writeInt(request.flags());
        extras.writeInt(request.expiration());

        ByteBuf content = request.content();
        boolean compress = datatypes.compression() && env.compressionEnabled()
            && content.readableBytes() >= env.compressionLowerLimit();
        if (compress) {
            ByteBuf compressed = ctx.alloc().buffer();
            snappy.encode(content, compressed, content.readableBytes());
            content.release();
            content = compressed;
        }

        FullBinaryMemcacheRequest msg = new DefaultFullBinaryMemcacheRequest(request.key(), extras, content);

        msg.setOpcode(BinaryMemcacheOpcodes.SET);
        msg.setKeyLength((short) request.key().length());
        msg.setTotalBodyLength((short) request.key().length() + content.readableBytes() + extras.readableBytes());
        msg.setReserved(request.partition());
        msg.setExtrasLength((byte) extras.readableBytes());
        if (datatypes.json() && request.isJson()) {
            if (compress) {
                msg.setDataType((byte) 0x03);
            } else {
                msg.setDataType((byte) 0x01);
            }
        } else if (compress) {
            msg.setDataType((byte) 0x02);
        }
        return msg;
