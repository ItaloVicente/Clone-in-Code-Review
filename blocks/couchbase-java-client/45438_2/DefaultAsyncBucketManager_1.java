                    try {
                        if (!response.status().isSuccess()) {
                            String msg = response.content().toString(CharsetUtil.UTF_8);
                            throw new DesignDocumentException("Could not store DesignDocument: " + msg);
                        }
                    } finally {
                        if (response.content() != null && response.content().refCnt() > 0) {
                            response.content().release();
                        }
