    private static BinaryMemcacheRequest handleGetAllMutationTokensRequest(ChannelHandlerContext ctx, GetAllMutationTokensRequest msg) {
        BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest("");

        ByteBuf extras;
        switch (msg.partitionState()) {
            case ANY:
                extras = Unpooled.EMPTY_BUFFER;
                break;
            case ACTIVE:
            case REPLICA:
            case PENDING:
            case DEAD:
            default:
                extras = ctx.alloc().buffer().writeInt(msg.partitionState().value());
        }
        byte extrasLength = (byte) extras.readableBytes();

        request
                .setOpcode(OP_GET_ALL_MUTATION_TOKENS)
                .setExtras(extras)
                .setExtrasLength(extrasLength)
                .setTotalBodyLength(extrasLength);
        return request;
    }

