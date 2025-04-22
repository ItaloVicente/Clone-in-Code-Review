                if (msg.getExtrasLength() > 0) {
                    final ByteBuf extras = msg.getExtras();
                    bySeqno = extras.readLong();
                    revSeqno = extras.readLong();
                }
                request = new RemoveMessage(msg.getStatus(), msg.getKey(), msg.getCAS(), bySeqno, revSeqno, connection.bucket());
