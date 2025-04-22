
        if (currentMessage != null && currentMessage.getExtras() != null) {
            if (currentMessage.getExtras().refCnt() > 0) {
                currentMessage.getExtras().release();
            }
        }

        if (currentMessage != null && currentMessage.getFramingExtras() != null) {
            if (currentMessage.getFramingExtras().refCnt() > 0) {
                currentMessage.getFramingExtras().release();
            }
        }

