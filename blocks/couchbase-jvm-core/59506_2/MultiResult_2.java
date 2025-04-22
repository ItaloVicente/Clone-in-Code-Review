    public static MultiResult<Lookup> create(short statusCode, ResponseStatus status, String path, Lookup operation, ByteBuf value) {
        return new MultiResult<Lookup>(statusCode, status, path, operation, value);
    }

    public static MultiResult<Mutation> create(short statusCode, ResponseStatus status, String path, Mutation operation, ByteBuf value) {
        return new MultiResult<Mutation>(statusCode, status, path, operation, value);
    }

