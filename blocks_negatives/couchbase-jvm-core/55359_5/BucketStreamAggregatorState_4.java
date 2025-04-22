    public BucketStreamState get(int partition) {
        if (feeds.length > partition) {
            return feeds[partition];
        } else {
            return BucketStreamState.BLANK;
        }
