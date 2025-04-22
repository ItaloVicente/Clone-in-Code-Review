
                    if (currentChunk.bytesBefore((byte) 't') >= 0) {
                        ByteBuf slice = currentChunk.readBytes(rowsStart+1);
                        String[] sliced = slice.toString(CharsetUtil.UTF_8).split(":");
                        String[] parts = sliced[1].split(",");
                        currentTotalRows = Integer.parseInt(parts[0]);
