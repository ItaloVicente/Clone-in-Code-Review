                    out.add(chunk);
                    if (alreadyReadChunkSize < valueLength) {
                        return;
                    }
                } else {
                    out.add(LastMemcacheContent.EMPTY_LAST_CONTENT);
