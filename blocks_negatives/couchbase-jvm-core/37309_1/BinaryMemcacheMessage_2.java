    /**
     * Increases the reference count by {@code 1}.
     */
    @Override
    BinaryMemcacheMessage retain();

    /**
     * Increases the reference count by the specified {@code increment}.
     */
    @Override
    BinaryMemcacheMessage retain(int increment);

