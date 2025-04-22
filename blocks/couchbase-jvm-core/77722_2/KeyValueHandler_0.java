        }

        if (msg.docFlags() != 0) {
            extrasLength += 1;
        }

        if (extrasLength > 0) {
            extras = ctx.alloc().buffer(extrasLength, extrasLength);
            if (msg.expiration() != 0L) {
                extras.writeInt(msg.expiration());
            }
            if (msg.docFlags() != 0) {
                extras.writeByte(msg.docFlags());
            }
