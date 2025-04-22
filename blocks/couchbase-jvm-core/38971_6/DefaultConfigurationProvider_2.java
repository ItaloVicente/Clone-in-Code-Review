    @Override
    public void signalOutdated() {
        for (Refresher refresher : refreshers.values()) {
            refresher.refresh(currentConfig.get());
        }
    }

