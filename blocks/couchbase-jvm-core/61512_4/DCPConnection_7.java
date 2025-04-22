            Attribute<Integer> attr = ctx.attr(CONSUMED_BYTES);
            Integer consumedBytes = attr.get();
            if (consumedBytes == null) {
                consumedBytes = 0;
            }
            consumedBytes += MINIMUM_HEADER_SIZE + delta;
            if (consumedBytes >= env.dcpConnectionBufferSize() * env.dcpConnectionBufferAckThreshold()) {
                ctx.writeAndFlush(createBufferAcknowledgmentRequest(ctx, consumedBytes));
                consumedBytes = 0;
            }
            attr.set(consumedBytes);
