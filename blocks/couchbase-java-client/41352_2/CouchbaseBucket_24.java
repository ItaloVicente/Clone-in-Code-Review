            })
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public Boolean unlock(String id, long cas) {
        return unlock(id, cas, binaryTimeout, TIMEOUT_UNIT);
