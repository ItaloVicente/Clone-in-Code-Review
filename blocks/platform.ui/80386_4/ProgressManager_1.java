		if (!scheduledUpdates.containsKey(info)) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					scheduledUpdates.remove(info);
					GroupInfo group = info.getGroupInfo();
					if (group != null) {
						refreshGroup(group);
					}

					Object[] listenersArray = listeners.getListeners();
					for (int i = 0; i < listenersArray.length; i++) {
						IJobProgressManagerListener listener = (IJobProgressManagerListener) listenersArray[i];
						if (!isCurrentDisplaying(info.getJob(), listener.showsDebug())) {
							listener.refreshJobInfo(info);
						}
					}
				}
			};
			scheduledUpdates.put(info, runnable);

			safeAsyncExec(new Runnable() {
				@Override
				public void run() {
					display.timerExec(100, runnable);
				}
			});
		}
	}

	private void safeAsyncExec(Runnable runnable) {
		if (!display.isDisposed()) {
			display.asyncExec(runnable);
		}
