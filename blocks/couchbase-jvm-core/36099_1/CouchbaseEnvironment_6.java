        return reqBufSize;
    }

    @Override
    public int responseBufferSize() {
        int resBufSize = getInt("core.responseBufferSize");
        if (resBufSize <= 0) {
            throw new EnvironmentException("Response Buffer Size must be > 0 and power of two");
        }
        return resBufSize;
