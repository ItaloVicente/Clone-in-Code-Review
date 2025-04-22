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
