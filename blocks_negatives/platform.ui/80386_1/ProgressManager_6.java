		for (IJobProgressManagerListener listener : listeners) {

			if (isNeverDisplaying(info.getJob(), listener.showsDebug()))
				continue;

			if (listener.showsDebug() || group.isActive())
				listener.refreshGroup(group);
			else
				listener.removeGroup(group);
