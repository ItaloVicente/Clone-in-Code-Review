    private static BinaryMemcacheRequest handleSubdocumentRequest(ChannelHandlerContext ctx, BinarySubdocRequest msg) {
        String key = msg.key();
        short keyLength = (short) msg.keyBytes().length;

        ByteBuf extras = ctx.alloc().buffer(3, 7); //extras can be 7 bytes if there is an expiry
        byte extrasLength = 3; //by default 2 bytes for pathLength + 1 byte for "command" flags
        extras.writeShort(msg.pathLength());

        long cas = 0L;

        if (msg instanceof BinarySubdocMutationRequest) {
            BinarySubdocMutationRequest mut = (BinarySubdocMutationRequest) msg;
            if (mut.createIntermediaryPath()) {
                extras.writeByte(0 | SUBDOC_BITMASK_MKDIR_P);
            } else {
                extras.writeByte(0);
            }
            if (mut.expiration() != 0L) {
                extrasLength = 7;
                extras.writeInt(mut.expiration());
            }

            cas = mut.cas();
        } else {
            extras.writeByte(0);
        }

        FullBinaryMemcacheRequest request = new DefaultFullBinaryMemcacheRequest(key, extras, msg.content());
        request.setOpcode(msg.opcode())
                .setKeyLength(keyLength)
                .setExtrasLength(extrasLength)
                .setTotalBodyLength(keyLength + msg.content().readableBytes() + extrasLength)
                .setCAS(cas);

        return request;
    }

    private static BinaryMemcacheRequest handleSubdocumentMultiLookupRequest(ChannelHandlerContext ctx,
                                                                             BinarySubdocMultiLookupRequest msg) {
        String key = msg.key();
        short keyLength = (short) msg.keyBytes().length;

        FullBinaryMemcacheRequest request = new DefaultFullBinaryMemcacheRequest(key, Unpooled.EMPTY_BUFFER, msg.content());
        request.setOpcode(OP_SUB_MULTI_LOOKUP)
                .setKeyLength(keyLength)
                .setExtrasLength((byte) 0)
                .setTotalBodyLength(keyLength + msg.content().readableBytes());

        return request;
    }

    private static BinaryMemcacheRequest handleSubdocumentMultiMutationRequest(ChannelHandlerContext ctx,
                                                                             BinarySubdocMultiMutationRequest msg) {
        String key = msg.key();
        short keyLength = (short) msg.keyBytes().length;

        byte extrasLength = 0;
        ByteBuf extras = Unpooled.EMPTY_BUFFER;
        if (msg.expiration() != 0L) {
            extrasLength = 4;
            extras = ctx.alloc().buffer(4, 4);
            extras.writeInt(msg.expiration());
        }

        FullBinaryMemcacheRequest request = new DefaultFullBinaryMemcacheRequest(key, extras, msg.content());
        request.setOpcode(OP_SUB_MULTI_MUTATION)
                .setKeyLength(keyLength)
                .setExtrasLength(extrasLength)
                .setTotalBodyLength(keyLength + msg.content().readableBytes() + extrasLength);

        return request;
    }

