            case READ_KEY:
                try {
                    short keyLength = currentMessage.getKeyLength();
                    if (keyLength > 0) {
                        if (in.readableBytes() < keyLength) {
                            return;
                        }

                        currentMessage.setKey(in.toString(in.readerIndex(), keyLength, CharsetUtil.UTF_8));
                        in.skipBytes(keyLength);
