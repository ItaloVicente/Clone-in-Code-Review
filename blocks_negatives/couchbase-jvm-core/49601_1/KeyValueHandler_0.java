            ByteBuf content = null;
            if (request instanceof BinaryStoreRequest) {
                content = ((BinaryStoreRequest) request).content();
            } else if (request instanceof AppendRequest) {
                content = ((AppendRequest) request).content();
            } else if (request instanceof PrependRequest) {
                content = ((PrependRequest) request).content();
            }
            if (content != null && content.refCnt() > 0) {
                content.release();
            }
