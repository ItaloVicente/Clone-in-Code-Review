        if (currentMessage != null && currentMessage.getFramingExtras() != null) {
            if (currentMessage.getFramingExtras().refCnt() > 0) {
                currentMessage.getFramingExtras().release();
            }
        }

