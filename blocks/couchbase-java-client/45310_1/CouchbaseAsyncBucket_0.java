                public Boolean call(GetResponse response) {
                    if (response.status().isSuccess()) {
                        return true;
                    }
                    ByteBuf content = response.content();
                    if (content != null && content.refCnt() > 0) {
                        content.release();
                    }
                    return false;
