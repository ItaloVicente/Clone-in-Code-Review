
    @Override
    public boolean hasSeenNotMyVbucket() {
        return seenNotMyVbucket;
    }

    @Override
    public void sawNotMyVbucket() {
        seenNotMyVbucket = true;
    }
