                    out.add(currentMessage);
                    state = State.READ_CONTENT;
                } catch (Exception e) {
                    out.add(invalidMessage(e));
                    return;
                }
            case READ_CONTENT:
                try {
                    int valueLength = currentMessage.getTotalBodyLength()
                        - currentMessage.getKeyLength()
                        - currentMessage.getExtrasLength();
                    int toRead = in.readableBytes();
                    if (valueLength > 0) {
                        if (toRead == 0) {
                            return;
                        }

                        if (toRead > chunkSize) {
                            toRead = chunkSize;
                        }

                        int remainingLength = valueLength - alreadyReadChunkSize;
                        if (toRead > remainingLength) {
                            toRead = remainingLength;
                        }

                        ByteBuf chunkBuffer = readBytes(ctx.alloc(), in, toRead);

                        MemcacheContent chunk;
                        if ((alreadyReadChunkSize += toRead) >= valueLength) {
                            chunk = new DefaultLastMemcacheContent(chunkBuffer);
                        } else {
                            chunk = new DefaultMemcacheContent(chunkBuffer);
                        }

                        out.add(chunk);
                        if (alreadyReadChunkSize < valueLength) {
                            return;
                        }
