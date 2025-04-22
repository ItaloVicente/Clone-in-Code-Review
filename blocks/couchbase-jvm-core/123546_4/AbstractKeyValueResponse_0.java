    @Override
    public BinaryResponse touch() {
        content.touch();
        return this;
    }

    @Override
    public BinaryResponse touch(Object o) {
        content.touch(o);
        return this;
    }

