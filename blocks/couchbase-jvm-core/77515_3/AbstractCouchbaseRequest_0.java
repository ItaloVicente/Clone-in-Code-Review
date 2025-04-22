    @Override
    public boolean isActive() {
        if (this.subscriber != null) {
            return !this.subscriber.isUnsubscribed();
        } else {
            return true;
        }
    }

    @Override
    public void subscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

