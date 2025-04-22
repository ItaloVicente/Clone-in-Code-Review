    /**
     * Increases the reference count by {@code 1}.
     */
    @Override
    MemcacheMessage retain();

    /**
     * Increases the reference count by the specified {@code increment}.
     */
    @Override
    MemcacheMessage retain(int increment);

