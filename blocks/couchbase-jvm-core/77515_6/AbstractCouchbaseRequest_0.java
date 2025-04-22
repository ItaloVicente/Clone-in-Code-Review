    @Override
    public boolean isActive() {
        return this.subscriber == null || !this.subscriber.isUnsubscribed();
    }

    @Override
    public void subscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

