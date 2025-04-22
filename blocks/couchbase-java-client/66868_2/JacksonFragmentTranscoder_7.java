    protected <T> T byteArrayToClass(byte[] byteArray, int offset, int length, Class<? extends T> clazz) throws IOException {
        throw new UnsupportedOperationException("byteArrayToClass is unused by custom decodeWithMessage");
    }

    @Override
    protected <T> byte[] writeValueAsBytes(T value) throws IOException {
        return mapper.writeValueAsBytes(value);
    }

    @Override
    protected void writeValueIntoStream(OutputStream out, Object o) throws IOException {
        mapper.writeValue(out, o);
