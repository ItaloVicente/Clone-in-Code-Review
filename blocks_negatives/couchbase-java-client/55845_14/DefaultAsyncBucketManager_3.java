                @Override
                public Boolean call(RemoveDesignDocumentResponse response) {
                    if (response.content() != null && response.content().refCnt() > 0) {
                        response.content().release();
                    }
                    return response.status().isSuccess();
