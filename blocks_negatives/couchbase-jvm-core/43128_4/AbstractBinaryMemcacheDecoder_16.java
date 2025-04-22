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
