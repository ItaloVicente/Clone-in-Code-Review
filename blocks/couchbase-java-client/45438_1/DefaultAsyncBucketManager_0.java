                    boolean success = response.status().isSuccess();
                    if (!success) {
                        if (response.content() != null && response.content().refCnt() > 0) {
                           response.content().release();
                        }
                    }
                    return success;
