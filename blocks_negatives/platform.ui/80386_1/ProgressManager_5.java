		for (IJobProgressManagerListener listener : listeners) {
			if (isNeverDisplaying(info.getJob(), listener.showsDebug()))
				continue;
			if (listener.showsDebug())
				listener.refreshJobInfo(info);
			else
				listener.removeJob(info);
