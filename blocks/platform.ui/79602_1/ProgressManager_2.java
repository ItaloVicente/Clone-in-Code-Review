			Object[] listenersArray = listeners.getListeners();
			for (int i = 0; i < listenersArray.length; i++) {
				IJobProgressManagerListener listener = (IJobProgressManagerListener) listenersArray[i];
				if (!isCurrentDisplaying(jobInfo.getJob(), listener.showsDebug())) {
					listener.refreshJobInfo(jobInfo);
				}
