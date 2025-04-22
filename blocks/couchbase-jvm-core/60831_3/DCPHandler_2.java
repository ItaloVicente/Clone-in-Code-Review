                request = new RemoveMessage(connection, msg.getTotalBodyLength(), msg.getStatus(), msg.getKey(),
                        msg.getCAS(), bySeqno, revSeqno, connection.bucket());
                break;

            case OP_EXPIRATION:
                if (msg.getExtrasLength() > 0) {
                    final ByteBuf extras = msg.getExtras();
                    bySeqno = extras.readLong();
                    revSeqno = extras.readLong();
                }
                request = new ExpirationMessage(connection, msg.getTotalBodyLength(), msg.getStatus(), msg.getKey(),
                        msg.getCAS(), bySeqno, revSeqno, connection.bucket());
