    @Override
    public String username() {
        return password;
    }

    @Override
    public BucketConfig username(final String username) {
        this.username = username;
        return this;
    }

