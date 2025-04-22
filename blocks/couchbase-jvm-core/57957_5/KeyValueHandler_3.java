    private static final byte SUBDOC_BITMASK_MKDIR_P = 1;

    private static BinaryMemcacheRequest handleSubdocumentRequest(ChannelHandlerContext ctx, BinarySubdocRequest msg) {
        String key = msg.key();
        short keyLength = (short) msg.keyBytes().length;

        ByteBuf extras = ctx.alloc().buffer(3, 7); //extras can be 7 bytes if there is an expiry
        byte extrasLength = 3; //by default 2 bytes for pathLength + 1 byte for "command" flags
        extras.writeShort(msg.pathLength());

        long cas = 0L;

        if (msg instanceof BinarySubdocMutationRequest) {
            BinarySubdocMutationRequest mut = (BinarySubdocMutationRequest) msg;
            extras.writeByte(mut.createIntermediaryPath() ? SUBDOC_BITMASK_MKDIR_P : 0);
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

