            case OP_STREAM_END:
                final ByteBuf extrasReleased = msg.getExtras();
                final ByteBuf extras = ctx.alloc().buffer(msg.getExtrasLength());
                extras.writeBytes(extrasReleased, extrasReleased.readerIndex(), extrasReleased.readableBytes());
                flags = extras.readInt();
                extras.release();
                request = new StreamEndMessage(StreamEndMessage.Reason.valueOf(flags), connection.bucket());
                connection.removeStream(msg.getOpaque());
                break;
