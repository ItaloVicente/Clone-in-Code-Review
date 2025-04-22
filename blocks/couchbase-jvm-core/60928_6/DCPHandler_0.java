            case OP_EXPIRATION:
                if (msg.getExtrasLength() > 0) {
                    final ByteBuf extras = msg.getExtras();
                    bySeqno = extras.readLong();
                    revSeqno = extras.readLong();
                }
                request = new ExpirationMessage(msg.getStatus(), msg.getKey(),
                        msg.getCAS(), bySeqno, revSeqno, connection.bucket());
                break;

