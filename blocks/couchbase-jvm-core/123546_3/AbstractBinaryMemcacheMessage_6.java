    @Override
    public BinaryMemcacheMessage touch() {
        if (extras != null) {
            extras.touch();
        }
        if (framingExtras != null) {
            framingExtras.touch();
        }
        return this;
    }

    @Override
    public BinaryMemcacheMessage touch(Object hint) {
        if (extras != null) {
            extras.touch(hint);
        }
        if (framingExtras != null) {
            framingExtras.touch(hint);
        }
        return this;
    }

