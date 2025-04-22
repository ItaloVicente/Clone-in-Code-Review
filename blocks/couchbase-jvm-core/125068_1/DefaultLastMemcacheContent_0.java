        return replace(content().duplicate());
    }

    @Override
    public LastMemcacheContent retainedDuplicate() {
        return replace(content().retainedDuplicate());
