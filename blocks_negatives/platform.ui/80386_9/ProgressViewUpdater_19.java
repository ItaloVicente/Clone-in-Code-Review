
        synchronized (updateLock) {
            currentInfo.refresh(info);
            GroupInfo group = info.getGroupInfo();
            if (group != null) {
				currentInfo.refresh(group);
			}
        }
