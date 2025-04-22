        return new DefaultJoinPath(this);
    }

    @Override
    public KeysPath useHash(HashSide side) {
        element(new HashJoinHintElement(side));
        return new DefaultKeysPath(this);
    }

    @Override
    public KeysPath useNestedLoop() {
        element(new NestedLoopJoinHintElement());
