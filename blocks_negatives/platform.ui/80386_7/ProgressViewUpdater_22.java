
        if (isUpdateJob(info.getJob())) {
			return;
		}

        synchronized (updateLock) {
            GroupInfo group = info.getGroupInfo();
            if (group == null) {
				currentInfo.remove(info);
			} else {
                currentInfo.refresh(group);
            }
