    @Override
    public boolean isTimedOut() {
        if (this.subscriber != null) {
            return this.subscriber.isUnsubscribed();
        } else {
            return false;
        }
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

