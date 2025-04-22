	public void refreshAll() {

        synchronized (updateLock) {
            currentInfo.updateAll = true;
        }

        scheduleUpdate();
