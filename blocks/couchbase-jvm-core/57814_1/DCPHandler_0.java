                if (msg.getExtrasLength() > 0) {
                    final ByteBuf extrasReleased = msg.getExtras();
                    final ByteBuf extras = ctx.alloc().buffer(msg.getExtrasLength());
                    extras.writeBytes(extrasReleased, extrasReleased.readerIndex(), extrasReleased.readableBytes());
                    bySeqno = extras.readLong();
                    revSeqno = extras.readLong();
                    extras.release();
                }
                request = new RemoveMessage(msg.getStatus(), msg.getKey(), msg.getCAS(), bySeqno, revSeqno, connection.bucket());
