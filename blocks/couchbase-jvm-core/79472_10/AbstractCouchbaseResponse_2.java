    @Override
    public ResponseStatusDetails statusDetails() {
        return statusDetails;
    }

    @Override
    public void statusDetails(final ResponseStatusDetails statusDetails) {
        if (this.statusDetails == null) {
            this.statusDetails = statusDetails;
        }
    }

